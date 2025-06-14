package org.spaceviewer.apod.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApodResponse(
        String copyright,
        String date,
        String explanation,
        String hdurl,
        @JsonProperty("media_type")
        String mediaType,
        String title,
        String url
) {
}
