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

    public ValidationInputData(String[] args) throws FileException,
            IOException, ParseException {
        if (args.length == 0) {
            throw new FileException("Arguments is empty");
        }
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
                throw new FileException("Threads = " + getCountThread());
            }
        }
    }

    private void generateOption(String[] args) throws ParseException {
        Options options = new Options();
        Option propertyOption = Option.builder().longOpt(INPUT)
                .argName(ARG_NAME)
                .hasArgs()
                .valueSeparator()
                .numberOfArgs(1)
                .build();
        Option propertyOptionResults = Option.builder().longOpt(OUTPUT)
                .argName(ARG_NAME)
                .hasArgs()
                .valueSeparator()
                .numberOfArgs(1)
                .build();
        Option propertyOptionCountThread = Option.builder().longOpt(THREAD)
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
    }

    public void checkExistsFile(String fileName) throws FileException,
            IOException {
        File myFile = new File(fileName);
        if (!myFile.exists()) {
            if (!myFile.createNewFile()) {
                throw new FileException("Error create file");
            } else {
                LoggerConsole.logMessage("File created");
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
