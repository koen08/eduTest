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
        ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordQueue);
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        BlockingQueue<String> blockingQueue = managerFile.getStackUrl();
        wordQueue = new ArrayBlockingQueue(1024);
        threadPool = Executors.newFixedThreadPool(countThread);
        threadConsumer = Executors.newSingleThreadExecutor();
        threadConsumer.execute(new ConsumerDictionary(wordQueue));
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
            consumerDictionary.setStop(true);
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
