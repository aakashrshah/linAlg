// Instructions:
// Write code in factorial() and then numCombinations()
// Calculate by hand numCombinations(5,k) for k=0,1,..,5.
// Compare with the computation.

public class Combinations {

	static int lookup[];
    public static void main (String[] argv)
    {
	// Try k=0,1,2,3,4,5.
	int k = 1;
	
	int r = numCombinations (5,k);
	System.out.println (r);
    }

    static int numCombinations (int n, int k)
    {
    	
    		lookup = new int[n+1];
    		int n1 = factorial(n);
    		lookup = new int[k+1];
    		int k1 = factorial(k);
    		lookup = new int[(n-k)+1];
    		int nk = factorial(n-k);
    		return (n1/(k1 * nk));
    }

    static int factorial (int k)
    {
		if(k <= 1) {
			return 1;
		} 
		if(lookup[k] != 0) {
			return lookup[k];
		}
		lookup[k] = k * factorial(k-1);
		return lookup[k];

    }

}
