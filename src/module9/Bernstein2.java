// Instructions:
// Copy over your code for computing n-choose-k
// Implement the recursive version of computing Bernsteins.

import java.util.*;

public class Bernstein2 {

    static int numCalls = 0;
    static int numCallsRecursive = 0;
    static int lookup[];
	static HashMap<ArrayList<Integer>,Double> lookupNewTable;
	static HashMap<ArrayList<Integer>,Double> lookupTable;
    public static void main (String[] argv)
    {
	int n = 5;

	// For variety ... we're swapping the loops. This time, the
	// outer loop is over t, and we do each berntein polynomial
	// for n,k inside. Nothing is being plotted, since we're only
	// comparing efficiency.
	lookup = new int[n];
	int numIntervals = 100;
	double deltaT = 1.0 / numIntervals;
	lookupTable = new HashMap<>();
	lookupNewTable = new HashMap<>();
	
	for (double t=0; t<=1; t+=deltaT) {
	    for (int k=0; k<=n; k++) {
		double b = bernstein (n, k, t);
		double b2 = bernsteinRecursive (n, k, t);
		if (Math.abs(b-b2) > 0.0001) {
		    // Error.
		    System.out.println ("t=" + t + " n=" + n + " k=" + k + " b=" + b + " b2=" + b2);
		}
	    }
	}

	System.out.println ("numCalls=" + numCalls + " numCallsRecursive=" + numCallsRecursive);
    }

    public static double bernsteinRecursive (int n, int k, double t)
    {
	numCallsRecursive ++;
	if ((n==k) || (n==1) || (k==0)) {
//		System.out.println("n: " + n + " k: " + k + " r: "+ 1);
	    return 1;
	}
	
	if(nkInDoubleMap(n,k)) {
		return getDoubleValue(n,k);
	}
	
    ArrayList<Integer> list = new ArrayList<Integer>(2);
	list.add(n);
	list.add(k);
	lookupTable.put(list, bernsteinRecursive(n-1,k,t) + bernsteinRecursive(n-1,k-1,t) );
	return lookupTable.get(list);
    }


    public static double bernstein (int n, int k, double t)
    {
	double b = numCombinations(n,k) * Math.pow (t, k) * Math.pow (1-t,n-k);
	return b;
    }

	private static double getDoubleValue(int n, int k) {
		ArrayList<Integer> list = new ArrayList<Integer>(2);
		list.add(n);
		list.add(k);
		return lookupNewTable.get(list);
	}

    private static boolean nkInDoubleMap(int n, int k) {
		ArrayList<Integer> list = new ArrayList<Integer>(2);
		list.add(n);
		list.add(k);
		if(lookupNewTable.containsKey(list)) {
			return true;
		}
		return false;
	}

    
    static double numCombinations (int n, int k)
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
	numCalls ++;
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
