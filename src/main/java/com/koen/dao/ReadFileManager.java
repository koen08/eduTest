package com.koen.dao;

import java.io.IOException;
import java.util.Map;

public interface ReadFileManager {
    int getCharacter() throws IOException;

    void close() throws IOException;

    int getAmountCharacterByFile();

    Map<Character, Integer> readFileToMap() throws IOException;
}
