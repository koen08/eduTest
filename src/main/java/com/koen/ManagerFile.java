package com.koen;

import java.io.*;
import java.util.Map;

public class ManagerFile {

    private final String fileRead;
    private final String fileWrite;

    public ManagerFile(String fileRead, String fileWrite) {
        this.fileRead = fileRead;
        this.fileWrite = fileWrite;
    }

    public void startCollectFrequency() {
        try {
            ReaderFile readerFile = new ReaderFile(new FileInputStream(fileRead));
            FrequencyChar frequencyChar = new FrequencyCharImpl();
            Map<Character, Integer> mapWithFrequency =
                    frequencyChar.collectStatisticFile(readerFile);
            WriterFile writerFile = new WriterFile(
                    new FileOutputStream(fileWrite),
                    frequencyChar.getAmountCharacter()
            );
            writerFile.writeMapToFile(mapWithFrequency);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
