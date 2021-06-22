package com.siberteam.koen.test.dictionary;

import com.siberteam.koen.dictionary.ConsumerDictionary;
import com.siberteam.koen.common.LoggerConsole;
import com.siberteam.koen.dictionary.ProducerDictionary;
import com.siberteam.koen.dictionary.UrlStreamWorker;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ConsumerTest {

    @Test
    public void consumerDictionaryIsSuccess() {
        try {
            File file = new File("src/main/resources/input.txt");
            UrlStreamWorker urlStreamWorker = new UrlStreamWorker("file:///" + file.getAbsolutePath());
            BlockingQueue<String> wordsQueue = new ArrayBlockingQueue(1024);
            ExecutorService threadConsumer = Executors.newSingleThreadExecutor();
            ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordsQueue);
            ProducerDictionary producerDictionary = new ProducerDictionary(wordsQueue,
                    null, null);
            threadConsumer.execute(consumerDictionary);
            String line;
            while ((line = urlStreamWorker.getLineFromUrlFile()) != null) {
                Thread.sleep(5000);
                producerDictionary.putAllTheWordsLineInQueue(new StringTokenizer(line));
            }
            urlStreamWorker.closeFileReaderWithUrl();
            assertEquals(getSetWordsExpected(), consumerDictionary.getSetWords());
        } catch (Exception e) {
            LoggerConsole.logError(e.getMessage());
        }
    }

    private Set<String> getSetWordsExpected() {
        Set<String> setWordsExpected = new HashSet<>();
        setWordsExpected.add("второе");
        setWordsExpected.add("первое");
        setWordsExpected.add("слово");
        return setWordsExpected;
    }

    @Test
    public void producerTakeTokenTest() {
        try {
            BlockingQueue<String> blockingQueueWords = new ArrayBlockingQueue<>(10);
            ProducerDictionary producerDictionary = new ProducerDictionary(blockingQueueWords,
                    null, null);
            producerDictionary.putAllTheWordsLineInQueue(
                    new StringTokenizer("Привет как тв,,,,...ои де!!!ла.", " \t\n\r,."));
            blockingQueueWords.put("STOP");
            String word;
            Set<String> setWords = new HashSet<>();
            while (!(word = blockingQueueWords.take()).equals("STOP")) {
                setWords.add(word);
            }
            assertEquals(getBlockQueueWithResultsWords(), setWords);
        } catch (InterruptedException e) {
            LoggerConsole.logError(e.getMessage());
        }
    }

    private Set<String> getBlockQueueWithResultsWords() {
        Set<String> setWords = new HashSet<>(2);
        setWords.add("привет");
        setWords.add("как");
        return setWords;
    }
}
