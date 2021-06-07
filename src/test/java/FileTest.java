import com.koen.FrequencyChar;
import com.koen.FrequencyCharImpl;
import com.koen.ManagerFile;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileTest {

    @Test
    public void correctCalculate() {
        FrequencyChar frequencyChar = new FrequencyCharImpl();
        assertEquals(50.0, frequencyChar.getPercent(5, 10), 0.01);
    }

    @Test
    public void correctFileWork() throws IOException {
        String inputFile = "src/main/resources/inputFile";
        String outPutFile = "src/main/resources/outPutFile";
        String resultFile = "src/main/resources/resultFile";
        ManagerFile managerFile = new ManagerFile(inputFile, outPutFile);
        managerFile.startCollectFrequency();
        assertEquals(getMapFromFile(resultFile), getMapFromFile(outPutFile));
    }

    private List<String> getMapFromFile(String file) {
        List<String> list = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
