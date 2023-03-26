public class Code
{
    public static void main(String[] args) 
    {
        // DEMO code, using automaton of Figure 1

        FSA A0=generateFSA0();

        // Check and print the automaton A0
        checkPrintFSA(A0,"A0");

        // Check if A0 accepts some words
        Word w = new Word(new String[]{"0","0","1"});
        System.out.println("accepts "+w+": "+A0.isAccepted(w));
        w = new Word(new String[]{"1","1","2"});        
        System.out.println("accepts "+w+": "+A0.isAccepted(w));
        w = new Word(new String[]{"2","0","1"});        
        System.out.println("accepts "+w+": "+A0.isAccepted(w));
        w = new Word(new String[]{});        
        System.out.println("accepts "+w+": "+A0.isAccepted(w));

        // Check if A0 is least-1 -- the method is not implemented
        System.out.println("least-1: "+isLeast(A0,1));
        // Check if A0 is most-1 -- the method is not implemented
        System.out.println("most-1: "+isMost(A0,1));


        // ---------
        // MAIN CODE
        // ---------
        // The code below tests the methods that you need to implement and prints
        // out related messages. Do not change this; instead, change the bodies 
        // of methods generateFSA1, etc. as specified in the coursework questions.

        FSA[] As = new FSA[]{generateFSA1(),generateFSA2()};
        String[] names = new String[]{"A1","A2"};
        
        // Questions 13-14
        System.out.println("\nPrintout (Q13-14)");
        checkPrintFSAs(As,names);

        // Question 15
        System.out.println("\nPrintout (Q15)");
        runFSAs(As,names);

        // Question 15 (isLeast/isMost)
        // you can comment out the test for the method that you are NOT implementing
        System.out.println("\nPrintout (Q16)");
        testIsLeast();
        testIsMost();

        // Question 16
        System.out.println("\nPrintout (Q17)");
        testComplement();
    }

    // Demo FSA, do not change this
    public static FSA generateFSA0() {
        String[] alphabet = new String[]{ "0", "1", "2" };
        Transition[] delta = new Transition[] { 
            new Transition(0,"0",0), 
            new Transition(0,"0",1),
            new Transition(0,"2",2),
            new Transition(1,"1",2),
            new Transition(2,"0",2),
            new Transition(2,"1",2)
        };
        int[] finals = new int[] { 2 };
        FSA A = new FSA(3,alphabet,delta,finals);
        return A;
    }

    // -----------------------------------
    // TODO CODE (REQUIRES IMPLEMENTATION)
    // -----------------------------------

    // TODO construct FSA of Question 13 and return it
    public static FSA generateFSA1() {
        return null; // TODO remove this
    }

    // TODO construct FSA of Question 14 and return it
    public static FSA generateFSA2() {
        return null; // TODO remove this
    }

    // TODO implement this for Question 15
    public static void runFSA(FSA A, String name, Word input) {
    }
    
    // TODO return true if A is least-k (Question 16)
    public static Boolean isLeast(FSA A, int k) {
        return null; // TODO remove this
    }

    // TODO return true if A is most-k (Question 16)
    public static Boolean isMost(FSA A, int k) {
        int count = 0;
        String[] ab = A.alphabet;
        int states = A.numStates;
        int[][] aCount = new int[states][ab.length];
        Transition[] t = A.delta;
        for (int i = 0; i < states; i++) {
            for (int j = 0; j < states; j++) {
                for (int z = 0; z < ab.length; z++) {
                    if (t[j].from == i && t[j].label == ab[z]) {
                        aCount[i][z]++;
                    }
                }
            }
        }
        for (int i = 0; i < states; i++) {
            for (int z = 0; z < ab.length; z++) {
                if (count < aCount[i][z]) {
                    count = aCount[i][z];
                }
            }
        }

        if (count <= k) {
            return true;
        }
        return false;
    }

    // TODO return a new FSA that is the complement of A (Question 17)
    // This method assumes that A is a complete deterministic FSA
    public static FSA complement(FSA A) {
    	return null; // TODO remove this
    }

    // --------------------------------
    // DO NOT CHANGE THE REMAINING CODE    
    // --------------------------------

    // print FSAs A, with given names, after checking they are valid
    public static void checkPrintFSAs(FSA[] As, String[] names) {
        for (int i=0; i<As.length; i++)
            checkPrintFSA(As[i],names[i]);
    }    
    
    // print FSA A, with given name, after checking it is valid
    public static void checkPrintFSA(FSA A, String name) {
	        if(A==null) return;
        	String s = A.check();
	        if(s!="") System.out.println("Error found in "+name+":\n"+s);
        	else System.out.println(name+" = "+A);
    }    

    // run FSAs, using runFSA method (Q15)
    public static void runFSAs(FSA[] As, String[] names) {
    	Word[] inputs = new Word[]{
    	        new Word(new String[]{}), 
    	        new Word(new String[]{"2"}), 
    	        new Word(new String[]{"3"}), 
    	        new Word(new String[]{"0","2","2"}), 
    	        new Word(new String[]{"0","2","3"}), 
    	        new Word(new String[]{"2","3","1"}), 
    	        new Word(new String[]{"3","2","1","1","2"}), 
    	        new Word(new String[]{"3","5","5","1","3"}),
    	        new Word(new String[]{"1","2","3","1","2"}),
    	        new Word(new String[]{"1","1","5","1","2"})
    	};
        for(int i=0; i<As.length; i++)
            	for(Word input : inputs)
        	    runFSA(As[i],names[i],input);
    }

    // test isLeast method on 15 FSAs (Q16)
    public static void testIsLeast() {
        System.out.println("Testing isLeast method");
        int j, marks = 0;
        Object[] X = FSA.getSamples();
        FSA[] tests = (FSA[])X[0];
        Boolean[] res1 = (Boolean[])X[1];
        Boolean[] res2 = (Boolean[])X[2];
        for(int i=0; i<tests.length; i++) {
            System.out.println("Test "+(i+1)+": "+tests[i]);
            j = (res1[i]==isLeast(tests[i],1)) ? 1 : 0;
            System.out.println("Result (k="+1+"): "+j+" (was "+res1[i]+")");
            marks += j;
            j = (res2[i]==isLeast(tests[i],2)) ? 1 : 0;
            System.out.println("Result (k="+2+"): "+j+" (was "+res2[i]+")");
            marks += j;
        }
        System.out.println("Total: "+marks);
    }

    // test isMost method on 15 FSAs (Q16)
    public static void testIsMost() {
        System.out.println("Testing isMost method");
        int j, marks = 0;
        Object[] X = FSA.getSamples();
        FSA[] tests = (FSA[])X[0];
        Boolean[] res1 = (Boolean[])X[3];
        Boolean[] res2 = (Boolean[])X[4];
        for(int i=0; i<tests.length; i++) {
            System.out.println("Test "+(i+1)+": "+tests[i]);
            j = (res1[i]==isMost(tests[i],1)) ? 1 : 0;
            System.out.println("Result (k="+1+"): "+j+" (was "+res1[i]+")");
            marks += j;
            j = (res2[i]==isMost(tests[i],2)) ? 1 : 0;
            System.out.println("Result (k="+2+"): "+j+" (was "+res2[i]+")");
            marks += j;
        }
        System.out.println("Total: "+marks);
    }
    
    // test the complement method on 10 complete DFAs (Q17)
    public static void testComplement() {
        System.out.println("Testing complement method");
        FSA[] As = FSA.getCompleteDFAs();
        for(int i=0; i<As.length; i++) {
            System.out.println("Test "+(i+1)+":     "+As[i]);
            System.out.println("Complement: "+complement(As[i]));
        }
    }
    
}
