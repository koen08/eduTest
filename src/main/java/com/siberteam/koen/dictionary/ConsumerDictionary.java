package com.siberteam.koen.dictionary;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class ConsumerDictionary implements Runnable {
    private final BlockingQueue<String> blockingQueue;
    private final Set<String> setWords;
    private final FileManager fileManager;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue, FileManager fileManager) {
        this.blockingQueue = blockingQueue;
        this.fileManager = fileManager;
        setWords = new TreeSet<>();
    }

    @Override
    public void run() {
        try {
            String word;
            while (!(word = blockingQueue.take()).equals("STOP")) {
                setWords.add(word);
            }
            if (fileManager != null) fileManager.writeResultsToFile(setWords);
        } catch (InterruptedException interruptedException) {
            LoggerError.log("Thread was interrupted", interruptedException);
            Thread.currentThread().interrupt();
        }
    }

    public Set<String> getSetWords() {
        return setWords;
    }

}
