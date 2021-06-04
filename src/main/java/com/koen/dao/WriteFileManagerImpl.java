package com.koen.dao;

import com.koen.service.Calculate;
import com.koen.service.CalculateImpl;

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
            if (map.getKey() != '\n') {
                textLine.append(getTextWithFrequencyToWrite(map.getKey(), map.getValue()));
            } else {
                textLine.append(getTextWithFrequencyToWrite('_', map.getValue()));
            }
            writeTextLine(textLine.toString());
            textLine.setLength(0);
        }
        closeWrite();
    }

    @Override
    public StringBuilder getTextWithFrequencyToWrite(Character key, Integer value) {
        Calculate calculate = new CalculateImpl();
        double percent = calculate.getPercent(value, amountCharacterByFile);
        return new StringBuilder().append(key).append(" (").append(String.format("%.1f%%", percent)).
                append("):").append(" ").append(generateSlash(percent));
    }

    @Override
    public String generateSlash(double percent) {
        StringBuilder stringBuilder = new StringBuilder();
        double slashCount;
        if (amountCharacterByFile < 100) {
            slashCount = (percent * (amountCharacterByFile * 2)) / 100;
        } else {
            slashCount = (percent * (amountCharacterByFile * 0.5)) / 100;
        }

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
