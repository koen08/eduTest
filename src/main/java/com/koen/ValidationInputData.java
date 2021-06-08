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
                    LoggerError.log("File created", null);
                } else {
                    throw new FileException("Error create file");
                }
            } catch (IOException e) {
                LoggerError.log("Error create file", e);
                throw new FileException("Probably file name has invalid path");
            }
        } else {
            throw new FileException("File exists");
        }
    }

    public String getInputFileNameArg() {
        return inputFileNameArg;
    }

    public String getOutputFilNameArg() {
        return outputFilNameArg;
    }
}
