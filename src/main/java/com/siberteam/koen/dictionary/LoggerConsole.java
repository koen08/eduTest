package com.siberteam.koen.dictionary;

public class LoggerConsole {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void logError(String messageLog) {
        System.out.println(ANSI_RED + "Message error: " + messageLog + ANSI_RESET);
    }

    public static void logMessage(String message) {
        System.out.println("Message: " + message);
    }
}
