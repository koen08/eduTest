package com.dictionary;

import java.net.ServerSocket;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class ConsumerDictionary implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private Set<String> setWords;
    public volatile boolean stop = true;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        setWords = new TreeSet<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (stop || !blockingQueue.isEmpty()) {
                    String word = blockingQueue.take();
                    setWords.add(word);
                    System.out.println(stop);
                } else break;
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
