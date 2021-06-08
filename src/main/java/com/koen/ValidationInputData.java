package com.koen;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {
    private String inputFileNameArg;
    private String outputFilNameArg;

    public ValidationInputData(String[] args) throws Exception {
        inputFileNameArg = args[0];
        outputFilNameArg = args[1];
        checkExistsFile(inputFileNameArg);
        checkExistsFile(outputFilNameArg);
    }

    public void checkExistsFile(String fileName) throws Exception {
        File myFile = new File(fileName);
        if (!myFile.exists()) {
            try {
                if (!myFile.createNewFile()) {
                    LoggerError.log("File created", null);
                } else {
                    LoggerError.log("Error create file", null);
                }
            } catch (IOException e) {
                LoggerError.log("Error create file", e);
                throw new Exception("Probably file name has invalid path");
            }
        } else {
            LoggerError.log("File exists", null);
        }
    }

    public String getInputFileNameArg() {
        return inputFileNameArg;
    }

    public String getOutputFilNameArg() {
        return outputFilNameArg;
    }
}
