package com.koen;

import java.io.*;
import java.util.Map;

public class WriterFile {
    private final BufferedWriter bufferedWriter;
    private final int amountCharacterByFile;

    public WriterFile(OutputStream outputStream, int amountCharacterByFile) {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        this.amountCharacterByFile = amountCharacterByFile;
    }

    public void writeMapToFile(Map<Character, Integer> mapWithFrequency) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder getTextWithFrequencyToWrite(Character key, Integer value) {
        FrequencyChar frequencyChar = new FrequencyCharImpl();
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

    public void writeTextLine(String textLine) throws IOException {
        bufferedWriter.write(textLine + System.lineSeparator());
    }

    public void closeWrite() throws IOException {
        bufferedWriter.close();
    }
}
