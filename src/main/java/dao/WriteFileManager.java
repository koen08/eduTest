package dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public interface WriteFileManager {
    void writeTextLine(String textLine) throws IOException;
    void closeWrite() throws IOException;
}
