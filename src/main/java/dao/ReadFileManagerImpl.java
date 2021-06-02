package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileManagerImpl implements ReadFileManager{
    private BufferedReader bufferedReader;

    public ReadFileManagerImpl(FileReader fileReader) {
        this.bufferedReader = new BufferedReader(fileReader);
    }

    @Override
    public int getCharacter() throws IOException {
        return bufferedReader.read();
    }
}
