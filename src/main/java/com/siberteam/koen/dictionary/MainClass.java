package com.siberteam.koen.dictionary;

public class MainClass {
    private static final byte EX_BASE = 64;

    public static void main(String[] args) {
        try {
            ValidationInputData validationInputData = new ValidationInputData(args);
            FileManager fileManager = new FileManager(validationInputData.getInputFileNameArg(),
                    validationInputData.getOutputFilNameArg());
            ThreadManager threadManager = new ThreadManager(fileManager, validationInputData.getCountThread());
            threadManager.startThread();
            System.out.println("Program completed successfully");
            threadManager.finishThread();
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            LoggerError.log("No arguments in the program...",
                    arrayIndexOutOfBoundsException);
            System.exit(EX_BASE);
        } catch (FileException exception) {
            LoggerError.log(exception.getMessage(),
                    exception);
            System.exit(EX_BASE);
        }
    }
}
