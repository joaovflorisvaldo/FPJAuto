package Service;

import dto.ItemVendaDto;
import dto.VendaDto;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*@author joaov*/
public class ServiceVenda {
    
    public class SaveResult {
        private boolean success;
        private String errorMessage;

        public SaveResult(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMessage() {
            return errorMessage;
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
                return new SaveResult(true, null); // Sucesso, sem mensagem de erro
            } else {
                // Se houver erro, obtenha a mensagem de erro do servidor
                String errorMessage = connection.getResponseMessage();
                System.out.println("Erro ao salvar Venda: " + errorMessage);
                return new SaveResult(false, errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SaveResult(false, e.getMessage());
        }
    }    
    
        public SaveResult salvarItemVenda(ItemVendaDto itemVendaDto) {
        String apiUrl = "http://localhost:8080/api/itemVenda";
        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Serializando o objeto ItemVendaDto para JSON
            String json = "{"
                + "\"quantidade\": " + itemVendaDto.getQuantidade() + ","
                + "\"valorUnitario\": " + itemVendaDto.getValorUnitario() + ","
                + "\"valorTotal\": " + itemVendaDto.getValorTotal() + ","
                + "\"venda\": {"
                    + "\"id\": " + itemVendaDto.getVenda().getId() + ","
                    + "\"cliente\": {"
                        + "\"id\": " + itemVendaDto.getVenda().getCliente().getId()
                    + "}"
                + "},"
                + "\"produto\": {"
                    + "\"id\": " + itemVendaDto.getProduto().getId()
                + "}"
            + "}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new SaveResult(true, null); // Sucesso, sem mensagem de erro
            } else {
                // Se houver erro, obtenha a mensagem de erro do servidor
                String errorMessage = connection.getResponseMessage();
                System.out.println("Erro ao salvar Item de Venda: " + errorMessage);
                return new SaveResult(false, errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SaveResult(false, e.getMessage());
        }
    }
}
