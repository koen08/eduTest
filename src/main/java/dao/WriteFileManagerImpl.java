package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFileManagerImpl implements WriteFileManager{
    private BufferedWriter bufferedWriter;

    public WriteFileManagerImpl(FileWriter fileWriter) {
        this.bufferedWriter = new BufferedWriter(fileWriter);
    }

    @Override
    public void writeTextLine(String textLine) throws IOException {
        bufferedWriter.write(textLine + System.lineSeparator());
    }

    @Override
    public void closeWrite() throws IOException {
        bufferedWriter.close();
    }
}
