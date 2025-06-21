package org.spaceviewer.apod;

import org.spaceviewer.apod.api.ApodClient;
import org.spaceviewer.apod.model.ApodResponse;
import org.spaceviewer.apod.service.ApodService;

import java.io.IOException;
import java.net.http.HttpClient;

public class ApodCliApp {
    private static void _downloadImage(String imageURL) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("wget", "--quiet", imageURL);

        int exitCode = processBuilder.start().waitFor();

        if (exitCode == 0) {
            System.out.println("File successfully downloaded to the current directory!");
        } else {
            throw new RuntimeException("Download failed with status code: " + exitCode);
        }
    }

    private static String getPreferredImageUrl(ApodResponse apodResponse) {
        String hdurl = apodResponse.hdurl();
        String url = apodResponse.url();

        if (hdurl != null && !hdurl.isBlank()) {
            return hdurl;
        }

        if (url != null && !url.isBlank()) {
            return url;
        }

        throw new IllegalStateException("No valid image URL found in the APOD response.");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            _printHelp();
            return;
        }
        _run(args);
    }

    private static void _printHelp() {
        System.out.println("Usage: java -jar apod-cli.jar [OPTION]...");
        System.out.println();
        System.out.println("Mandatory arguments to long options are mandatory for short options too.");
        System.out.println("  -d, --download    download the APOD image");
        System.out.println("  -v, --view        view the last downloaded image");
    }

    private static void _run(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        ApodClient apodClient = new ApodClient(httpClient);
        ApodService apodService = new ApodService(apodClient);

        ApodResponse apodResponse = apodService.getTodayApod();

        String imageURL = getPreferredImageUrl(apodResponse);

        String option = args[0];
        switch (option) {
            case "--download":
            case "-d":
                _downloadImage(imageURL);
            case "--view":
            case "-v":
                _viewImage(imageURL);
            }
        }

    private static void _viewImage(String imageURL) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("xdg-open", imageURL);

        int exitCode = processBuilder.start().waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Failed to open image viewer. Exit code: " + exitCode);
        }
    }
}