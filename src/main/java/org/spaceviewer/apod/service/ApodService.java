package org.spaceviewer.apod.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.spaceviewer.apod.api.ApodClient;
import org.spaceviewer.apod.model.ApodResponse;

public class ApodService {
    private final ApodClient apodClient;
    private final ObjectMapper objectMapper;

    public ApodService(ApodClient apodClient, ObjectMapper objectMapper) {
        this.apodClient = apodClient;
        this.objectMapper = objectMapper;
    }

    public ApodResponse getTodayApod() throws IOException, InterruptedException {
        ApodResponse apodResponse = apodClient.fetchApodData();

        if (apodResponse == null) {
            throw new IllegalStateException("APOD response is null");
        }

        if (!"image".equals(apodResponse.mediaType())) {
            throw new IllegalStateException("APOD is not an image. Media type: " + apodResponse.mediaType());
        }

        return apodResponse;
    }
}
