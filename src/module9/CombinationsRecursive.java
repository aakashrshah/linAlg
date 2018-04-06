import java.util.ArrayList;
import java.util.HashMap;

public class CombinationsRecursive {

	static int lookup[];
    static int numCallsRecursive = 0;

	static HashMap<ArrayList<Integer>,Integer> lookupTable;
    public static void main (String[] argv){
		int n = 10;
		Function C = new Function ("n-choose-k vs k");
		for (int k=0; k<=n; k++) {
			lookupTable = new HashMap<>();
		    int r = numCombinationsRecursive (n,k);
		    System.out.println ("n=" + n + " k=" + k + " r=" + r );

//		    System.out.println("---------");
		    C.add (k, r);
		}
		System.out.println ("numCallsRecursive=" + numCallsRecursive );
//		C.show ();
    }

    static int numCombinationsRecursive (int n, int k){
    		numCallsRecursive++;
		if ((n==k) || (n==1) || (k==0)) {
//			System.out.println("n: " + n + " k: " + k + " r: "+ 1);
		    return 1;
		}
		
		if(nkInMap(n,k)) {
			return getValue(n,k);
		}
		
	    ArrayList<Integer> list = new ArrayList<Integer>(2);
		list.add(n);
		list.add(k);
		lookupTable.put(list, numCombinationsRecursive(n-1,k)+numCombinationsRecursive(n-1,k-1));
//		System.out.println("n: " + n + " k: " + k + " r: "+ lookupTable.get(list));
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
}
