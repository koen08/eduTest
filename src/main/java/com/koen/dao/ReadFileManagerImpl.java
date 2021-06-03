package com.koen.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadFileManagerImpl implements ReadFileManager {
    private BufferedReader bufferedReader;
    private int amountCharacterByFile;

    public ReadFileManagerImpl(FileReader fileReader) {
        this.bufferedReader = new BufferedReader(fileReader);
    }

    public Map<Character, Integer> readFileToMap() throws IOException {
        Map<Character, Integer> mapWithFrequency = new HashMap<>();
        int character;
        while ((character = getCharacter()) != -1) {
            amountCharacterByFile++;
            if (!mapWithFrequency.containsKey((char) character)) {
                mapWithFrequency.put((char) character, 1);
            } else {
                Integer countFrequencyObject = mapWithFrequency.getOrDefault((char) character, 1);
                countFrequencyObject++;
                mapWithFrequency.put((char) character, countFrequencyObject);
            }
        }
        close();
        return mapWithFrequency;
    }

    @Override
    public int getCharacter() throws IOException {
        return bufferedReader.read();
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    @Override
    public int getAmountCharacterByFile() {
        return amountCharacterByFile;
    }


}
