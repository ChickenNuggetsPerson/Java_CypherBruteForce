package Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DictionarySys {
    
    //private String dictLocation = "/usr/share/dict/words";
    //private String dictLocation = "./words_alpha.txt";
    private String dictLocation = "./processedOxford.txt";

    public Set<String> storage = new HashSet<>();

    public DictionarySys() throws FileNotFoundException {
        File file = new File(dictLocation);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            this.storage.add(scanner.nextLine().toLowerCase());
        }
        scanner.close();
        System.out.println("-----------------");        
        System.out.println("Loaded Dictionary");
    }

}
