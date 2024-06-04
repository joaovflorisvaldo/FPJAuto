package Service;
import com.google.gson.Gson;
import com.mycompany.desktop.Util;
import dto.ClienteDto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Felipe
 */
public class serviceWeb {
          
          private static String URLWEBSERVICE = "http://localhost:8080/swagger-ui/index.html";
          private static int SUCESSO = 200;
          
          public static ClienteDto buscaCliente(String cliente) throws Exception{
                    String urlChamada = URLWEBSERVICE + cliente + "/json";

                    try{
                              URL url = new URL(urlChamada); 
                              HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                              
                              if( conexao.getResponseCode() != SUCESSO){
                                        throw new RuntimeException("Erro ao conectar: "+ conexao.getResponseCode());
                              }

                              //pegando resposta da API
                              BufferedReader resposta = new BufferedReader(new InputStreamReader( conexao.getInputStream() ));
                              
                              String json = Util.converteJsonString(resposta);
                              
                              Gson gson = new Gson();
                              ClienteDto dto = gson.fromJson(json, ClienteDto.class);
                              return dto;
                              
                    }catch(Exception ex){
                              throw new Exception("Erro ao retornar cliente: " + ex);
                    }          
          }
          
}