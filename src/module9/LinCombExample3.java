// Instructions: 
// Try values of n=3, n=5, n=7, n=13 in the function g().
// Do the same after changing the intervalEnd to Math.PI.

import java.util.*;

public class LinCombExample3 {

    public static void main (String[] argv)
    {
	double totalError = 0;
	double deltaX = 0.1;
	double intervalEnd = 2*Math.PI;
	for (double x=0; x<=intervalEnd; x+=deltaX) {
	    double errorAtX = Math.abs (g(x) - Math.sin(x));
	    double  errorCurrent = errorAtX;
	    System.out.println(errorCurrent);
	    totalError += errorCurrent;
	}

	System.out.println ("Total error: " + totalError);
    }

    static double g (double x)
    {
	// Array of coefficients (scalars in the linear combination).
	double[] alpha = {0, 1.0, 0, -0.16666666666666666, 0, 0.008333333333333333, 0, -1.9841269841269839E-4, 0, 2.7557319223985884E-6, 0, -2.5052108385441714E-8, 0, 1.605904383682161E-10};

	// How many terms to include: Start with n=3, then change to n=5,
	// n=7, then n=13.
	int n = 13;

	double g = 0;
	for (int k=0; k<=n; k++) {
	    System.out.println ("alpha: " + alpha[k]);
	    g = g + alpha[k] * Math.pow (x, k);
	}

	return g;
    }

}
