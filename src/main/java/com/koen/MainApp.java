package com.koen;

import java.io.File;
import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException {
        String readFile = args[0];
        String writeFile = args[1];
        ValidationInputData validationInputData = new ValidationInputData();
        validationInputData.checkExistsFile(readFile);
        validationInputData.checkExistsFile(writeFile);
        ManagerFile managerFile = new ManagerFile(readFile, writeFile);
        managerFile.startCollectFrequency();
        System.out.println("Program completed successfully");
    }
}
