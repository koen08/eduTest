package com.dictionary;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LineConverter {
    public BlockingQueue<String> getListUrlFromFile(FileReader fileReader) {
        BlockingQueue<String> listUrl = new ArrayBlockingQueue<>(1024);
        String line;
        while ((line = fileReader.getLine()) != null) {
            listUrl.add(line);
        }
        return listUrl;
    }

    public void putLineToQueue(String[] words, BlockingQueue<String> queueWord) {
        for (String str : words) {
            try {
                queueWord.put(str);
            } catch (InterruptedException interruptedException) {
                LoggerError.log("Thread was interrupt", interruptedException);
                Thread.currentThread().interrupt();
            }
        }
    }

    public String[] convertLineTextToWords(String line) {
        return line.split(" ");
    }
}
