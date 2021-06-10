package com.dictionary;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class MainClass {
    private static byte EX_BASE = 64;

    public static void main(String[] args) {
        try {
            long m = System.currentTimeMillis();
            ValidationInputData validationInputData = new ValidationInputData(args);
            ManagerFile managerFile = new ManagerFile(validationInputData.getInputFileNameArg(),
                    validationInputData.getOutputFilNameArg());
            new ManagerThead(managerFile.getStackUrl(), (byte) 4).startThread();
            System.out.println("Program completed successfully");
            System.out.println((double) (System.currentTimeMillis() - m) / 1000);
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
