package com.koen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

public class FileWriter implements AutoCloseable {
    private final BufferedWriter bufferedWriter;
    private final int amountCharacterByFile;

    public FileWriter(OutputStream outputStream, int amountCharacterByFile) {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        this.amountCharacterByFile = amountCharacterByFile;
    }

    public void writeMapToFile(Map<Character, Integer> mapWithFrequency) {
        StringBuilder textLine = new StringBuilder();
        for (Map.Entry<Character, Integer> map : mapWithFrequency.entrySet()) {
            if (map.getKey() == '\n') {
                textLine.append(getTextWithFrequencyToWrite('_', map.getValue()));
            } else if (map.getKey() == ' ') {
                textLine.append(getTextWithFrequencyToWrite('¿', map.getValue()));
            } else {
                textLine.append(getTextWithFrequencyToWrite(map.getKey(), map.getValue()));
            }
            writeTextLine(textLine.toString());
            textLine.setLength(0);
        }
        close();
    }

    public StringBuilder getTextWithFrequencyToWrite(Character key, Integer value) {
        IFrequencyChar frequencyChar = new FrequencyChar();
        double percent = frequencyChar.getPercent(value, amountCharacterByFile);
        return new StringBuilder().append(key).append(" (").append(String.format("%.1f%%", percent)).
                append("):").append(" ").append(generateSlash(percent));
    }

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

    public void writeTextLine(String textLine) {
        try {
            bufferedWriter.write(textLine + System.lineSeparator());
        } catch (IOException e) {
            LoggerError.log("File can not be write", e);
        }
    }

    @Override
    public void close() {
        try {
            bufferedWriter.close();
        } catch (Exception e) {
            LoggerError.log("Stream can not be close", e);
        }
    }
}
