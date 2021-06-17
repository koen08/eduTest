package com.siberteam.koen.dictionary;

import java.io.IOException;
import java.util.Deque;
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
                    putAllTheWordsLineInQueue(line);
                }
                urlStreamWorker.closeFileReaderWithUrl();
            }
            countDownLatch.countDown();
        } catch (IOException e) {
            LoggerConsole.logError(e.getMessage());
            countDownLatch.countDown();
        }
    }

    public void putAllTheWordsLineInQueue(String line) {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char symbolWord = line.charAt(i);
            if (Character.isLetterOrDigit(symbolWord) && symbolWord >= 224) {
                word.append(symbolWord);
            }
            if (Character.isWhitespace(symbolWord)
                    || isPunctuation(symbolWord)
                    || line.length() - 1 == i) {
                if (checkWordIntoCorrect(word)) {
                    try {
                        wordsQueue.put(word.toString().toLowerCase());
                    } catch (InterruptedException e) {
                        LoggerConsole.logError("Thread was interrupted");
                    }
                }
                word.setLength(0);
            }
        }
    }

    private boolean isPunctuation(char symbolWord) {
        return symbolWord >= 33 && symbolWord <= 47;
    }

    private boolean checkWordIntoCorrect(StringBuilder word) {
        if (word.length() < 3) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) < 224) {
                return false;
            }
        }
        return true;
    }
}
