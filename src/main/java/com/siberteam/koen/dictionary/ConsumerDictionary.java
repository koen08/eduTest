package com.siberteam.koen.dictionary;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class ConsumerDictionary implements Runnable {
    private final BlockingQueue<String> blockingQueue;
    private final Set<String> setWords;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        setWords = new TreeSet<>();
    }

    @Override
    public void run() {
        try {
            String word;
            while (!(word = blockingQueue.take()).equals("STOP")) {
                setWords.add(word);
            }
        } catch (InterruptedException interruptedException) {
            LoggerError.log("Thread was interrupted", interruptedException);
        }
    }

    public Set<String> getSetWords() {
        return setWords;
    }

}
