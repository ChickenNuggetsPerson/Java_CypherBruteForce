import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Utils.UtilsFuncs;

public class RailFence {
    
    public static void main(String[] args) throws Exception {
        
        List<String> strList = new ArrayList<>();
        RailFence test = new RailFence(strList);
        
        //strList.add("voied");
        strList.add("msyascpwolodor");
        test.solve(5);
    }

    private List<String> strList = new ArrayList();
    private UtilsFuncs utils;

    RailFence(List<String> strs) throws FileNotFoundException {
        this.utils = new UtilsFuncs();
        this.strList = strs;
    };
    RailFence() throws FileNotFoundException {
        this.utils = new UtilsFuncs();
    };

    public void solve(int fenceSize) {
        System.out.println("\nSolving");

        System.out.println("");

        for (int i = 0; i < strList.size(); i++) {
            String result = decodeString(strList.get(i), fenceSize);
            StringBuilder tmp = new StringBuilder();
            for (int s = strList.get(i).length(); s < 20; s++) {
                tmp.append(" ");
            }
            System.out.println(strList.get(i) + tmp.toString() + " : " + result);
        }
        
    }   
    private String decodeString(String str, int fenceSize) {
        
        // Create the grid
        this.utils.charGrid_Create(str.length(), fenceSize, ' ');
        
        // Fill spots with placeholder values
        {
            int x = 0;
            int y = 0;
            int yVel = 0;
            for (int i = 0; i < str.length(); i++) {
                if (y <= 0) { yVel = 1; }
                if (y >= fenceSize - 1) { yVel = -1; }
                this.utils.charGrid_Set(x, y, '_');
                y += yVel;
                x++;
            }
        }

        // Place characters into empty spaces
        {
            int x = 0;
            int y = 0;
            for (int i = 0; i < str.length(); i++) {
                while (this.utils.charGrid_Get(x, y) != '_') {
                    if (x >= str.length() - 1) { x = 0; y++; } else { x++; }
                }
                this.utils.charGrid_Set(x, y, str.charAt(i));
                x++;
            }
        }

        // Read decifered string
        StringBuilder newStr = new StringBuilder();
        
        {
            int x = 0;
            int y = 0;
            int yVel = 0;
            for (int i = 0; i < str.length(); i++) {
                if (y <= 0) { yVel = 1; }
                if (y >= fenceSize - 1) { yVel = -1; }
                newStr.append(this.utils.charGrid_Get(x, y));
                y += yVel;
                x++;
            }
        }

        return newStr.toString();
    }
}

