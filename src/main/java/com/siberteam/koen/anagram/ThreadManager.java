package com.siberteam.koen.anagram;

import com.siberteam.koen.common.LoggerConsole;
import com.siberteam.koen.dictionary.ProducerDictionary;

import java.util.Deque;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class ThreadManager {
    private final Deque<String> urls;
    private final byte countThreadProducer;
    private final byte countThreadConsumer;
    private ExecutorService threadConsumer;
    private ExecutorService threadPool;
    private Map<String, Set<String>> anagrams;
    private CountDownLatch countLatchConsumer;

    public ThreadManager(Deque<String> urls,
                         byte countThreadProducer,
                         byte countThreadConsumer) {
        this.urls = urls;
        this.countThreadProducer = countThreadProducer;
        this.countThreadConsumer = countThreadConsumer;
    }

    public void startThread() {
        try {
            anagrams = new ConcurrentHashMap<>();
            BlockingQueue<String> wordQueue = new ArrayBlockingQueue<>(1024);
            threadConsumer = Executors.newFixedThreadPool(countThreadConsumer);
            threadPool = Executors.newFixedThreadPool(countThreadProducer);
            CountDownLatch counterForProducer = new CountDownLatch(countThreadProducer);
            for (int i = 0; i < countThreadProducer; i++) {
                threadPool.execute(new ProducerDictionary(wordQueue, urls, counterForProducer));
            }
            countLatchConsumer = new CountDownLatch(countThreadConsumer);
            for (int i = 0; i < countThreadConsumer; i++){
                threadConsumer.execute(new ConsumerDictionary(wordQueue, anagrams, countLatchConsumer));
            }
            counterForProducer.await();
            wordQueue.put("STOP");
        } catch (InterruptedException interruptedException) {
            LoggerConsole.logError("Thread was interrupted");
        }
    }

    public void finishThread() throws InterruptedException {
        countLatchConsumer.await();
        threadPool.shutdown();
        threadConsumer.shutdown();
        LoggerConsole.logMessage("Threads were closed");
    }

    public Map<String, Set<String>> getAnagrams() {
        return anagrams;
    }
}
