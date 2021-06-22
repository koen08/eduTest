package com.siberteam.koen.test.anagram;

import com.siberteam.koen.anagram.ThreadManager;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

public class AnagramTest {

    @Test
    public void anagramTest() {
        try {
            File file = new File("src/main/resources/anagramTestInput.txt");
            Deque<String> urls = new ArrayDeque<>();
            urls.add("file://" + file.getAbsolutePath());
            ThreadManager threadManager = new ThreadManager(urls, (byte) 1, (byte) 1);
            threadManager.startThread();
            threadManager.finishThread();
            assertEquals(getAnagramsExtend(), threadManager.getAnagrams());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Set<String>> getAnagramsExtend() {
        Map<String, Set<String>> map = new ConcurrentHashMap<>();
        Set<String> set = new HashSet<>();
        set.add("агно");
        set.add("нога");
        set.add("гаон");
        set.add("агон");
        set.add("анго");
        map.put("агно", set);
        Set<String> set1 = new HashSet<>();
        set1.add("кто");
        set1.add("ток");
        set1.add("кот");
        map.put("кот", set1);
        return map;
    }

}
