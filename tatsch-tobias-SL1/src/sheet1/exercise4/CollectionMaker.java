package sheet1.exercise4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CollectionMaker {

    // total number of sonetts
    public static int numberOfSonnets = 154;

    // Main method for testing
    public static void main(String[] args) {
        String[] documents = readDocuments("data/collection.txt");

        System.out.println("Shakespeare's Sonnets");
        for (int i = 0; i < documents.length; i++) {
            System.out.println(i + "> " + documents[i]);
        }
    }

    // Exercise 4.1
    public static String[] readDocuments(String string) {
        List<String> shakespear = new ArrayList<String>();
        
        try {
            Scanner scanner = new Scanner(new File(string));
            while (scanner.hasNextLine()) {
                shakespear.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return shakespear.toArray(new String[]{});
    }
}
