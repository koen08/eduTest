package com.koen;

import java.io.*;

public class FileReader {
    private Reader reader;

    public FileReader(InputStream inputStream) {
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
            System.out.println("File can not be read");
            e.printStackTrace();
        }
        return -1;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Stream can not be close");
            e.printStackTrace();
        }
    }
}
