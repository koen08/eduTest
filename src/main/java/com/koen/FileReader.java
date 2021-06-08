package com.koen;

import java.io.*;

public class FileReader implements AutoCloseable {
    private Reader reader;

    public FileReader(InputStream inputStream) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LoggerError.log("Unsupported encoding ", e);
        }
    }

    public int getCharacter() {
        try {
            return reader.read();
        } catch (IOException e) {
            LoggerError.log("File can not be read", e);
        }
        return -1;
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
