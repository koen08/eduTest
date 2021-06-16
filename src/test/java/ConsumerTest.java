import com.siberteam.koen.dictionary.ConsumerDictionary;
import com.siberteam.koen.dictionary.FileManager;
import com.siberteam.koen.dictionary.ProducerDictionary;
import jdk.nashorn.internal.ir.Block;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
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
            FileManager fileManager = new FileManager("", "");
            BlockingQueue<String> wordQueue = new ArrayBlockingQueue(1024);
            BlockingQueue<String> urlQueue = new ArrayBlockingQueue<>(1024);
            urlQueue.put("file:///home/koen/IdeaProjects/eduTest/src/main/resources/1.txt");
            ExecutorService threadConsumer = Executors.newSingleThreadExecutor();
            ExecutorService threadPool = Executors.newFixedThreadPool(1);
            ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordQueue, null);
            ProducerDictionary producerDictionary = new ProducerDictionary(
                    wordQueue,
                    urlQueue,
                    null,
                    null
            );
            threadConsumer.execute(consumerDictionary);
            List<String> listLine = fileManager.getLineFromUrlFile(urlQueue.take());
            for (String line : listLine) {
                producerDictionary.putAllTheWordsLineInQueue(line);
                Thread.sleep(5000);
            }
            assertEquals(getSetWordsExpected(), consumerDictionary.getSetWords());
        } catch (InterruptedException e) {
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
