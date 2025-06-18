package org.spaceviewer.apod.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.spaceviewer.apod.api.ApodClient;
import org.spaceviewer.apod.model.ApodResponse;

public class ApodService {
    public ApodService(ApodClient apodClient) {
        this.apodClient = apodClient;
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

    private final ApodClient apodClient;
}
