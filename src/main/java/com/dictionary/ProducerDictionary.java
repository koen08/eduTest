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
        char characterLine;
        int pointerCharacterLine = 0;
        String word = "";
        for (int i = 0; i < line.length(); i++) {
            characterLine = line.charAt(i);
            if (!Character.isLetterOrDigit(characterLine) &&
                    !Character.isWhitespace(characterLine)) {
                line = line.replace(characterLine, ' ');
                characterLine = line.charAt(i);
            }
            if (Character.isWhitespace(characterLine)) {
                word = line.substring(pointerCharacterLine, i);
                pointerCharacterLine = i + 1;
            }
            if (line.length() - 1 == i && word.equals("")) {
                word = line.substring(pointerCharacterLine, i + 1);
            }
            word = word.toLowerCase();
            if (word.length() >= 3 && checkWordIntoCorrect(word)) {
                wordQueue.add(word);
                word = "";
            }
        }
    }

    private boolean checkWordIntoCorrect(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) < 224) {
                return false;
            }
        }
        return true;
    }
}
