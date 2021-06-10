package com.dictionary;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class ConsumerDictionary implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private Set<String> setWords;
    private Iterator<String> it;
    public static boolean stop = false;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        setWords = new TreeSet<>();
    }

    @Override
    public void run() {
        try {
            try (FileWriter fileWriter = new FileWriter(new FileOutputStream(ManagerFile.fileWrite))) {
                while (!stop && !blockingQueue.isEmpty()) {
                    String word = blockingQueue.take();
                    word = word.toLowerCase();
                    if (!word.matches("(.*[0-9]+.*)")) {
                        word = word.replaceAll("[^а-яё]", "");
                        if (word.matches("([а-яё]{3,})")) {
                            setWords.add(word);
                        }
                    }
                }
                it = setWords.iterator();
                while (it.hasNext()) {
                    fileWriter.writeTextLine(it.next());
                }
            } catch (InterruptedException interruptedException) {
                LoggerError.log("Thread was interrupt", interruptedException);
                Thread.currentThread().interrupt();
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File not found", e);
        }
    }
}
