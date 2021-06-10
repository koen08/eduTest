package com.dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class ProducerDictionary implements Runnable {
    private BlockingQueue<String> queueLine;
    private BlockingQueue<String> urlFile;

    public ProducerDictionary(BlockingQueue<String> queueLine,
                              BlockingQueue<String> urlFile) {
        this.queueLine = queueLine;
        this.urlFile = urlFile;
    }

    @Override
    public void run() {
        while (!urlFile.isEmpty()) {
            LineConverter lineConverter = new LineConverter();
            try (FileReader readerFile = new FileReader(new URL(urlFile.take()).openStream())) {
                String line;
                while ((line = readerFile.getLine()) != null) {
                    lineConverter.putLineToQueue(lineConverter.convertLineTextToWords(
                            line.replaceAll("[()?:!.,;{}-]+", " ")), queueLine);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
