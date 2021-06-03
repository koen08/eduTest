package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteFileManagerImpl implements WriteFileManager {
    private final BufferedWriter bufferedWriter;
    private final int amountCharacterByFile;

    public WriteFileManagerImpl(FileWriter fileWriter, int amountCharacterByFile) {
        this.bufferedWriter = new BufferedWriter(fileWriter);
        this.amountCharacterByFile = amountCharacterByFile;
    }

    public void writeMapToFile(Map<Character, Integer> mapWithFrequency) throws IOException {
        StringBuilder textLine = new StringBuilder();
        for (Map.Entry<Character, Integer> map : mapWithFrequency.entrySet()) {
            double percent = getPercent(map.getValue());
            textLine.append(map.getKey()).append(" (").append(percent).
                    append("%):").append(" ").append(generateSlash(percent));
            writeTextLine(textLine.toString());
            textLine = new StringBuilder();
        }
        closeWrite();
    }

    private double getPercent(int valueFrequency) {
        double division = (double) valueFrequency / amountCharacterByFile;
        double percent = division * 100;
        return Math.round(percent);
    }

    private String generateSlash(double percent) {
        StringBuilder stringBuilder = new StringBuilder();
        double slashCount = (percent * (amountCharacterByFile * 2)) / 100;
        double slashCountRounded = Math.round(slashCount);
        for (int i = 0; i < slashCountRounded; i++) {
            stringBuilder.append("#");
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeTextLine(String textLine) throws IOException {
        bufferedWriter.write(textLine + System.lineSeparator());
    }

    @Override
    public void closeWrite() throws IOException {
        bufferedWriter.close();
    }
}
