package org.spaceviewer.apod.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.IOException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import java.util.Properties;

import org.spaceviewer.apod.model.ApodResponse;

public class ApodClient {
    public ApodClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ApodResponse fetchApodData() throws IOException, InterruptedException {
        String APOD_API_URL = "https://api.nasa.gov/planetary/apod";
        URI uri = URI.create(APOD_API_URL + "?api_key=" + _getProperty("api.key"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper.readValue(response.body(), ApodResponse.class);
    }

    private String _getProperty(String propertyKey) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (input == null) {
            throw new IOException("config.properties not found in classpath.");
        }

        Properties props = new Properties();
        props.load(input);

        String apiKey = props.getProperty(propertyKey);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(propertyKey + " not set in config.properties");
        }

        return apiKey;
    }

    private final HttpClient httpClient;
}
