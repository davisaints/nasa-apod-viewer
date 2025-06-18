package org.spaceviewer.apod.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import org.spaceviewer.apod.model.ApodResponse;

public class ApodClient {
    public ApodClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ApodResponse fetchApodData() throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");

        String APOD_API_URL = "https://api.nasa.gov/planetary/apod";
        URI uri = URI.create(APOD_API_URL + "?api_key=" + apiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper.readValue(response.body(), ApodResponse.class);
    }

    private final HttpClient httpClient;
}
