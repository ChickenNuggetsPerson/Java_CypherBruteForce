package Utils;
public class StringScore {
    public String str;
    public String altStr;
    public String thirdAltStr;
    public String fourthAltStr;
    public double score;
    public int shift;
    public StringScore(String str, double score, int shift) {
        this.str = str;
        this.score = score;
        this.shift = shift;
    }
}