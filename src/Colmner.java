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

public class Colmner {
    
    public static void main(String[] args) throws Exception {
        
        List<String> strList = new ArrayList<>();
        //strList.add("voied");
        strList.add("carmlwosdosypo");

        Colmner test = new Colmner(strList);
        //test.solve("pizza");
        test.solve_brute();
    }

    private List<String> strList = new ArrayList();
    private UtilsFuncs utils;

    Colmner(List<String> strs) throws FileNotFoundException {
        this.utils = new UtilsFuncs();
        this.strList = strs;
    };
    Colmner() throws FileNotFoundException {
        this.utils = new UtilsFuncs();
    };

    public void solve(String key) {
        System.out.println("\nSolving");

        System.out.println("");

        for (int i = 0; i < strList.size(); i++) {
            String result = decodeString(strList.get(i), key);
            StringBuilder tmp = new StringBuilder();
            for (int s = strList.get(i).length(); s < 20; s++) {
                tmp.append(" ");
            }
            System.out.println(strList.get(i) + tmp.toString() + " : " + result);
        }
    }   
    public void solve_brute() {

        int maxSize = this.utils.dictSys.strStorage.size() * strList.size();
        ProgressBar bar = new ProgressBar(0.00, maxSize, 0.00, "Progress...");
        bar.start();

        List<StringScore> scores = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            for (int z = 0; z < this.utils.dictSys.strStorage.size(); z++) {

                bar.updateVal((i * this.utils.dictSys.strStorage.size()) + z);

                String result = this.decodeString(strList.get(i), this.utils.dictSys.strStorage.get(z));
                //int score = this.utils.evaluateString(result);
                //if (score >= 2) {
                    List<String> scoreStr = this.utils.evaluateString_S(result);
                    StringBuilder tmpBuilder = new StringBuilder();
                    double avgCount = 0.00;
                    for (int x = 0; x < scoreStr.size(); x++) {
                        avgCount += scoreStr.get(x).length();
                        tmpBuilder.append(scoreStr.get(x) + "_");
                    }   
                    avgCount = avgCount / scoreStr.size();

                    StringScore newScore = new StringScore(result, avgCount, 0);
                    newScore.altStr = tmpBuilder.toString();
                    newScore.thirdAltStr = strList.get(i);
                    scores.add(newScore);
                //}
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
                " : " + this.utils.addStringPadding("Found", 10) + // Found Word
                " : " +  this.utils.addStringPadding("Decoded", 20) + // Decoded Value
                " : " + this.utils.addStringPadding("Key", 20) + // Key Value
                " : " + "Origonal" // Origonal Value
            );
        System.out.println("");
        for (int i = 0; i < 20; i++) {
            System.out.println(
                this.utils.addStringPadding((int)(scores.get(i).score), 5) + // Score
                " : " + this.utils.addStringPadding(scores.get(i).str.substring(0, (int)scores.get(i).score), 10) + // Found Word
                " : " +  this.utils.addStringPadding(scores.get(i).str, 20) + // Decoded Value
                " : " + this.utils.addStringPadding(scores.get(i).altStr, 20) + // Key Value
                " : " + scores.get(i).thirdAltStr // Origonal Value
            );
        }

        String fileName = "colmner.csv";
        System.out.println("Saving Results to: " + fileName);
        try {
            // Generate the CSV String
            StringBuilder writeString = new StringBuilder();

            for (int i = 0; i < scores.size(); i++) {
                writeString.append(scores.get(i).score + // Score
                "," + scores.get(i).str.substring(0, (int)scores.get(i).score) + // Found Word
                "," + scores.get(i).str + // Decoded Value
                "," + scores.get(i).altStr + // Key Value
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

    private class CharIndex {
        char c;
        int i;
        int numVal;
        CharIndex(char c, int i, int numVal) {
            this.c = c;
            this.i = i;
            this.numVal = numVal;
        }
    }
    private char getCharAtIndex(List<CharIndex> l, int index) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).i == index) { return l.get(i).c; }
        }
        return ' ';
    }
    private int getScoreAtIndex(List<CharIndex> l, int index) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).i == index) { return i; }
        }
        return -1;
    }
    private String decodeString(String str, String key) {
        boolean printing = false;


        // Create the grid
        int ySize = str.length() / key.length();
        if (str.length() % key.length() != 0) {
            ySize++;
            this.utils.charGrid_Create(key.length(), ySize, ' ');

            // Set empty spots
            int bottomRow = (str.length() % key.length()) + 1;
            for (int i = key.length() - 1; i >= bottomRow - 1; i--) {
                this.utils.charGrid_Set(i, ySize - 1, '*');
            }

        } else {
            this.utils.charGrid_Create(key.length(), ySize, ' ');
        }

        // Generate Index Scores
        List<CharIndex> scores = new ArrayList<>();
        for (int i = 0; i < key.length(); i++) {
            scores.add(new CharIndex(key.charAt(i), i, this.utils.convertCharToNum(key.charAt(i))));
        }
        // Sort by character
        Collections.sort(scores, new Comparator<CharIndex>() {
            @Override
            public int compare(CharIndex o1, CharIndex o2) {
                return o1.numVal - o2.numVal;
            }
        });

        if (printing) {
            for (int i = 0; i < key.length(); i++) {
                System.out.print(key.charAt(i) + " ");
            }
            System.out.print("\n");
            for (int i = 0; i < scores.size(); i++) {
                System.out.print(this.getScoreAtIndex(scores, i) + 1 + " ");
            }
            System.out.print("\n");  
            
            this.utils.charGrid_Print();

            System.out.print("\n");  
            System.out.print("\n");  
        }
        
        // Populate Board
        int stringIndex = 0;
        for (int i = 0; i < scores.size(); i++) {
            for (int yPos = 0; yPos < ySize; yPos++) {
                if (this.utils.charGrid_Get(scores.get(i).i, yPos) != '*') {
                    this.utils.charGrid_Set(scores.get(i).i, yPos, str.charAt(stringIndex));
                    stringIndex++;
                }
            }
        }
        // Read the Board
        StringBuilder newStr = new StringBuilder();
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < key.length(); x++) {
                if (this.utils.charGrid_Get(x, y) == '*') { continue; }
                newStr.append(this.utils.charGrid_Get(x, y));
            }
        }

        if (printing) {
            for (int i = 0; i < key.length(); i++) {
                System.out.print(key.charAt(i) + " ");
            }
            System.out.print("\n");
            for (int i = 0; i < scores.size(); i++) {
                System.out.print(this.getScoreAtIndex(scores, i) + 1 + " ");
            }
            System.out.print("\n");  
            
            this.utils.charGrid_Print();
        }
    
        return newStr.toString();
    }

}

