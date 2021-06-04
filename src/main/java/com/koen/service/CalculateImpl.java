package com.koen.service;

public class CalculateImpl implements Calculate {
    @Override
    public double getPercent(int valueFrequency, int amountCharacterByFile) {
        double division = (double) valueFrequency / amountCharacterByFile;
        double percent = division * 100;
        return percent;
    }
}
