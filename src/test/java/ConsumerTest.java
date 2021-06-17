import com.siberteam.koen.dictionary.ConsumerDictionary;
import com.siberteam.koen.dictionary.FileStreamWorker;
import com.siberteam.koen.dictionary.ProducerDictionary;
import com.siberteam.koen.dictionary.UrlStreamWorker;
import org.junit.Test;

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
            UrlStreamWorker urlStreamWorker = new UrlStreamWorker("file:///home/koen/IdeaProjects/eduTest/src/main/resources/1.txt");
            BlockingQueue<String> wordsQueue = new ArrayBlockingQueue(1024);
            Deque<String> urls = new ArrayDeque<>();
            ExecutorService threadConsumer = Executors.newSingleThreadExecutor();
            ConsumerDictionary consumerDictionary = new ConsumerDictionary(wordsQueue);
            ProducerDictionary producerDictionary = new ProducerDictionary(
                    wordsQueue,
                    urls,
                    null
            );
            threadConsumer.execute(consumerDictionary);
            String line = "";
            while ((line = urlStreamWorker.getLineFromUrlFile()) != null) {
                Thread.sleep(5000);
                producerDictionary.putAllTheWordsLineInQueue(line);
            }
            urlStreamWorker.closeFileReaderWithUrl();
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
