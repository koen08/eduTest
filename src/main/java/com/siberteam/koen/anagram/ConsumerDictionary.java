package com.siberteam.koen.anagram;

import com.siberteam.koen.common.LoggerConsole;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ConsumerDictionary implements Runnable {
    private final BlockingQueue<String> blockingQueue;
    private final Map<String, Set<String>> anagrams;
    private final CountDownLatch countDownLatch;

    public ConsumerDictionary(BlockingQueue<String> blockingQueue,
                              Map<String, Set<String>> anagrams,
                              CountDownLatch countDownLatch) {
        this.blockingQueue = blockingQueue;
        this.anagrams = anagrams;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            String word;
            while (!(word = blockingQueue.take()).equals("STOP")) {
                anagrams.computeIfAbsent(takeKey(word), setAnagram -> ConcurrentHashMap.newKeySet()).add(word);
            }
            blockingQueue.put("STOP");
            countDownLatch.countDown();
        } catch (InterruptedException interruptedException) {
            LoggerConsole.logError("Thread was interrupted");
        }
    }

    private String takeKey(String word){
        char[] wordCharacter = word.toCharArray();
        Arrays.sort(wordCharacter);
        return new String(wordCharacter);
    }
}
