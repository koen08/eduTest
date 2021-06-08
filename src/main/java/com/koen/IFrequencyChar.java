package com.koen;

import java.util.Map;

public interface IFrequencyChar {
    double getPercent(int valueFrequency, int amountCharacterByFile);

    Map<Character, Integer> collectStatisticFile(FileReader readerFile);

    int getAmountCharacter();
}
