package com.koen;

public class LoggerError {
    public static void log(String messageLog, Exception exception) {
        System.out.println("Message error: " + messageLog);
        if (exception != null) exception.printStackTrace();
    }
}
