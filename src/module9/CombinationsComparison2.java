import java.util.ArrayList;
import java.util.HashMap;

// Instructions:
// Copy over your code from CombinationsComparison into 
// methods numCombinations() and numCombinationsRecursive()
// Then implement the second recursive approach in numCombinationsRecursive2()
// and the iterative approach in numCombinationsIterative()

public class CombinationsComparison2 {

    static int numCalls = 0;
    static int numCallsRecursive = 0;
    static int numCallsRecursive2 = 0;
    static int numIterations = 0;
	static int lookup[];
	static HashMap<ArrayList<Integer>,Double> lookupNewTable;
	static HashMap<ArrayList<Integer>,Integer> lookupTable;

    public static void main (String[] argv)
    {
	// Try n=5, n=10, n=20.
	int n = 5;

	for (int k=0; k<=n; k++) {
		lookupTable = new HashMap<>();
		lookupNewTable = new HashMap<>();
		
	    int p = numCombinations (n,k);
	    int q = numCombinationsRecursive (n,k);
	    double r = numCombinationsRecursive2 (n,k);
	    double s = numCombinationsIterative (n,k);
	    System.out.println ("n=" + n + "\tk=" + k + "\tp=" + p + "\tq=" + q + "\tr=" + r + "\ts=" + s + "\tnumCalls=" + numCalls + "\tnumCallsRecursive=" + numCallsRecursive + "\tnumCallsRecursive2=" + numCallsRecursive2 + "\tnumIterations=" + numIterations);
	}
	System.out.println ("\nTotalnumCalls=" + numCalls + "\nTotalnumCallsRecursive=" + numCallsRecursive + "\nTotalnumCallsRecursive2=" + numCallsRecursive2 + "\nTotalnumIterations=" + numIterations);
    }

    static double numCombinationsIterative (int n, int k)
    {
		double result = 1.0;
		for(int i= 0;i<=n;i++) {
			if((k-i) > 0) {
				result *= ((n-i)*(1.0))/((k-i)*(1.0));
				numIterations++;			
			}
		}
		return result;
    }

    static double numCombinationsRecursive2 (int n, int k)
    {
	    	numCallsRecursive2 ++;
	    if(n==k || n==1 || k==0) {
	    		return 1.0;
	    }
	    
	    if(nkInDoubleMap(n,k)) {
	    		return getDoubleValue(n,k);
	    }
	    
	    ArrayList<Integer> list = new ArrayList<Integer>(2);
	    list.add(n);
	    list.add(k);
	    double ans = (n * 1.0)/(k * 1.0);
	    lookupNewTable.put(list,ans * numCombinationsRecursive2(n-1,k-1));
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

	private static double getDoubleValue(int n, int k) {
		ArrayList<Integer> list = new ArrayList<Integer>(2);
		list.add(n);
		list.add(k);
		return lookupNewTable.get(list);
	}
	
	static int numCombinationsRecursive (int n, int k)
    {
		numCallsRecursive++;
	if ((n==k) || (n==1) || (k==0)) {
//		System.out.println("n: " + n + " k: " + k + " r: "+ 1);
	    return 1;
	}
	
	if(nkInMap(n,k)) {
		return getValue(n,k);
	}
	
    ArrayList<Integer> list = new ArrayList<Integer>(2);
	list.add(n);
	list.add(k);
	lookupTable.put(list, numCombinationsRecursive(n-1,k)+numCombinationsRecursive(n-1,k-1));
//	System.out.println("n: " + n + " k: " + k + " r: "+ lookupTable.get(list));
	return lookupTable.get(list);
    		
    }


	private static int getValue(int n, int k) {
		ArrayList<Integer> list = new ArrayList<Integer>(2);
		list.add(n);
		list.add(k);
		return lookupTable.get(list);
	}

	private static boolean nkInMap(int n, int k) {
		ArrayList<Integer> list = new ArrayList<Integer>(2);
		list.add(n);
		list.add(k);
		if(lookupTable.containsKey(list)) {
			return true;
		}
		return false;
	}

    static int numCombinations (int n, int k)
    {
    	numCalls ++;

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
