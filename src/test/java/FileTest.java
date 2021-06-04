import com.koen.service.Calculate;
import com.koen.service.CalculateImpl;
import com.koen.service.FileManager;
import com.koen.service.FileManagerImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileTest {
    @Rule
    public TemporaryFolder fileTemp = new TemporaryFolder();

    @Test
    public void correctCalculate() {
        Calculate calculate = new CalculateImpl();
        assertEquals(50.0, calculate.getPercent(5, 10), 0.01);
    }

    @Test
    public void correctFileWork() throws IOException {
        File fileInput = fileTemp.newFile("input.txt");
        File fileResults = fileTemp.newFile("result.txt");
        writeTestTextToFileInput(fileInput);
        FileManager fileManager = new FileManagerImpl(fileInput, fileResults);
        fileManager.startCollectFrequency();
        assertEquals("5 (100,0%): ##\n", getTextFromResults(fileResults));
    }

    private void writeTestTextToFileInput(File fileInput) throws IOException {
        FileWriter fileInputWriter = new FileWriter(fileInput);
        fileInputWriter.write('5');
        fileInputWriter.flush();
        fileInputWriter.close();
    }

    private String getTextFromResults(File fileResults) throws IOException {
        FileReader resultsFileReader = new FileReader(fileResults);
        int c;
        StringBuilder stringBuilder = new StringBuilder();
        while ((c = resultsFileReader.read()) != -1) {
            stringBuilder.append((char) c);
        }
        resultsFileReader.close();
        return stringBuilder.toString();
    }
}
