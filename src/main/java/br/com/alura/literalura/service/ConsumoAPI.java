package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    private final HttpClient httpClient;

    public ConsumoAPI() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String obterDados(String urlBase) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBase))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Erro na requisição: Código " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao consumir a API: " + e.getMessage());
            return null;
        }
    }
}
