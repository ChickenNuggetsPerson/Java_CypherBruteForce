import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Utils.DictionarySys;
import Utils.UtilsFuncs;

public class Atbash {
    
    
    public static void main(String[] args) throws Exception {
        Atbash test = new Atbash();

        System.out.println(test.getSemetricChar('a'));
        System.out.println(test.getSemetricChar('b'));
        System.out.println(test.getSemetricChar('z'));
    }

    private List<String> strList = new ArrayList();
    private UtilsFuncs utils;

    Atbash(List<String> strs) throws FileNotFoundException {
        this.utils = new UtilsFuncs();
        this.strList = strs;
    };
    Atbash() throws FileNotFoundException {
        this.utils = new UtilsFuncs();
    };

    public void solve() {
        for (int i = 0; i < strList.size(); i++) {
            StringBuilder tmp = new StringBuilder();
            for (int s = strList.get(i).length(); s < 20; s++) {
                tmp.append(" ");
            }
            System.out.println(strList.get(i) + tmp.toString() + " : " + this.convertString(strList.get(i)));
        }
    }


    private String convertString(String str) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            newStr.append(this.getSemetricChar(str.charAt(i)));
        }
        return newStr.toString();
    }
    private char getSemetricChar(char c) {
        if (this.utils.isSpecialChar(c)) { return c; }
        return this.utils.convertIntToChar(25 - this.utils.convertCharToNum(c));
    }
}
