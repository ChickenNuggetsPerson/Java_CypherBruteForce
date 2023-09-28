import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
    
        List<String> strList = new ArrayList<>();
       
        // Simple substitution ( Key word ) 
        // Colomner 

        //strList.add("yjkngecvutgkip");       // => Caesar     =>  while cats reign
        //strList.add("zuifrg,yfgzgvxs");      // => Atbash     =>  a fruit, but a tech
        //strList.add("ahttorteliaeoteuml");   // => Rail Fence =>  are the ultimate loot
        //strList.add("tseeh'ahreneentsrroc"); // => Rail Fence =>  there's another screen
        //strList.add("njtejsfdujpo");         // => Caesar     =>  misdirection
        //strList.add("gqneptpbgbktehpypy");   // => Playfair   =>  in the social spree
        strList.add("igeouwhenjjttl.ts");    
        strList.add("wcspsetvgqmhly");
        //strList.add("abgyro,ipcyoeysgb");    // => Playfair   =>  salute, where ducks
        

        //Caesar C_solver = new Caesar(strList); C_solver.solve();
        //Atbash A_solver = new Atbash(strList); A_solver.solve();
        //RailFence R_solver = new RailFence(strList); 
        //R_solver.solve(3);
        

        Colmner Col_solver = new Colmner(strList); 
        //Col_solver.solve("cats");
        Col_solver.solve_brute(true);

        //Playfair P_Solver = new Playfair(strList);
        //P_Solver.solve_brute(true);
        //P_Solver.solve("videos");
    }
}
