package com.siberteam.koen.dictionary;

import java.util.Deque;
import java.util.concurrent.*;

public class ThreadManager {
    private ConsumerDictionary consumerDictionary;
    private ExecutorService threadConsumer;
    private ExecutorService threadPool;
    private final Deque<String> urls;
    private final byte countThread;

    public ThreadManager(Deque<String> urls,
                         byte countThread) {
        this.urls = urls;
        this.countThread = countThread;
    }

    public void startThread() {
        BlockingQueue<String> wordQueue = new ArrayBlockingQueue<>(1024);
        consumerDictionary = new ConsumerDictionary(wordQueue);
        threadConsumer.execute(consumerDictionary);
        threadConsumer = Executors.newSingleThreadExecutor();
        threadPool = Executors.newFixedThreadPool(countThread);
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        for (int i = 0; i < countThread; i++) {
            threadPool.execute(new ProducerDictionary(wordQueue, urls, countDownLatch));
        }
        try {
            countDownLatch.await();
            wordQueue.put("STOP");
        } catch (InterruptedException interruptedException) {
            LoggerConsole.logError("Thread was interrupted");
        }
    }

    public void finishThread() {
        threadPool.shutdown();
        threadConsumer.shutdown();
        LoggerConsole.logMessage("Threads were closed");
    }

    public ConsumerDictionary getConsumerDictionary() {
        return consumerDictionary;
    }
}
