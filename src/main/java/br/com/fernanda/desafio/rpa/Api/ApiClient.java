package br.com.fernanda.desafio.rpa.Api;

import br.com.fernanda.desafio.rpa.Automation.Feriado;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiClient {

    private static final String API_URL = "https://spprev.ambientalqvt.com.br/api/dinamico/avaliacao-vaga/registrar-feriados";
    private static final String BEARER_TOKEN = "0a811779-1973-4c12-bf3d-ec01b75ce61a";

    public void sendFeriados(List<Feriado> feriados) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        Gson gson = new Gson();


        JsonObject requestObject = new JsonObject();
        JsonArray feriadosArray = new JsonArray();

        for (Feriado f : feriados) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("estado", f.getEstado());
            jsonObject.addProperty("cidade", f.getCidade());
            jsonObject.addProperty("data", f.getData().toString());
            jsonObject.addProperty("tipo", f.getTipo());
            jsonObject.addProperty("feriado", f.getFeriado());

            feriadosArray.add(jsonObject);
        }

        requestObject.add("feriados", feriadosArray);

        String jsonString = gson.toJson(requestObject);

        // Print the JSON object that will be sent
        System.out.println("Enviando para a API: " + jsonString);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            // Lidar com exceções de entrada/saída que podem ocorrer
            System.err.println("Ocorreu um erro ao tentar obter o OutputStream ou ao escrever dados: " + e.getMessage());
            e.printStackTrace(); // Opcional: imprimir o stack trace para depuração
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream is = conn.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Resposta da API: " + response.toString());
                System.out.println("Envio bem-sucedido.");
            } catch (IOException e) {
                // Lidar com exceções de entrada/saída que podem ocorrer
                System.err.println("Ocorreu um erro ao tentar obter o InputStream ou ao ler dados: " + e.getMessage());
                e.printStackTrace(); // Opcional: imprimir o stack trace para depuração
            }
        } else {
            try (InputStream is = conn.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Resposta da API: " + response.toString());
            } catch (IOException e) {
                // Lidar com exceções de entrada/saída que podem ocorrer
                System.err.println("Ocorreu um erro ao tentar obter o InputStream de erro ou ao ler dados: " + e.getMessage());
                e.printStackTrace(); // Opcional: imprimir o stack trace para depuração
            }
            System.out.println("Falha no envio. Código de resposta: " + responseCode);
        }
    }
}
