package com.siberteam.koen.dictionary;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {
    private static final String INPUT = "files";
    private static final String OUTPUT = "out";
    private static final String THREAD = "threads";
    private static final String ARG_NAME = "property=value";
    private String inputFileNameArg;
    private String outputFilNameArg;
    private byte countThread;
    private CommandLine cmd;

    public ValidationInputData(String[] args) throws FileException {
        generateOption(args);
        if (cmd.hasOption(INPUT)) {
            inputFileNameArg = cmd.getOptionValue(INPUT);
            checkExistsFile(inputFileNameArg);
        }
        if (cmd.hasOption(OUTPUT)) {
            outputFilNameArg = cmd.getOptionValue(OUTPUT);
            checkExistsFile(outputFilNameArg);
        }
        if (cmd.hasOption(THREAD)) {
            countThread = Byte.parseByte(cmd.getOptionValue(THREAD));
            if (getCountThread() < 1) {
                LoggerError.log("The number of threads cannot be less than 1",
                        new FileException("Thread=" + getCountThread()));
            }
        }
    }

    private void generateOption(String[] args) {
        try {
            Options options = new Options();
            Option propertyOption = Option.builder()
                    .longOpt(INPUT)
                    .argName(ARG_NAME)
                    .hasArgs()
                    .valueSeparator()
                    .numberOfArgs(1)
                    .build();
            Option propertyOptionResults = Option.builder()
                    .longOpt(OUTPUT)
                    .argName(ARG_NAME)
                    .hasArgs()
                    .valueSeparator()
                    .numberOfArgs(1)
                    .build();
            Option propertyOptionCountThread = Option.builder()
                    .longOpt(THREAD)
                    .argName(ARG_NAME)
                    .hasArgs()
                    .valueSeparator()
                    .numberOfArgs(1)
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
