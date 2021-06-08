package com.koen;

public class MainApp {
    public static void main(String[] args) {
        try {
            ValidationInputData validationInputData = new ValidationInputData(args);
            ManagerFile managerFile = new ManagerFile(
                    validationInputData.getInputFileNameArg(),
                    validationInputData.getInputFileNameArg());
            managerFile.startCollectFrequency();
            System.out.println("Program completed successfully");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            LoggerError.log("No arguments in the program...",
                    arrayIndexOutOfBoundsException);
            System.exit(64);
        }
        catch (Exception exception){
            LoggerError.log(exception.getMessage(),
                    exception);
            System.exit(64);
        }
    }
}
