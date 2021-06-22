package com.siberteam.koen.anagram;

import com.siberteam.koen.common.FileException;
import com.siberteam.koen.common.LoggerConsole;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class ValidationInputData {
    private static final String INPUT = "files";
    private static final String OUTPUT = "out";
    private static final String THREAD_PRODUCER = "producer";
    private static final String THREAD_CONSUMER = "consumer";
    private String inputFileNameArg;
    private String outputFilNameArg;
    private byte producerThread;
    private byte consumerThread;
    private CommandLine cmd;

    public ValidationInputData(String[] args) throws Exception {
        generateOption(args);
        if (cmd.hasOption(INPUT)) {
            inputFileNameArg = cmd.getOptionValue(INPUT);
            checkExistsFile(inputFileNameArg);
        }
        if (cmd.hasOption(OUTPUT)) {
            outputFilNameArg = cmd.getOptionValue(OUTPUT);
            checkExistsFile(outputFilNameArg);
        }
        if (cmd.hasOption(THREAD_PRODUCER)) {
            producerThread = Byte.parseByte(cmd.getOptionValue(THREAD_PRODUCER));
            if (getProducerThread() < 1) {
                throw new FileException("Threads = " + getProducerThread());
            }
        }
        if (cmd.hasOption(THREAD_CONSUMER)) {
            consumerThread = Byte.parseByte(cmd.getOptionValue(THREAD_CONSUMER));
            if (getProducerThread() < 1) {
                throw new FileException("Threads = " + getProducerThread());
            }
        }
    }

    private void generateOption(String[] args) throws Exception {
        Options options = new Options();
        try {
            options.addRequiredOption("f", INPUT, true, "Input file");
            options.addRequiredOption("o", OUTPUT, true, "Output file");
            options.addRequiredOption("p", THREAD_PRODUCER, true, "Count threads for producer");
            options.addRequiredOption("c", THREAD_CONSUMER, true, "Count threads for consumer");
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ANAGRAM APP", options);
            throw new Exception("Please write arguments");
        }
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

    public byte getProducerThread() {
        return producerThread;
    }

    public byte getConsumerThread() {
        return consumerThread;
    }
}
