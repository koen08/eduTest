package com.siberteam.koen.dictionary;

import java.util.Deque;
import java.util.concurrent.*;

public class ThreadManager {
    private ExecutorService threadConsumer;
    private ExecutorService threadPool;
    private final Deque<String> urls;
    private final byte countThread;

    public ThreadManager(Deque<String> urls,
                         byte countThread) {
        this.urls = urls;
        this.countThread = countThread;
    }

    private ConsumerDictionary consumerDictionary;

    public void startThread() {
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        BlockingQueue<String> wordQueue = new ArrayBlockingQueue<>(1024);
        consumerDictionary = new ConsumerDictionary(wordQueue);
        threadPool = Executors.newFixedThreadPool(countThread);
        threadConsumer = Executors.newSingleThreadExecutor();
        threadConsumer.execute(consumerDictionary);
        for (int i = 0; i < countThread; i++) {
            threadPool.execute(new ProducerDictionary(
                    wordQueue,
                    urls,
                    countDownLatch
            ));
        }
        try {
            countDownLatch.await();
            wordQueue.put("STOP");
        } catch (InterruptedException interruptedException) {
            LoggerError.log("Thread was interrupted", interruptedException);
        }
    }

    public void finishThread() {
        threadPool.shutdown();
        threadConsumer.shutdown();
        System.out.println("Threads were closed");
    }

    public ConsumerDictionary getConsumerDictionary() {
        return consumerDictionary;
    }
}
