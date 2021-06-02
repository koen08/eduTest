package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CollectionManagerImpl implements CollectionManager{
    private List<Character> listRead;

    public CollectionManagerImpl(List<Character> listRead) {
        this.listRead = listRead;
    }

    @Override
    public Map<Character, Integer> getFrequencyCharacter(){
        Map<Character, Integer> mapWithFrequency = new HashMap<>();
        for (int i = 0; i < listRead.size(); i++){
            Character character = listRead.get(i);
            if (!mapWithFrequency.containsKey(character)){
                mapWithFrequency.put(listRead.get(i), 1);
            } else {
                Integer countFrequencyObject = mapWithFrequency.get(listRead.get(i));
                countFrequencyObject++;
                mapWithFrequency.put(listRead.get(i), countFrequencyObject);
            }
        }
        return mapWithFrequency;
    }

    @Override
    public int getListSize() {
        return listRead.size();
    }
}
