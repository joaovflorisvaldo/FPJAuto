/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import com.google.gson.Gson;
import dto.ProdutoDto;
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
public class ServiceProduto {
    
    private static final String URL_WEBSERVICE = "http://localhost:8080/api/produto";
            private static final int HTTP_SUCESSO = 200;

            public static List<ProdutoDto> obterProdutosDaAPI() throws Exception {
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
                  ProdutoDto[] produtosArray = gson.fromJson(json, ProdutoDto[].class);

                  // Convertendo o array de produtos para uma lista
                  List<ProdutoDto> produtos = new ArrayList<>();
                  for (ProdutoDto produto : produtosArray) {
                      produtos.add(produto);
                  }

                  return produtos;
              }
            } catch (Exception ex) {
              throw new Exception("Erro ao retornar produtos da API: " + ex.getMessage());
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
