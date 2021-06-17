package com.siberteam.koen.dictionary;

import java.io.IOException;
import java.net.URL;

public class UrlStreamWorker {
    private FileBufferedReader fileBufferedReader;

    public UrlStreamWorker(String url) {
        try {
            this.fileBufferedReader = new FileBufferedReader(new URL(url).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLineFromUrlFile() {
        String line = "";
        line = fileBufferedReader.getLine();
        return line;
    }
    public void closeFileReaderWithUrl() {
        fileBufferedReader.close();
    }
}
