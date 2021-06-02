package service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CollectionManager {
    Map<Character, Integer> getFrequencyCharacter();
    int getListSize();
}
