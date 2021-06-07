package com.koen;

public class MainApp {
    private static final ValidationInputData validationInputData = new ValidationInputData();

    public static void main(String[] args) {
        try {
            String inputFileArg = args[0];
            String outPutFileArg = args[1];
            validationInputData.checkExistsFile(inputFileArg);
            validationInputData.checkExistsFile(outPutFileArg);
            ManagerFile managerFile = new ManagerFile(inputFileArg, outPutFileArg);
            managerFile.startCollectFrequency();
            System.out.println("Program completed successfully");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            validationInputData.arrayIndexOutOfBoundsException();
        }
    }
}
