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
            this.storage.add(readLine);
            this.strStorage.add(readLine);
        }
        scanner.close();
        System.out.println("-----------------");        
        System.out.println("Loaded Dictionary");
        System.out.println("-----------------");        
        System.out.println(" ");
    }

}
