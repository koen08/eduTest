package com.koen;

import java.util.HashMap;
import java.util.Map;

public class FrequencyCharImpl implements FrequencyChar {

    private int amountCharacterByFile;

    @Override
    public double getPercent(int valueFrequency, int amountCharacterByFile) {
        double division = (double) valueFrequency / amountCharacterByFile;
        double percent = division * 100;
        return percent;
    }

    @Override
    public Map<Character, Integer> collectStatisticFile(ReaderFile readerFile) {
        Map<Character, Integer> mapWithFrequency = new HashMap<>();
        int character;
        while ((character = readerFile.getCharacter()) != -1) {
            amountCharacterByFile++;
            Integer countFrequencyObject = mapWithFrequency.getOrDefault((char) character, 0);
            countFrequencyObject++;
            mapWithFrequency.put((char) character, countFrequencyObject);
        }
        readerFile.close();
        return mapWithFrequency;
    }

    @Override
    public int getAmountCharacter() {
        return amountCharacterByFile;
    }
}
