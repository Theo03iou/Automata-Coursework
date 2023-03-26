import java.util.*;

public class FSA
{
    public int numStates;
    public String[] alphabet;
    public Transition[] delta;
    public int[] finalStates;

    public FSA(int n, String[] a, Transition[] d, int[] f)
    {
        numStates = n;
        alphabet = a;
        finalStates = f;
        delta = d;
    }

    // Convert the FSA into a 5-tuple string representation
    public String toString()
    {
        String s = "("+toStringAsSet(alphabet)+", {";
        for(int i=0; i<numStates; i++)
        {
            if(i!=0) s += ", ";
            s += "q"+i;
        }
        s += "}, "+toStringAsSet(delta)+", q0, {";
        for(int i=0; i<finalStates.length; i++)
        {
            if(i!=0) s += (", ");
            s += "q"+finalStates[i];
        }
        return s+"})";
    }


    // check if FSA A accepts word w -- DFS implementation
    public Boolean isAccepted(Word w) {
        return isAcceptedRec(w,0,0);
    }
    
    private Boolean isAcceptedRec(Word w, int q, int i) {
        if(i==w.length) {
            for(int qF : finalStates)
                if(q==qF) return true;
            return false;
        }
        for(Transition t : delta) 
            if(t.from==q && t.label.equals(w.get(i)))
                if(isAcceptedRec(w,t.to,i+1)) return true;
        return false;
    }

    // Perform all kinds of checks on an FSA
    public String check()
    {
        // Check the alphabet is valid
        if(alphabet==null) return "Bad alphabet (null)";
        // Check the number of states is valid
        if(numStates<=0) return "Bad number of states ("+numStates+")";
        // Check that the transition relation is valid
        String checkTR = checkTRelation(delta,"transition relation");
	if (!checkTR.equals("")) return checkTR;        
        // Check that final states are valid
        if(finalStates==null) return "Bad (null) final states";
        for(int s : finalStates) {
            String checkS = checkState(s);
            if(!checkS.equals("")) return ("Bad final states element: "+checkS);
        }
        return "";
    }

    private String checkState(int s)
    {
        if (s<0 || s>=this.numStates) return ("incorrect state number ("+s+")");
        return "";
    }

    private String checkTRelation(Transition[] a,String name)
    {
        if(a==null) return ("Bad (null) "+name);
        for(Transition t : a) {
            String checkT = checkTransition(t);
            if (!checkT.equals("")) return ("Bad "+name+" element "+t+": "+checkT);
        }
        return "";
    }

    private String checkLabel(String l)
    {
        for(String s : alphabet) if(s.equals(l)) return "";
        if(!l.equals("")) return ("label not in alphabet ("+l+")"); return "";
    }

    private String checkTransition(Transition t)
    {
        String checkC = checkState(t.from);
        if(!checkC.equals("")) return checkC;
        checkC = checkState(t.to);
        if(!checkC.equals("")) return checkC;
        return checkLabel(t.label);
    }

    // Convert an array that represents a set into a string
    private String toStringAsSet(Object[] x)
    {
        String t = Arrays.toString(x);
        return "{"+t.substring(1,t.length()-1)+"}";
    }
    
    public static Object[] getSamples()
    {
        int num = 15;
        Object[][] tests = new Object[][]{
            new Object[]{3,3,"0","1","2",6,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"1",2,1,2},
            new Object[]{3,3,"0","1","2",9,1,"0",1,1,"2",1,2,"2",2,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"1",2,1,2},
            new Object[]{3,3,"0","1","2",18,1,"0",1,1,"0",2,1,"2",1,1,"2",2,2,"2",2,2,"2",0,0,"1",0,0,"1",1,0,"0",1,0,"0",2,0,"2",2,0,"2",1,1,"1",2,1,"1",0,2,"0",2,2,"0",1,2,"1",2,2,"1",0,1,2},
            new Object[]{3,3,"0","1","2",6,0,"0",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"1",2,1,2},
            new Object[]{3,3,"0","1","2",6,0,"0",0,0,"0",1,0,"0",2,1,"1",2,2,"0",2,2,"1",2,1,2},
            new Object[]{3,3,"0","1","2",6,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"0",1,1,2},
            new Object[]{3,3,"0","1","2",10,1,"0",0,1,"2",1,2,"2",1,2,"1",0,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"0",1,1,2},
            new Object[]{3,3,"0","1","2",7,0,"1",0,0,"0",1,0,"2",2,1,"0",2,2,"0",2,2,"0",0,2,"0",1,1,2},
            new Object[]{3,6,"0","1","2","a","b","c",7,0,"1",0,0,"0",1,0,"2",2,1,"0",2,2,"a",2,2,"b",0,2,"c",1,1,2},
            new Object[]{3,6,"0","1","2","a","b","c",7,0,"1",0,0,"0",1,0,"2",2,1,"0",2,2,"a",2,2,"b",2,2,"c",2,1,2},
            new Object[]{3,5,"0","1","2","a","b",7,0,"1",0,0,"1",1,0,"2",2,1,"0",2,2,"a",2,2,"b",2,2,"a",1,1,2},
            new Object[]{4,4,"a","b","c","d",16,0,"a",0,0,"b",1,0,"c",2,0,"d",3,1,"a",0,1,"b",1,1,"c",2,1,"d",3,2,"a",0,2,"b",1,2,"c",2,2,"d",3,3,"a",0,3,"b",1,3,"c",2,3,"d",3,4,0,1,2,3},
            new Object[]{1,5,"0","1","2","a","b",0,0},
            new Object[]{4,3,"a","b","c",16,0,"a",0,0,"b",1,0,"c",2,0,"c",3,1,"a",0,1,"b",1,1,"c",2,1,"a",3,2,"a",0,2,"b",1,2,"c",2,2,"b",3,3,"a",0,3,"b",1,3,"c",2,3,"b",3,4,0,1,2,3},
            new Object[]{4,3,"a","b","c",16,0,"a",0,0,"b",1,0,"c",2,0,"c",3,1,"a",0,1,"b",1,1,"c",2,1,"a",3,2,"a",0,2,"b",1,2,"c",2,2,"b",3,3,"a",0,3,"b",1,3,"c",2,0,"c",0,4,0,1,2,3}
        };
        FSA[] samples = new FSA[num];
        for(int i=0; i<num; i++)
            samples[i] = fromArray(tests[i]);
        Boolean[] resLeast1 = new Boolean[]{ false, true, true, false, false, false, true, false, false, false, false, true, false,true,true };
        Boolean[] resLeast2 = new Boolean[]{ false, false, true, false, false, false, false, false, false, false, false, false, false,false,false };
        Boolean[] resMost1 = new Boolean[]{ true, true, false, false, false, false, false, false, true, true, false, true, true,false,false };            
        Boolean[] resMost2 = new Boolean[]{ true, true, true, true, false, true, true, false, true, true, true, true, true,true,false };            
        return new Object[]{ samples, resLeast1,resLeast2, resMost1,resMost2};
    }
    
    public static FSA[] getCompleteDFAs()
    {
        int num = 5;
        Object[][] tests = new Object[][]{
            new Object[]{2,2,"a","b",4,0,"b",0,0,"a",1,1,"a",1,1,"b",0,1,1},
            new Object[]{3,2,"a","b",6,0,"b",2,0,"a",1,1,"a",1,1,"b",1,2,"a",2,2,"b",2,1,1},
            new Object[]{4,2,"a","b",8,0,"a",0,0,"b",1,1,"a",1,1,"b",2,2,"a",2,2,"b",3,3,"a",3,3,"b",3,1,2},
            new Object[]{3,3,"0","1","2",9,1,"0",1,1,"2",1,2,"2",2,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"1",2,1,2},
            new Object[]{4,4,"a","b","c","d",16,0,"a",0,0,"b",1,0,"c",2,0,"d",3,1,"a",0,1,"b",1,1,"c",2,1,"d",3,2,"a",0,2,"b",1,2,"c",2,2,"d",3,3,"a",0,3,"b",1,3,"c",2,3,"d",3,4,0,1,2,3},
        };
        FSA[] samples = new FSA[num];
        for(int i=0; i<num; i++)
            samples[i] = fromArray(tests[i]);
        return samples;
    }

    private static FSA fromArray(Object[] X)
    {
        int i,n,ns = (int)X[0];
        n = (int)X[1];
        String[] a = new String[n];
        for(i=2; i<n+2; i++)
            a[i-2] = (String)X[i];
        n = (int)X[i]; i++;
        Transition[] ts = new Transition[n];
        for(int j=0; j<n; i+=3, j++) 
            ts[j] = new Transition((int)X[i],(String)X[i+1],(int)X[i+2]);
        n = (int)X[i]; i++;
        int[] fs = new int[n];
        for(int j=0; j<n; i++, j++)
            fs[j] = (int)X[i];
        return new FSA(ns,a,ts,fs);
    }
}
