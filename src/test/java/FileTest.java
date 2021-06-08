import com.koen.FrequencyChar;
import com.koen.IFrequencyChar;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileTest {
    @Test
    public void correctCalculate() {
        IFrequencyChar frequencyChar = new FrequencyChar();
        assertEquals(50.0, frequencyChar.getPercent(5, 10), 0.01);
    }

    @Test
    public void correctFileWork() throws IOException {
        String inputFilePath = "src/main/resources/inputFile";
        com.koen.FileReader readerFile = new com.koen.FileReader(new FileInputStream(inputFilePath));
        IFrequencyChar frequencyChar = new FrequencyChar();
        Map<Character, Integer> mapWithFrequency =
                frequencyChar.collectStatisticFile(readerFile);
        assertEquals(getFilledMap(), mapWithFrequency);
    }

    private Map<Character, Integer> getFilledMap() {
        Map<Character, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put('1', 3);
        expectedMap.put('2', 2);
        expectedMap.put('3', 1);
        return expectedMap;
    }
}
