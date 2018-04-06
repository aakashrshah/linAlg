import java.util.ArrayList;
import java.util.HashMap;

// Instructions:
// Copy over your code from CombinationsRecursive into 
// methods numCombinations() and numCombinationsRecursive()

public class CombinationsComparison {

    static int numCalls = 0;
	static int lookup[];
    static int numCallsRecursive = 0;
	static HashMap<ArrayList<Integer>,Integer> lookupTable;

    public static void main (String[] argv)
    {
	int n = 5;
	lookupTable = new HashMap<>();
	for (int k=0; k<=n; k++) {
	    int p = numCombinations (n,k);
	    int q = numCombinationsRecursive (n,k);
	    System.out.println ("n=" + n + " k=" + k + " p=" + p + " q=" + q);
	}
	System.out.println ("numCalls=" + numCalls + " numCallsRecursive=" + numCallsRecursive);
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
