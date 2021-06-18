package com.siberteam.koen.test.dictionary;

import com.siberteam.koen.dictionary.ConsumerDictionary;
import com.siberteam.koen.dictionary.ProducerDictionary;
import com.siberteam.koen.dictionary.UrlStreamWorker;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ConsumerTest {

    @Test
    public void consumerDictionaryIsSuccess() {
        try {
            String pathFile = ConsumerTest.class.getResource("/1.txt").getPath();
            UrlStreamWorker urlStreamWorker = new UrlStreamWorker("file:///" + pathFile);
            BlockingQueue<String> wordsQueue = new ArrayBlockingQueue(1024);
            ExecutorService threadConsumer = Executors.newSingleThreadExecutor();
            ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordsQueue);
            ProducerDictionary producerDictionary = new ProducerDictionary(wordsQueue,
                    null, null);
            threadConsumer.execute(consumerDictionary);
            String line;
            while ((line = urlStreamWorker.getLineFromUrlFile()) != null) {
                Thread.sleep(5000);
                producerDictionary.putAllTheWordsLineInQueue(line);
            }
            urlStreamWorker.closeFileReaderWithUrl();
            assertEquals(getSetWordsExpected(), consumerDictionary.getSetWords());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> getSetWordsExpected() {
        Set<String> setWordsExpected = new HashSet<>();
        setWordsExpected.add("второе");
        setWordsExpected.add("первое");
        setWordsExpected.add("слово");
        return setWordsExpected;
    }
}
