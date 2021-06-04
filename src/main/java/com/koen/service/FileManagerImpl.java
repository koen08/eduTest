package com.koen.service;

import com.koen.dao.ReadFileManager;
import com.koen.dao.ReadFileManagerImpl;
import com.koen.dao.WriteFileManager;
import com.koen.dao.WriteFileManagerImpl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileManagerImpl implements FileManager {

    private final File fileRead;
    private final File fileWrite;

    public FileManagerImpl(File fileRead, File fileWrite) {
        this.fileRead = fileRead;
        this.fileWrite = fileWrite;
    }

    @Override
    public void startCollectFrequency() throws IOException {
        ReadFileManager readFileManager = new ReadFileManagerImpl(new FileReader(fileRead));
        Map<Character, Integer> mapWithFrequency = readFileManager.readFileToMap();
        WriteFileManager writeFileManager =
                new WriteFileManagerImpl(new FileWriter(fileWrite), readFileManager.getAmountCharacterByFile());
        writeFileManager.writeMapToFile(mapWithFrequency);
    }
}