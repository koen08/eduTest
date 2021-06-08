package com.koen;

import java.util.LinkedHashMap;
import java.util.Map;

public class FrequencyChar implements IFrequencyChar {

    private int amountCharacterByFile;

    @Override
    public double getPercent(int valueFrequency, int amountCharacterByFile) {
        return ((double) valueFrequency / amountCharacterByFile) * 100;
    }

    @Override
    public Map<Character, Integer> collectStatisticFile(FileReader readerFile) {
        Map<Character, Integer> mapWithFrequency = new LinkedHashMap<>();
        int character;
        while ((character = readerFile.getCharacter()) != -1) {
            amountCharacterByFile++;
            Integer countFrequencyObject = mapWithFrequency.getOrDefault((char) character, 0);
            countFrequencyObject++;
            mapWithFrequency.put((char) character, countFrequencyObject);
        }
        readerFile.close();
        Map<Character, Integer> mapWithFrequencySorted = new LinkedHashMap<>();
        mapWithFrequency.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> mapWithFrequencySorted.put(x.getKey(), x.getValue()));
        return mapWithFrequencySorted;
    }

    @Override
    public int getAmountCharacter() {
        return amountCharacterByFile;
    }
}
