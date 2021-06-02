package service;

import dao.ReadFileManager;
import dao.ReadFileManagerImpl;
import dao.WriteFileManager;
import dao.WriteFileManagerImpl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileManagerImpl implements FileManager {

    private CollectionManager collectionManager;
    private final File fileRead;
    private final File fileWrite;

    public FileManagerImpl(File fileRead, File fileWrite){
        this.fileRead = fileRead;
        this.fileWrite = fileWrite;
    }

    @Override
    public void startCollectFrequency() throws IOException {
        List<Character> listRead = getListReadFileData();
        collectionManager = new CollectionManagerImpl(listRead);
        Map<Character, Integer> mapWithFrequency = collectionManager.getFrequencyCharacter();
        writeMapToFile(mapWithFrequency);
    }

    private List<Character> getListReadFileData() throws IOException {
        ReadFileManager readFileManager = new ReadFileManagerImpl(new FileReader(fileRead));
        int character;
        List<Character> listRead = new LinkedList<>();
        while ((character = readFileManager.getCharacter()) != -1){
            listRead.add((char)character);
        }
        return listRead;
    }

    private void writeMapToFile(Map<Character, Integer> mapWithFrequency) throws IOException {
        WriteFileManager writeFileManager = new WriteFileManagerImpl(new FileWriter(fileWrite));
        String textLine = "";
        for (Map.Entry<Character, Integer> map : mapWithFrequency.entrySet()) {
            double percent = getPercent(map.getValue());
            textLine += map.getKey() + " (" + percent + "%):" + " " + generateSlesh(percent);
            writeFileManager.writeTextLine(textLine);
            textLine = "";
        }
        writeFileManager.closeWrite();
    }

    private double getPercent(int valueFrequency){
        double division = (double) valueFrequency / collectionManager.getListSize();
        double percent = division * 100;
        return Math.round(percent);
    }

    private String generateSlesh(double percent){
        StringBuilder stringBuilder = new StringBuilder();
        double sleshCount =(percent * (collectionManager.getListSize() * 2))/100;
        double sleshCountRounded = Math.round(sleshCount);
        for (int i = 0; i < sleshCountRounded; i++){
            stringBuilder.append("#");
        }
        return stringBuilder.toString();
    }
}
