import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Utils.DictionarySys;
import Utils.StringScore;
import Utils.UtilsFuncs;

public class Caesar {
    private class SpecialPos {
        char c;
        int pos;
        SpecialPos(char c, int pos) {
            this.c = c;
            this.pos = pos;
        }
    } 

    public static void main(String[] args) throws Exception {

        Caesar test = new Caesar();

        System.out.println(test.shiftString("a,b,c", 1));

    }

    private UtilsFuncs utils;

    private List<String> strList = new ArrayList();
    Caesar(List<String> strs) throws FileNotFoundException {
        this.strList = strs;
        this.utils = new UtilsFuncs();
    };
    Caesar() throws FileNotFoundException {
        this.utils = new UtilsFuncs();
    };

    public void solve() {
        int maxShift = 30;
        String fileName = "caesar.csv";

        String[][] data = new String[strList.size()][maxShift];

        List<StringScore> tmp = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (int x = 0; x < strList.size(); x++) {
            for (int y = 0; y < maxShift; y++) {
                data[x][y] = this.shiftString(strList.get(x), y);
                //System.out.println(Csolver.evaluateString(data[x][y]));
                if (seen.contains(data[x][y])) { continue; }
                seen.add(data[x][y]);
                tmp.add(new StringScore(data[x][y], this.utils.evaluateString(data[x][y]), y));
            }
        }


        Collections.sort(tmp, new Comparator<StringScore>() {
            @Override
            public int compare(StringScore o1, StringScore o2) {
                return o1.score - o2.score;
            }
        });
        for (int i = 0; i < tmp.size(); i++) {
            if (tmp.get(i).score <= 1) {
                continue;
            }
            System.out.println(tmp.get(i).score + " : Shift: " + tmp.get(i).shift + " : " + tmp.get(i).str.substring(0, tmp.get(i).score) + " : " +  tmp.get(i).str);
        }

        System.out.println("Saving Results to: " + fileName);
        try {
            // Generate the CSV String
            StringBuilder writeString = new StringBuilder();

            for (int i = 0; i < strList.size(); i++) {
                writeString.append(strList.get(i));
                writeString.append(",");
            }
            writeString.append("\n");
            
            for (int i = 0; i < strList.size(); i++) {
                writeString.append(" ");
                writeString.append(",");
            }
            writeString.append("\n");
            
            for (int y = 0; y < maxShift; y++) {
                for (int x = 0; x < strList.size(); x++) {
                    writeString.append(y + " " + data[x][y]);
                    writeString.append(",");
                }
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



    public String shiftString(String str, int amount) {
        List<Integer> numericRep = new ArrayList();
        List<SpecialPos> posList = new ArrayList();

        for (int i = 0; i < str.length(); i++) {
            if (this.utils.isSpecialChar(str.charAt(i))) {
                posList.add(new SpecialPos(str.charAt(i), i));
                continue;
            }
            numericRep.add(this.utils.convertCharToNum(str.charAt(i)));
        }

        for (int i = 0; i < numericRep.size(); i++) {
            numericRep.set(i, numericRep.get(i) + amount);
        }

        String result = "";
        for (int i = 0; i < numericRep.size(); i++) {
            result += this.utils.convertIntToChar(numericRep.get(i));
        }

        for (int i = 0; i < posList.size(); i++) {
            result = this.utils.insertIntoString(result, posList.get(i).c, posList.get(i).pos);
        }

        return result;
    }
    
}
