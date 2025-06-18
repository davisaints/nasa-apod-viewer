package org.spaceviewer.apod;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.http.HttpClient;

import java.util.Arrays;

import org.spaceviewer.apod.api.ApodClient;
import org.spaceviewer.apod.model.ApodResponse;
import org.spaceviewer.apod.service.ApodService;

public class ApodCliApp {
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
        System.out.println("  -s, --set         set the image as wallpaper");
        System.out.println("  -v, --view        view the last downloaded image");
    }

    private static void _run(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        ApodClient apodClient = new ApodClient(httpClient);
        ApodService apodService = new ApodService(apodClient);

        ProcessBuilder builder = new ProcessBuilder();
        String option = args[0];
        switch (option) {
            case "--download":
            case "-d":
                ApodResponse apodResponse = apodService.getTodayApod();
                String imageURL = (apodResponse.hdurl().isEmpty() || apodResponse.hdurl().isBlank())
                        ? apodResponse.url()
                        : apodResponse.hdurl();

                builder.command(Arrays.asList("wget", imageURL));

                try {
                    Process process = builder.inheritIO().start();

                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        System.out.println("File downloaded successfully!");
                    }

                    if (exitCode > 0) {
                        System.err.println("Download failed with exit code: " + exitCode);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}