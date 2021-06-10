package com.dictionary;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManagerThead {
    private BlockingQueue<String> blockingQueue;
    private BlockingQueue<String> stackUrl;
    private byte countThread;

    public ManagerThead(BlockingQueue<String> stackUrl,
                        byte countThread) {
        this.blockingQueue = new ArrayBlockingQueue(1024);
        this.stackUrl = stackUrl;
        this.countThread = countThread;
    }

    public void startThread() {
        ExecutorService threadPool =
                Executors.newFixedThreadPool(countThread);
        for (int i = 0; i < countThread; i++) {
            threadPool.execute(new ProducerDictionary(blockingQueue, stackUrl));
        }
        ExecutorService threadConsumer =
                Executors.newSingleThreadExecutor();
        threadConsumer.execute(new ConsumerDictionary(blockingQueue));
        threadPool.shutdown();
        if (threadPool.isTerminated()){
            ConsumerDictionary.stop = true;
            threadConsumer.shutdown();
        }
    }
}
