package com.siberteam.koen.anagram;

import java.io.*;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FileStreamWorker {
    private final String fileReaderName;
    public final String fileWriterName;

    public FileStreamWorker(String fileRead, String fileWrite) {
        this.fileReaderName = fileRead;
        this.fileWriterName = fileWrite;
    }

    public Deque<String> getStackUrl() throws IOException {
        Deque<String> urls = new ConcurrentLinkedDeque<>();
        try (BufferedReader readerFile = new BufferedReader(new FileReader(fileReaderName))) {
            String line;
            while ((line = readerFile.readLine()) != null) {
                urls.push(line);
            }
        }
        return urls;
    }

    public void writeResultsToFile(Map<String, Set<String>> anagrams) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileWriterName))) {
            for (Map.Entry<String, Set<String>> words : anagrams.entrySet()) {
                Set<String> setWords = words.getValue();
                StringBuilder anagramString = new StringBuilder();
                Iterator<String> iterator = setWords.stream().skip(1).iterator();
                while (iterator.hasNext()) {
                    anagramString.append(iterator.next() + ",");
                }
                if (!anagramString.toString().isEmpty()) {
                    anagramString.setLength(anagramString.length() - 1);
                    fileWriter.write(setWords.stream().findFirst().get() + ": "
                            + anagramString + System.lineSeparator());
                }
            }
        }
    }
}
