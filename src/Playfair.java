import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Utils.ProgressBar;
import Utils.StringScore;
import Utils.UtilsFuncs;
import Utils.UtilsFuncs.GridLoc;
import Utils.UtilsFuncs.SpecialCharStore;

public class Playfair {
    
    public static void main(String[] args) throws Exception {
        
        List<String> strList = new ArrayList<>();
        //strList.add("cgdiahklfqnuuokz"); // Sugar
        strList.add("abgyro,ipcyoeysgb");
        //strList.add("igeouwhenjjttl.ts");

        Playfair test = new Playfair(strList);
        //test.solve_brute(true);
        test.solve("videos");
    }

    private List<String> strList = new ArrayList();
    private UtilsFuncs utils;

    Playfair(List<String> strs) throws FileNotFoundException {
        this.utils = new UtilsFuncs();
        this.strList = strs;
    };
    Playfair() throws FileNotFoundException {
        this.utils = new UtilsFuncs();
    };

    public void solve(String key) {
        System.out.println("\nSolving");

        System.out.println("");

        for (int i = 0; i < strList.size(); i++) {
            SpecialCharStore store = this.utils.specialCharStoreGen(strList.get(i));
            String result = decodeString(store.formatedStr, key);
            System.out.println(this.utils.addStringPadding(strList.get(i), 20) + " : " + this.utils.specialCharStore_Apply(result, store.storage));
        }
    }   
    public void solve_brute(boolean deepEvaluation) {
        
        int maxSize = this.utils.dictSys.strStorage.size() * strList.size();
        ProgressBar bar = new ProgressBar(0.00, maxSize, 0.00, "Progress...");
        bar.start();

        List<StringScore> scores = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            for (int z = 0; z < this.utils.dictSys.strStorage.size(); z++) {

                bar.updateVal((i * this.utils.dictSys.strStorage.size()) + z);

                if (this.utils.dictSys.strStorage.get(z).contains("-")) {
                    continue;
                }


                SpecialCharStore store = this.utils.specialCharStoreGen(strList.get(i));
                String result = this.decodeString(store.formatedStr, this.utils.dictSys.strStorage.get(z));
                
                if (deepEvaluation) {
                    List<String> scoreStr = this.utils.evaluateString_S(result + " ");
                    double avgCount = 0.00;
                    StringBuilder tmpBuilder = new StringBuilder();
                    for (int x = 0; x < scoreStr.size(); x++) {
                        avgCount += scoreStr.get(x).length();
                        tmpBuilder.append(scoreStr.get(x) + " ");
                    }   
                    avgCount = (double)(avgCount / (double)scoreStr.size());


                    //StringScore newScore = new StringScore(this.utils.specialCharStore_Apply(result, store.storage), avgCount, 0);
                    StringScore newScore = new StringScore(this.utils.specialCharStore_Apply(result, store.storage), avgCount, 0);
                    newScore.altStr = tmpBuilder.toString();
                    newScore.thirdAltStr = strList.get(i);
                    newScore.fourthAltStr = this.utils.dictSys.strStorage.get(z);
                    scores.add(newScore);
                } else {
                    int score = this.utils.evaluateString(result);
                    StringScore newScore = new StringScore(this.utils.specialCharStore_Apply(result, store.storage), score, 0);
                    newScore.altStr = result.substring(0, score);
                    newScore.thirdAltStr = strList.get(i);
                    newScore.fourthAltStr = this.utils.dictSys.strStorage.get(z);
                    scores.add(newScore);
                }
               
            }
        }

        bar.stop();

        Collections.sort(scores, new Comparator<StringScore>() {
            @Override
            public int compare(StringScore o1, StringScore o2) {
                return (int)o2.score - (int)o1.score;
            }
        });
        System.out.println("Found " + scores.size() + " values");
        System.out.println("");
        System.out.println(
                this.utils.addStringPadding("Score", 5) + // Score
                " : " + this.utils.addStringPadding("Found", 30) + // Found Word
                " : " +  this.utils.addStringPadding("Decoded", 20) + // Decoded Value
                " : " + this.utils.addStringPadding("Key", 20) + // Key Value
                " : " + "Origonal" // Origonal Value
            );
        System.out.println("");
        for (int i = 0; i < 20; i++) {
            System.out.println(
                this.utils.addStringPadding((int)(scores.get(i).score), 5) + // Score
                " : " + this.utils.addStringPadding(scores.get(i).altStr, 30) + // Found Word
                " : " +  this.utils.addStringPadding(scores.get(i).str, 20) + // Decoded Value
                " : " + this.utils.addStringPadding(scores.get(i).fourthAltStr, 20) + // Key Value
                " : " + scores.get(i).thirdAltStr // Origonal Value
            );
        }

        String fileName = "playfair.csv";
        System.out.println("Saving Results to: " + fileName);
        try {
            // Generate the CSV String
            StringBuilder writeString = new StringBuilder();

            for (int i = 0; i < scores.size(); i++) {
                writeString.append(scores.get(i).score + // Score
                "," + scores.get(i).altStr + // Found Word
                "," + scores.get(i).str + // Decoded Value
                "," + scores.get(i).fourthAltStr + // Key Value
                "," + scores.get(i).thirdAltStr // Origonal Value
                );
                writeString.append("\n");
            }

            writeString.append("\n");
            FileWriter wr = new FileWriter(fileName);
            wr.write(writeString.toString());
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    private int hasDoubleX(String str) {    
        if (str.indexOf("x") == -1) { // Has no X
            return -1;
        }
        if (str.indexOf("x") == str.length() - 1) { // Ends with X
            return -1;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != 'x') { continue; }
            if (i == 0 || i >= str.length() - 2) { continue; }
            if (str.charAt(i - 1) == str.charAt(i + 1)) {
                return i;
            }
        }
        return -1;
    }
    private String removeDoubleX(String str) {
        int doubleX = this.hasDoubleX(str);
        if (doubleX == -1) {
            return str;
        }
        return this.utils.removeFromString(str, doubleX);
    }
    private String findPairs(char c1, char c2) {
        GridLoc loc1 = this.utils.charGrid_C_At(c1, true);
        GridLoc loc2 = this.utils.charGrid_C_At(c2, true);

        String result = "";
        // Same row
        if (loc1.y == loc2.y) {
            result += this.utils.charGrid_GetLeftShift(loc1);
            result += this.utils.charGrid_GetLeftShift(loc2);
            return result;
        }

        // Same col
        if (loc1.x == loc2.x) {
            result += this.utils.charGrid_GetUpShift(loc1);
            result += this.utils.charGrid_GetUpShift(loc2);
            return result;
        }

        // Forms a box... #sadFace
        result += this.utils.charGrid_Get(loc2.x, loc1.y);
        result += this.utils.charGrid_Get(loc1.x, loc2.y);
        //System.out.println("Same Box: " + c1 + c2 + " -> " + result);
        return result;
        

    }

    private String decodeString(String str, String key) {
        boolean printing = true;

        // Create the grid
        this.utils.charGrid_Create(5, 5, ' ');
        

        // Populate the board
        String alphabet = key + "abcdefghijklmnopqrstuvwxyz";
        int x = 0;
        int y = 0;
        for (int i = 0; i < alphabet.length(); i++) {
            if (!this.utils.charGrid_Contains(alphabet.charAt(i), true)) {
                this.utils.charGrid_Set(x, y, alphabet.charAt(i));
                x++;
                if (x >= 5) { y++; x = 0; }
            }
        }

        if (printing) { this.utils.charGrid_Print(); }

        // Read Board in reverse
        String newStr = "";
        for (int i = 0; i < str.length() - 1; i += 2) {
            String tmp = this.findPairs(str.charAt(i), str.charAt(i + 1));
            //System.out.print(tmp);
            newStr += tmp;
        }
        //System.out.println("\n");

        return this.removeDoubleX(newStr);
    }

}

