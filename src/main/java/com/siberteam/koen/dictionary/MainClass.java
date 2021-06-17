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
            System.out.println("Program started");
            threadManager.finishThread();
            fileStreamManager.writeResultsToFile(threadManager.getConsumerDictionary().getSetWords());
            System.out.println("Program completed successfully");
        } catch (ArrayIndexOutOfBoundsException e) {
            LoggerError.log("No arguments in the program...",
                    e);
            System.exit(BASE_VALUE_FOR_ERROR);
        } catch (FileException e) {
            LoggerError.log(e.getMessage(),
                    e);
            System.exit(BASE_VALUE_FOR_ERROR);
        }
    }
}
