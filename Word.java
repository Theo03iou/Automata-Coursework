import java.util.*;

public class Word
{
    private String[] letters;
    public int length;

    public Word(String[] l)
    {
        letters = l;
        length = l.length;
    }

    // Convert the Word into a string
    public String toString()
    {
    	if(length==0) return "epsilon";
        String s = "";
        for(String x : letters) s = s+x;
        return s;
    }

    // Get i-th letter of word
    public String get(int i)
    {
        return letters[i];
    }
}
