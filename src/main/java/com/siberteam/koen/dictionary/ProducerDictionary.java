package com.siberteam.koen.dictionary;

import com.siberteam.koen.common.LoggerConsole;

import java.util.Deque;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class ProducerDictionary implements Runnable {
    private final BlockingQueue<String> wordsQueue;
    private final Deque<String> urls;
    private final CountDownLatch countDownLatch;

    public ProducerDictionary(BlockingQueue<String> wordsQueue,
                              Deque<String> urls,
                              CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        this.wordsQueue = wordsQueue;
        this.urls = urls;
    }

    @Override
    public void run() {
        try {
            String url;
            while ((url = urls.poll()) != null) {
                UrlStreamWorker urlStreamWorker = new UrlStreamWorker(url);
                String line;
                while ((line = urlStreamWorker.getLineFromUrlFile()) != null) {
                    putAllTheWordsLineInQueue(new StringTokenizer(line, " \t\n\r,."));
                }
                urlStreamWorker.closeFileReaderWithUrl();
            }
            countDownLatch.countDown();
        } catch (Exception e) {
            LoggerConsole.logError(e.getMessage());
            countDownLatch.countDown();
        }
    }

    public void putAllTheWordsLineInQueue(StringTokenizer tokenizer) throws InterruptedException {
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            if (word.matches("[а-яё]*") && word.length() >= 3) {
                wordsQueue.put(word);
            }
        }
    }
}
