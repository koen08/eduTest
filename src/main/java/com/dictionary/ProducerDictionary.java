package com.dictionary;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class ProducerDictionary implements Runnable {
    private final BlockingQueue<String> wordQueue;
    private final BlockingQueue<String> urlQueue;
    private CountDownLatch countDownLatch;
    private final ManagerFile managerFile;

    public ProducerDictionary(BlockingQueue<String> wordQueue,
                              BlockingQueue<String> urlQueue,
                              CountDownLatch countDownLatch,
                              ManagerFile managerFile) {
        this.managerFile = managerFile;
        this.countDownLatch = countDownLatch;
        this.wordQueue = wordQueue;
        this.urlQueue = urlQueue;
    }

    @Override
    public void run() {
        while (!urlQueue.isEmpty()) {
            try {
                List<String> listLine = managerFile.getLineFromUrlFile(urlQueue.take());
                for (String line : listLine) {
                    putAllTheWordsLineInQueue(line);
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        countDownLatch.countDown();
    }

    private void putAllTheWordsLineInQueue(String line) {
        char symbolWord;
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            symbolWord = line.charAt(i);
            if (Character.isLetterOrDigit(symbolWord)) {
                word.append(symbolWord);
            }
            if (Character.isWhitespace(symbolWord) ||
                    isPunctuation(symbolWord) ||
                    line.length() - 1 == i) {
                if (checkWordIntoCorrect(word)){
                    try {
                        wordQueue.put(word.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
