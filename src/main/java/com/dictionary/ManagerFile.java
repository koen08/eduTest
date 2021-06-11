package com.dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ManagerFile {

    private final String fileRead;
    public static String fileWrite;
    private final LineConverter lineConverter;

    public ManagerFile(String fileRead, String fileWrite) {
        this.fileRead = fileRead;
        this.fileWrite = fileWrite;
        lineConverter = new LineConverter();
    }

    public BlockingQueue<String> getStackUrl() {
        BlockingQueue<String> queueUrl = new ArrayBlockingQueue<>(1024);
        try {
            try (FileReader readerFile = new FileReader(new FileInputStream(fileRead))) {
                queueUrl = lineConverter.getListUrlFromFile(readerFile);
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File did not found", e);
        }
        return queueUrl;
    }

    public static void writeResultsToFile(Set<String> setWords) {
        try (FileWriter fileWriter = new FileWriter(new FileOutputStream(ManagerFile.fileWrite))) {
            for (String setWord : setWords) {
                fileWriter.writeTextLine(setWord);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLineFromUrlFile(String url){
        List<String> stringList = new LinkedList<>();
        try (FileReader readerFile = new FileReader(new URL(url).openStream())) {
            String line;
            while ((line = readerFile.getLine()) != null) {
                stringList.add(line);
            }
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
