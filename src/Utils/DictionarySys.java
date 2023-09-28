package Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DictionarySys {
    
    //private String dictLocation = "/usr/share/dict/words";
    //private String dictLocation = "./words_alpha.txt";
    private String dictLocation = "./processedOxford.txt";

    public Set<String> storage = new HashSet<>();
    public List<String> strStorage = new ArrayList<>();

    public DictionarySys() throws FileNotFoundException {
        File file = new File(dictLocation);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String readLine = scanner.nextLine().toLowerCase();
            if (this.endsWithSpecial(readLine)) { 
                readLine = readLine.substring(0, readLine.length() - 1);
            }
            if (storage.contains(readLine)) { continue; }
            //if (readLine.length() <= 1) { continue; }
            this.storage.add(readLine);
            this.strStorage.add(readLine);
        }
        scanner.close();
        System.out.println("-----------------");        
        System.out.println("Loaded Dictionary");
        System.out.println("-----------------");        
        System.out.println(" ");
    }

    private boolean endsWithSpecial(String str) {
        if (str.endsWith("0"))
            return true;
        if (str.endsWith("1"))
            return true;
        if (str.endsWith("2"))
            return true;
        if (str.endsWith("3"))
            return true;
        if (str.endsWith("4"))
            return true;
        if (str.endsWith("5"))
            return true;
        if (str.endsWith("6"))
            return true;
        if (str.endsWith("7"))
            return true;
        if (str.endsWith("8"))
            return true;
        if (str.endsWith("9"))
            return true;
        if (str.endsWith(","))
            return true;
        if (str.endsWith("."))
            return true;
        return false;
    }
}
