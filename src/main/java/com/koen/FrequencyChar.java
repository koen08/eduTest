package com.koen;

import java.util.Map;

public interface FrequencyChar {
    double getPercent(int valueFrequency, int amountCharacterByFile);
    Map<Character, Integer> collectStatisticFile(ReaderFile readerFile);
    int getAmountCharacter();
}
