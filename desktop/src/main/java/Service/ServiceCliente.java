package Service;
import com.google.gson.Gson;
import com.mycompany.desktop.Util;
import dto.ClienteDto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class ServiceCliente {
          
            private static final String URL_WEBSERVICE = "http://localhost:8080/api/cliente";
            private static final int HTTP_SUCESSO = 200;

            public static List<ClienteDto> obterClientesDaAPI() throws Exception {
            String urlChamada = URL_WEBSERVICE;

            try {
              URL url = new URL(urlChamada);
              HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

              if (conexao.getResponseCode() != HTTP_SUCESSO) {
                  throw new RuntimeException("Erro ao conectar: " + conexao.getResponseCode());
              }

              try (BufferedReader resposta = new BufferedReader(new InputStreamReader(conexao.getInputStream()))) {
                  String json = lerConteudo(resposta);

                  Gson gson = new Gson();
                  ClienteDto[] clientesArray = gson.fromJson(json, ClienteDto[].class);

                  // Convertendo o array de clientes para uma lista
                  List<ClienteDto> clientes = new ArrayList<>();
                  for (ClienteDto cliente : clientesArray) {
                      clientes.add(cliente);
                  }

                  return clientes;
              }
            } catch (Exception ex) {
              throw new Exception("Erro ao retornar clientes da API: " + ex.getMessage());
            }
            }

            private static String lerConteudo(BufferedReader reader) throws Exception {
            StringBuilder conteudo = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
              conteudo.append(linha);
            }

            return conteudo.toString();
            }
          
}