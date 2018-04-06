// Instructions:
// Write code for implementing factorial below.

public class SinTaylor {
	public static double lookup[];
    public static void main (String[] argv)
    {
	    	int range = 13;
	    	lookup = new double[range+1];			//Initialized with 0;

		for (int k=1; k<=range; k+=2) {
		    double alpha = 1.0 / factorial (k);
		    System.out.println ("alpha (without the sign): " + alpha);
		}
    }
    static double factorial (int k)
    {
    		if(k <= 1) {
    			return 1;
    		} 
    		if(lookup[k] != 0.0) {
    			return lookup[k];
    		}
    		lookup[k] = k * factorial(k-1);
    		return lookup[k];
    }
}
