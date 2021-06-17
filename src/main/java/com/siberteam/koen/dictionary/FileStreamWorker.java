package com.siberteam.koen.dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayDeque;
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

    public Deque<String> getStackUrl() {
        Deque<String> urls = new ConcurrentLinkedDeque<>();
        try {
            try (FileBufferedReader readerFile = new FileBufferedReader(new FileInputStream(fileReaderName))) {
                String line;
                while ((line = readerFile.getLine()) != null) {
                    urls.push(line);
                }
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File did not found", e);
        }
        return urls;
    }

    public void writeResultsToFile(Set<String> setWords) {
        try (FileBufferedWriter fileWriter = new FileBufferedWriter(new FileOutputStream(fileWriterName))) {
            for (String setWord : setWords) {
                fileWriter.writeTextLine(setWord);
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File not found", e);
        }
    }

}
