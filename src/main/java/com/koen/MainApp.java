package com.koen;

public class MainApp {
    public static void main(String[] args) {
        try {
            String inputFileNameArg = args[0];
            String outPutFileNameArg = args[1];
            new ValidationInputData(inputFileNameArg, outPutFileNameArg);
            ManagerFile managerFile = new ManagerFile(inputFileNameArg, outPutFileNameArg);
            managerFile.startCollectFrequency();
            System.out.println("Program completed successfully");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("No arguments in the program...");
        }
    }
}
