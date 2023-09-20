package Utils;

import java.io.FileNotFoundException;

public class UtilsFuncs {

    private DictionarySys dictSys;
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
}
