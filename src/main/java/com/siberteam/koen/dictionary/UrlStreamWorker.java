package com.siberteam.koen.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UrlStreamWorker {
    private final BufferedReader fileBufferedReader;

    public UrlStreamWorker(String url) throws IOException {
        this.fileBufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream(),
                StandardCharsets.UTF_8));
    }

    public String getLineFromUrlFile() throws IOException {
        return fileBufferedReader.readLine();
    }

    public void closeFileReaderWithUrl() throws IOException {
        fileBufferedReader.close();
    }
}
