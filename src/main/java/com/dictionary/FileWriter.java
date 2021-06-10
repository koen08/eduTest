package com.dictionary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileWriter implements AutoCloseable {
    private final BufferedWriter bufferedWriter;

    public FileWriter(OutputStream outputStream) {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void writeTextLine(String textLine) {
        try {
                bufferedWriter.write(textLine + System.lineSeparator());
        } catch (IOException e) {
            LoggerError.log("File can not be write", e);
        }
    }

    @Override
    public void close() {
        try {
            bufferedWriter.close();
        } catch (Exception e) {
            LoggerError.log("Stream can not be close", e);
        }
    }
}
