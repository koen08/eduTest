package com.koen;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {
    private String inputFileNameArg;
    private String outputFilNameArg;

    public ValidationInputData(String[] args) throws FileException {
        inputFileNameArg = args[0];
        outputFilNameArg = args[1];
        checkExistsFile(inputFileNameArg);
        checkExistsFile(outputFilNameArg);
    }

    public void checkExistsFile(String fileName) throws FileException {
        File myFile = new File(fileName);
        if (!myFile.exists()) {
            try {
                if (!myFile.createNewFile()) {
                    throw new FileException("Error create file");
                } else {
                    LoggerError.log("File created", null);
                }
            } catch (IOException e) {
                LoggerError.log("Error create file", e);
                throw new FileException("Probably file name has invalid path");
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
