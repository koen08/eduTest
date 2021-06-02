import service.FileManager;
import service.FileManagerImpl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите название исходного файла...");
        String readFile = in.nextLine();
        System.out.println("Введите название файла результатов...");
        String writeFile = in.nextLine();
        File myFile = new File(readFile + ".txt");
        if (!myFile.exists()){
            System.out.println("File results create");
            myFile.createNewFile();
        }
        File results = new File(writeFile + ".txt");
        if(!results.exists()) {
           System.out.println("File results create");
           results.createNewFile();
         }
       FileManager fileManager = new FileManagerImpl(myFile, results);
       fileManager.startCollectFrequency();
    }
}
