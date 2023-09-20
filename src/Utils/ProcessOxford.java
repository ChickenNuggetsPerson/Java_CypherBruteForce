package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ProcessOxford {
    public static void main(String[] args) throws Exception {
        ProcessOxford test = new ProcessOxford();
    }

    private String dictLocation = "./Oxford English Dictionary.txt";

    private StringBuilder storage = new StringBuilder();

    ProcessOxford() throws IOException {

        File file = new File(dictLocation);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String readLine = scanner.nextLine().toLowerCase();
            String[] split = readLine.split(" ");
            if (split.length == 0) { continue; }
            if (split[0] == "") { continue; }
            this.storage.append(split[0] + "\n");
        }
        scanner.close();        

        FileWriter wr = new FileWriter("./processedOxford.txt");
        wr.write(this.storage.toString());
        wr.flush();
        wr.close();
        System.out.println("Done");
    }
}
