package com.koen.dao;

import java.io.IOException;
import java.util.Map;

public interface WriteFileManager {
    void writeTextLine(String textLine) throws IOException;

    void writeMapToFile(Map<Character, Integer> mapWithFrequency) throws IOException;

    void closeWrite() throws IOException;

    String generateSlash(double percent);

    StringBuilder getTextWithFrequencyToWrite(Character key, Integer value);
}
