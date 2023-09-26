import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
    

        // TODO: Add doubles on end for playfair

        List<String> strList = new ArrayList<>();
       
        //strList.add("yjkngecvutgkip");       // => Caesar     =>  while cats reign
        //strList.add("zuifrg,yfgzgvxs");      // => Atbash     =>  a fruit, but a tech
        strList.add("ahttorteliaeoteuml");
        //strList.add("tseeh'ahreneentsrroc"); // => Rail Fence =>  there's another screen
        //strList.add("njtejsfdujpo");         // => Caesar     =>  misdirection
        strList.add("gqneptpbgbiuehpy");
        strList.add("igeouwhenjjttl.ts");
        strList.add("wcspsetvgqmhly");

        //Caesar C_solver = new Caesar(strList); C_solver.solve();
        
        //Atbash A_solver = new Atbash(strList); A_solver.solve();
        
        //RailFence R_solver = new RailFence(strList); R_solver.solve(4);
        
        //Colmner Col_solver = new Colmner(strList); 
        //Col_solver.solve("hoper");
        //Col_solver.solve_brute();

        Playfair P_Solver = new Playfair(strList);
        //P_Solver.solve("cryptogram");
        P_Solver.solve_brute(false);
    }
}
