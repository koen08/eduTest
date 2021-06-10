package com.dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
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

    public String getFileWrite() {
        return fileWrite;
    }
}
