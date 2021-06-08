package com.koen;

public class MainApp {
    private static byte EX_BASE = 64;

    public static void main(String[] args) {
        try {
            ValidationInputData validationInputData = new ValidationInputData(args);
            ManagerFile managerFile = new ManagerFile(
                    validationInputData.getInputFileNameArg(),
                    validationInputData.getOutputFilNameArg());
            managerFile.startCollectFrequency();
            System.out.println("Program completed successfully");
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
