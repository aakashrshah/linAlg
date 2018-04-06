// Instructions: 
// Try values of n=3, n=5, n=7, n=13 in the function g().

import java.util.*;

public class LinCombExample2 {

    public static void main (String[] argv)
    {
	Function G = new Function ("g(x) vs x");
	for (double x=0; x<=6.283; x+=0.1) {
	    G.add (x, g(x));
	}

	// Draw the curve.
	G.show ();
    }

    static double g (double x)
    {
	// Array of coefficients (scalars in the linear combination).
	double[] alpha = {0, 1.0, 0, -0.16666666666666666, 0, 0.008333333333333333, 0, -1.9841269841269839E-4, 0, 2.7557319223985884E-6, 0, -2.5052108385441714E-8, 0, 1.605904383682161E-10};

	// How many terms to include: Start with n=3, then change to n=5,
	// n=7, then n=13.
	int n = 3;

	double g = 0;
	for (int k=0; k<=n; k++) {
	    g = g + alpha[k] * Math.pow (x, k);
	}

	return g;
    }

}
