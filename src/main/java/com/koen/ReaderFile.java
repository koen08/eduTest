package com.koen;

import java.io.*;

public class ReaderFile {
    private Reader reader;

    public ReaderFile(InputStream inputStream) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public int getCharacter() {
        try {
            return reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
