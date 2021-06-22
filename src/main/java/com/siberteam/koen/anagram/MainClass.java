package com.siberteam.koen.anagram;
import com.siberteam.koen.common.LoggerConsole;

import java.util.Deque;

public class MainClass {
    private static final byte BASE_VALUE_FOR_ERROR = 64;
    public static void main(String[] args) {
        try {
            LoggerConsole.logMessage("Program started");
            ValidationInputData validation = new ValidationInputData(args);
            FileStreamWorker fileStreamManager = new FileStreamWorker(validation.getInputFileNameArg(),
                    validation.getOutputFilNameArg());
            Deque<String> stackUrl = fileStreamManager.getStackUrl();
            ThreadManager threadManager = new ThreadManager(stackUrl, validation.getProducerThread(),
                    validation.getConsumerThread());
            threadManager.startThread();
            threadManager.finishThread();
            fileStreamManager.writeResultsToFile(threadManager.getAnagrams());
            LoggerConsole.logMessage("Program completed successfully");
        } catch (Exception e) {
            LoggerConsole.logError(e.getMessage());
            System.exit(BASE_VALUE_FOR_ERROR);
        }
    }
}
