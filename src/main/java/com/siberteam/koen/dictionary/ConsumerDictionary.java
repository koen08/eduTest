package com.siberteam.koen.dictionary;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class ConsumerDictionary implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private Set<String> setWords;
    public volatile boolean stop = false;
    private FileManager fileManager;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue, FileManager fileManager) {
        this.blockingQueue = blockingQueue;
        this.fileManager = fileManager;
        setWords = new TreeSet<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!blockingQueue.isEmpty()) {
                    String word = blockingQueue.take();
                    setWords.add(word);
                } else if (stop) break;
            }
            fileManager.writeResultsToFile(setWords);
        } catch (InterruptedException interruptedException) {
            LoggerError.log("Thread was interrupted", interruptedException);
            Thread.currentThread().interrupt();
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
