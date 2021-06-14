package com.dictionary;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {
    private String inputFileNameArg;
    private String outputFilNameArg;
    private byte countThread;
    private CommandLine cmd;

    public ValidationInputData(String[] args) throws FileException {
        generateOption(args);
        if (cmd.hasOption("fileInput")) {
            inputFileNameArg = cmd.getOptionValue("fileInput");
            checkExistsFile(inputFileNameArg);
        }
        if (cmd.hasOption("fileResults")) {
            outputFilNameArg = cmd.getOptionValue("fileResults");
            checkExistsFile(outputFilNameArg);
        }
        if (cmd.hasOption("count")) {
            countThread = Byte.parseByte(cmd.getOptionValue("count"));
            checkArgCountThread();
        }
    }

    private void generateOption(String[] args) {
        try {
            Options options = new Options();
            Option propertyOption = Option.builder()
                    .longOpt("fileInput")
                    .argName("property=value")
                    .hasArgs()
                    .valueSeparator()
                    .numberOfArgs(1)
                    .desc("use value for given properties")
                    .build();
            Option propertyOptionResults = Option.builder()
                    .longOpt("fileResults")
                    .argName("property=value")
                    .hasArgs()
                    .valueSeparator()
                    .numberOfArgs(1)
                    .desc("use value for given properties")
                    .build();
            Option propertyOptionCountThread = Option.builder()
                    .longOpt("count")
                    .argName("property=value")
                    .hasArgs()
                    .valueSeparator()
                    .numberOfArgs(1)
                    .desc("use value for given properties")
                    .build();
            options.addOption(propertyOption);
            options.addOption(propertyOptionResults);
            options.addOption(propertyOptionCountThread);
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public void checkArgCountThread() {
        if (getCountThread() < 1) {
            LoggerError.log("The number of threads cannot be less than 1",
                    new FileException("Thread=" + getCountThread()));
        }
    }

    public String getInputFileNameArg() {
        return inputFileNameArg;
    }

    public String getOutputFilNameArg() {
        return outputFilNameArg;
    }

    public byte getCountThread() {
        return countThread;
    }
}
