package com.siberteam.koen.dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileManager {

    private final String fileRead;
    public static String fileWrite;

    public FileManager(String fileRead, String fileWrite) {
        this.fileRead = fileRead;
        this.fileWrite = fileWrite;
    }

    public BlockingQueue<String> getQueueUrl() {
        BlockingQueue<String> queueUrl = new ArrayBlockingQueue<>(1024);
        try {
            try (FileReader readerFile = new FileReader(new FileInputStream(fileRead))) {
                String line;
                while ((line = readerFile.getLine()) != null) {
                    queueUrl.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File did not found", e);
        }
        return queueUrl;
    }

    public void writeResultsToFile(Set<String> setWords) {
        try (FileWriter fileWriter = new FileWriter(new FileOutputStream(FileManager.fileWrite))) {
            for (String setWord : setWords) {
                fileWriter.writeTextLine(setWord);
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File not found", e);
        }
    }

    public List<String> getLineFromUrlFile(String url) {
        List<String> stringList = new LinkedList<>();
        try (FileReader readerFile = new FileReader(new URL(url).openStream())) {
            String line;
            while ((line = readerFile.getLine()) != null) {
                stringList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LoggerError.log("I can not get line from url file", e);
        }
        return stringList;
    }
}
