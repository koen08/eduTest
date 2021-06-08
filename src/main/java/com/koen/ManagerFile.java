package com.koen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
            IFrequencyChar frequencyChar;
            Map<Character, Integer> mapWithFrequency;
            try (FileReader readerFile = new FileReader(new FileInputStream(fileRead))) {
                frequencyChar = new FrequencyChar();
                mapWithFrequency =
                        frequencyChar.collectStatisticFile(readerFile);
            }
            try (FileWriter writerFile = new FileWriter(
                    new FileOutputStream(fileWrite),
                    frequencyChar.getAmountCharacter()
            )) {
                writerFile.writeMapToFile(mapWithFrequency);
            }
        } catch (FileNotFoundException e) {
            LoggerError.log("File did not found", e);
        }
    }
}
