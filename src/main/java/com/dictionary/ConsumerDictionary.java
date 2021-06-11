package com.dictionary;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class ConsumerDictionary implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private Set<String> setWords;
    private boolean stop = false;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        setWords = new TreeSet<>();
    }

    @Override
    public void run() {
        try {
            while (!stop || !blockingQueue.isEmpty()) {
                String word = blockingQueue.take();
                setWords.add(word);
            }
            System.out.println("Я сюда пришел");
            ManagerFile.writeResultsToFile(setWords);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
