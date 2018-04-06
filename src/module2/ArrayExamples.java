public class ArrayExamples {

    public static void main (String[] argv)
    {
	//oneD ();
	//twoD ();
	testDoublePalindrome ();
    }


    static void testDoublePalindrome ()
    {
	// A double palindrome is defined as follows:
	// (1) The string has even length.
	// (2) The string is a palindrome.
	// (3) Each half of the string is also a palindrome.

	// These are double palindromes:
	testDP ("3333", true);
	testDP ("24422442", true);
	testDP ("123321123321", true);
	testDP ("1232112321", true);
	System.out.println("\n");

	// These are not.
	testDP ("123242321", false);  // Not even.
	testDP ("3344", false);       // Overall string is not a palindrome.
	testDP ("121212", false);     // Same as above.
	testDP ("12344321", false);   // Each half is not a palindrome.
	testDP ("12211212", false);   // Second half is not a palindrome.
    }

    static void testDP (String str, boolean isDP)
    {
	char[] digits = str.toCharArray ();
	boolean result = Palindrome.isDoublePalindrome (digits);
	if (result == isDP) {
	    System.out.println (">> Passed test: " + str);
	}
	else {
	    System.out.println (">> Failed test: " + str);
	}
    }

    static void oneD ()
    {
	int n = 5;                      // Array size.
	int[] A = new int [n];          // Create the space.
	for (int k=0; k<n; k++) {
	    A[k] = 2*k + 1;             // Populate the array.
	}

	// Compute successive partial sums
	int[] B = new int [n];
	B[0] = A[0];
	for (int k=1; k<n; k++) {
	    B[k] = B[k-1] + A[k];
	}

	// Print
	System.out.print ("B: " );
	for (int k=0; k<n; k++) {
	    System.out.print ("  " + B[k]);
	}
	System.out.println ();
    }

    static void twoD ()
    {
	double[][] A = new double [4][6];
	A[1][4] = 0.5;
    }

}
