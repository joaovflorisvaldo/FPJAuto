package Service;

import dto.ItemVendaDto;
import dto.VendaDto;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import modelo.ItemVenda;
import org.json.JSONObject; // Importando a biblioteca org.json

public class ServiceVenda {

    public class SaveResult {
        private boolean success;
        private String errorMessage;
        private int vendaId; // Adicionado para armazenar o ID da venda

        public SaveResult(boolean success, String errorMessage, int vendaId) {
            this.success = success;
            this.errorMessage = errorMessage;
            this.vendaId = vendaId;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public int getVendaId() {
            return vendaId;
        }
    }

    public SaveResult salvarVenda(VendaDto vendaDto) {
        String apiUrl = "http://localhost:8080/api/venda";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Criando o JSON manualmente
            String json = "{"
                + "\"observacoes\": \"" + vendaDto.getObservacoes() + "\","
                + "\"data\": \"" + vendaDto.getData() + "\","
                + "\"total\": " + vendaDto.getTotal() + ","
                + "\"cliente\": {"
                    + "\"id\": " + vendaDto.getCliente()
                + "}"
            + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Ler a resposta do servidor para obter o ID da venda criada
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Supondo que a resposta seja um JSON contendo o ID da venda
                JSONObject jsonResponse = new JSONObject(response.toString());
                int vendaId = jsonResponse.getInt("id");

                vendaDto.setId(vendaId); // Atualizar o objeto vendaDto com o ID da venda criada

                return new SaveResult(true, null, vendaId); // Sucesso, sem mensagem de erro, com o ID da venda
            } else {
                // Se houver erro, obtenha a mensagem de erro do servidor
                String errorMessage = connection.getResponseMessage();
                System.out.println("Erro ao salvar Venda: " + errorMessage);
                return new SaveResult(false, errorMessage, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SaveResult(false, e.getMessage(), -1);
        }
    }

    public SaveResult salvarItemVenda(ItemVendaDto itemVendaDto) {
        String apiUrl = "http://localhost:8080/api/itemVenda";

        try {
            // Loop through each item in the list of items
            for (ItemVenda item : itemVendaDto.getItens()) {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create JSON for the current item of sale
                String json = "{"
                    + "\"quantidade\": " + item.getQuantidade() + ","
                    + "\"valorUnitario\": " + item.getValorUnitario() + ","
                    + "\"valorTotal\": " + item.getValorTotal() + ","
                    + "\"venda\": {"
                        + "\"id\": " + itemVendaDto.getVenda().getId() + ","
                        + "\"observacoes\": \"" + itemVendaDto.getVenda().getObservacoes() + "\","
                        + "\"data\": \"" + itemVendaDto.getVenda().getData() + "\","
                        + "\"total\": " + itemVendaDto.getVenda().getTotal() + ","
                        + "\"cliente\": {"
                            + "\"id\": " + itemVendaDto.getVenda().getCliente()
                        + "}"
                    + "},"
                    + "\"produto\": {"
                        + "\"id\": " + item.getProduto()
                    + "}"
                + "}";

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    // If there is an error, get the error message from the server
                    String errorMessage = connection.getResponseMessage();
                    System.out.println("Erro ao salvar Item de Venda: " + errorMessage);
                    return new SaveResult(false, errorMessage, -1);
                }

                // Close the connection for the current item
                connection.disconnect();
            }
            return new SaveResult(true, null, -1); // Success, no error message

        } catch (Exception e) {
            e.printStackTrace();
            return new SaveResult(false, e.getMessage(), -1);
        }
    }
}
