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
            FileReader readerFile = new FileReader(new FileInputStream(fileRead));
            IFrequencyChar frequencyChar = new FrequencyChar();
            Map<Character, Integer> mapWithFrequency =
                    frequencyChar.collectStatisticFile(readerFile);
            FileWriter writerFile = new FileWriter(
                    new FileOutputStream(fileWrite),
                    frequencyChar.getAmountCharacter()
            );
            writerFile.writeMapToFile(mapWithFrequency);
        } catch (FileNotFoundException e) {
            System.out.println("File did not found");
            e.printStackTrace();
        }
    }
}
