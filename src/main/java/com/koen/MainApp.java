package com.koen;

import com.koen.service.FileManager;
import com.koen.service.FileManagerImpl;

import java.io.File;
import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException {
        String readFile = args[0];
        String writeFile = args[1];
        File myFile = new File(readFile + ".txt");
        if (!myFile.exists()) {
            if (!myFile.createNewFile()) {
                System.out.println("File basic did not created");
            } else {
                System.out.println("File basic create");
            }
        }
        File results = new File(writeFile + ".txt");
        if (!results.exists()) {
            if (!results.createNewFile()) {
                System.out.println("File results did not created");
            } else {
                System.out.println("File results create");
            }
        }
        FileManager fileManager = new FileManagerImpl(myFile, results);
        fileManager.startCollectFrequency();
        System.out.println("Program completed successfully");
    }
}
