package com.siberteam.koen.dictionary;

import java.util.Deque;

public class MainClass {
    private static final byte BASE_VALUE_FOR_ERROR = 64;

    public static void main(String[] args) {
        try {
            ValidationInputData validation = new ValidationInputData(args);
            FileStreamWorker fileStreamManager = new FileStreamWorker(validation.getInputFileNameArg(),
                    validation.getOutputFilNameArg());
            Deque<String> stackUrl = fileStreamManager.getStackUrl();
            ThreadManager threadManager = new ThreadManager(stackUrl, validation.getCountThread());
            threadManager.startThread();
            LoggerConsole.logMessage("Program started");
            threadManager.finishThread();
            fileStreamManager.writeResultsToFile(threadManager.getConsumerDictionary().getSetWords());
            LoggerConsole.logMessage("Program completed successfully");
        } catch (Exception e) {
            LoggerConsole.logError(e.getMessage());
            System.exit(BASE_VALUE_FOR_ERROR);
        }
    }
}
