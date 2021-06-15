package com.siberteam.koen.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileReader implements AutoCloseable {
    private BufferedReader reader;

    public FileReader(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public String getLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            LoggerError.log("File can not be read", e);
        }
        return null;
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            LoggerError.log("Stream can not be close", e);
        }
    }
}
