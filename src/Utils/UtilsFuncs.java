package Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilsFuncs {

    public static void main(String[] args) throws Exception {
        UtilsFuncs test = new UtilsFuncs();

        System.out.println(test.evaluateString_S("ducksliketoswimx"));
    }

    public DictionarySys dictSys;

    public UtilsFuncs() throws FileNotFoundException {
        this.dictSys = new DictionarySys();
    }

    public int convertCharToNum(char c) {
        return Character.getNumericValue(Character.toLowerCase(c)) - 10;
    }
    public char convertIntToChar(int i) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        if (i >= 0) {
            return alphabet.charAt(i % 26);
        }
        return alphabet.charAt(26 - Math.abs(i % 26));
    }
    public boolean isSpecialChar(char c) {
        return Character.getNumericValue(c) == -1;
    }
    public String insertIntoString(String baseStr, String insertStr, int index) {
        String newStr = "";
        for (int i = 0; i < baseStr.length(); i++) {
            if (i == index) {
                newStr += insertStr;
                newStr += baseStr.charAt(i);
            } else {
                newStr += baseStr.charAt(i);
            }
        }
        return newStr;
    }
    public String insertIntoString(String baseStr, char insertChar, int index) {
        String test = "";
        test += insertChar;
        return this.insertIntoString(baseStr, test, index);
    }

    public int evaluateString(String str) {
        for (int i = str.length(); i >= 0; i--) {
            String subStr = this.insertIntoString(str, ' ', i);
            if (this.dictSys.storage.contains(subStr.split(" ")[0])) {
                return i;
            }
        }
        return 0;
    }
   
    
    public List<String> evaluateString_S(String str) {

        String tmpStr = str;
        tmpStr = str.toLowerCase();
        //StringBuilder largest = new StringBuilder();
        List<String> returnList = new ArrayList<>();

        while (tmpStr.length() >= 0) {
            String found = "";
            for (int i = 0; i < this.dictSys.strStorage.size(); i++) {
                if (tmpStr.contains(this.dictSys.strStorage.get(i))) {
                    if (this.dictSys.strStorage.get(i).length() > found.length()) {
                        found = this.dictSys.strStorage.get(i);
                    }
                }
            }
            if (found.equals("")) {
                break;
            }
            if (found.length() <= 1 && tmpStr.length() <= 1) { 
                //largest.append(tmpStr + "_");
                returnList.add(tmpStr);
                //largest.append("-------");
                break; 
            }

            tmpStr = tmpStr.replace(found, "");

            //largest.append(found + "_");
            returnList.add(found);
            //System.out.println(found);   
        }
        if (tmpStr.length() == 0) {
            //largest.append("-------");
        }

        return returnList;
    }


    public String addStringPadding(String str, int p) {
        StringBuilder tmp = new StringBuilder();
        tmp.append(str);
        for (int s = str.length(); s < p; s++) {
            tmp.append(" ");
        }
        return tmp.toString();
    }
    public String addStringPadding(int str, int p) {
        StringBuilder tmp = new StringBuilder();
        tmp.append(str);
        for (int s = tmp.length(); s < p; s++) {
            tmp.append(" ");
        }
        return tmp.toString();
    }

    private char[][] charStorage;
    private int xSize;
    private int ySize;
    private char defaultChar;

    public void charGrid_Create(int xSize, int ySize, char defaultChar) {
        this.charStorage = new char[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
        this.defaultChar = defaultChar;
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                this.charStorage[x][y] = defaultChar;
            }
        }
    }
    public char charGrid_Get(int x, int y) {
        if (x < 0 || x >= this.xSize) { return this.defaultChar; }
        if (y < 0 || y >= this.ySize) { return this.defaultChar; }
        return this.charStorage[x][y];
    }
    public void charGrid_Set(int x, int y, char c) {
        if (x < 0 || x >= this.xSize) { return; }
        if (y < 0 || y >= this.ySize) { return; }
        this.charStorage[x][y] = c;
    }
    public void charGrid_Print() {
        for (int i = 0; i < xSize; i++) {
            System.out.print("_ ");
        }
        System.out.println("");

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                System.out.print(this.charGrid_Get(x, y) + " ");
            }
            System.out.print("\n");
        }

        for (int i = 0; i < xSize; i++) {
            System.out.print("‾ ");
        }
        System.out.println("");
    }
    public class GridLoc {
        public int x;
        public int y;
        public char c;
        GridLoc(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }
    public GridLoc charGrid_C_At(char c, boolean isPlayfair) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (isPlayfair && ( c == 'i' || c == 'j')) {
                    if (this.charStorage[x][y] == 'i' || this.charStorage[x][y] == 'j') {
                        return new GridLoc(x, y, c);
                    }
                } else {
                    if (this.charStorage[x][y] == c) {
                        return new GridLoc(x, y, c);
                    }
                }
            }
        }
        return new GridLoc(-1, -1, ' ');
    }
    public boolean charGrid_Contains(char c, boolean isPlayfair) {
        if (isPlayfair && ( c == 'i' || c == 'j')) {

            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    if (this.charStorage[x][y] == 'i' || this.charStorage[x][y] == 'j') {
                        return true;
                    }
                }
            }

        } else {
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    if (this.charStorage[x][y] == c) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public char charGrid_GetLeftShift(GridLoc loc) {
        if (loc.x <= 0) {
            return this.charStorage[xSize - 1][loc.y];
        }
        return this.charStorage[loc.x - 1][loc.y];
    }
    public char charGrid_GetUpShift(GridLoc loc) {
        if (loc.y <= 0) {
            return this.charStorage[loc.x][ySize - 1];
        }
        return this.charStorage[loc.x][loc.y - 1];
    }
}
