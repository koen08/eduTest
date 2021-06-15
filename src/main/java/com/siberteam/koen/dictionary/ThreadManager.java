package com.siberteam.koen.dictionary;

import java.util.concurrent.*;

public class ThreadManager {
    private BlockingQueue<String> wordQueue;
    private ExecutorService threadConsumer;
    private ExecutorService threadPool;
    private final FileManager fileManager;
    private final byte countThread;

    public ThreadManager(FileManager fileManager,
                         byte countThread) {
        this.fileManager = fileManager;
        this.countThread = countThread;
    }

    public void startThread() {
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        BlockingQueue<String> queueUrl = fileManager.getQueueUrl();
        wordQueue = new ArrayBlockingQueue(1024);
        ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordQueue, fileManager);
        threadPool = Executors.newFixedThreadPool(countThread);
        threadConsumer = Executors.newSingleThreadExecutor();
        threadConsumer.execute(consumerDictionary);
        for (int i = 0; i < countThread; i++) {
            threadPool.execute(new ProducerDictionary(
                    wordQueue,
                    queueUrl,
                    countDownLatch,
                    fileManager
            ));
        }
        try {
            countDownLatch.await();
            consumerDictionary.setStop(true);
        } catch (InterruptedException interruptedException) {
            LoggerError.log("Thread was interrupted", interruptedException);
            Thread.currentThread().interrupt();
        }
    }

    public void finishThread() {
        threadPool.shutdown();
        threadConsumer.shutdown();
        System.out.println("Threads were closed");
    }
}
