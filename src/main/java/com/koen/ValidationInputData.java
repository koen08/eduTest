package com.koen;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {

    public ValidationInputData(String inputFileNameArg, String outputFilNameArg) {
        checkExistsFile(inputFileNameArg);
        checkExistsFile(outputFilNameArg);
    }

    public void checkExistsFile(String fileName) {
        File myFile = new File(fileName);
        if (!myFile.exists()) {
            try {
                if (!myFile.createNewFile()) {
                    System.out.println("File basic did not created");
                } else {
                    System.out.println("File basic create");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
