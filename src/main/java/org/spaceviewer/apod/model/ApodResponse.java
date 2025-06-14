package org.spaceviewer.apod.model;

public record ApodResponse(
        String copyright,
        String date,
        String explanation,
        String hdurl,
        String title,
        String url
) {
}
