package com.dictionary;

import java.util.concurrent.*;

public class ManagerThead {
    private BlockingQueue<String> wordQueue;
    private ExecutorService threadConsumer;
    private ExecutorService threadPool;
    private final ManagerFile managerFile;
    private final byte countThread;

    public ManagerThead(ManagerFile managerFile,
                        byte countThread) {
        this.managerFile = managerFile;
        this.countThread = countThread;
    }

    public void startThread() {
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        BlockingQueue<String> blockingQueue = managerFile.getStackUrl();
        wordQueue = new ArrayBlockingQueue(1024);
        ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordQueue);
        threadPool = Executors.newFixedThreadPool(countThread);
        threadConsumer = Executors.newSingleThreadExecutor();
        threadConsumer.execute(consumerDictionary);
        for (int i = 0; i < countThread; i++) {
            threadPool.execute(new ProducerDictionary(
                    wordQueue,
                    blockingQueue,
                    countDownLatch,
                    managerFile
            ));
        }
        try {
            countDownLatch.await();
            consumerDictionary.setStop(false);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void finishThread() {
        threadPool.shutdown();
        threadConsumer.shutdown();
        System.out.println("Threads were closed");
    }
}
