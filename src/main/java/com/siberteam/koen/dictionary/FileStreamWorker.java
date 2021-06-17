package com.siberteam.koen.dictionary;

import java.io.*;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FileStreamWorker {
    private final String fileReaderName;
    public final String fileWriterName;

    public FileStreamWorker(String fileRead, String fileWrite) {
        this.fileReaderName = fileRead;
        this.fileWriterName = fileWrite;
    }

    public Deque<String> getStackUrl() throws IOException {
        Deque<String> urls = new ConcurrentLinkedDeque<>();
        try (BufferedReader readerFile = new BufferedReader(new FileReader(fileReaderName))) {
            String line;
            while ((line = readerFile.readLine()) != null) {
                urls.push(line);
            }
        }
        return urls;
    }

    public void writeResultsToFile(Set<String> setWords) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileWriterName))) {
            for (String setWord : setWords) {
                fileWriter.write(setWord + System.lineSeparator());
            }
        }
    }
}
