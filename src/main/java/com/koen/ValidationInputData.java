package com.koen;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {
    private String inputFileNameArg;
    private String outputFilNameArg;

    public ValidationInputData(String[] args) {
        inputFileNameArg = args[0];
        outputFilNameArg = args[1];
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
                LoggerError.log("Error create file", e);
            }
        }
    }

    public String getInputFileNameArg() {
        return inputFileNameArg;
    }

    public String getOutputFilNameArg() {
        return outputFilNameArg;
    }
}
