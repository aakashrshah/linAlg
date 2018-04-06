// Instructions: 
// Compile and execute. 

public class LinCombExample {

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
	double[] alpha = {0, 1.0, 0, -0.16666666666666666};

	// Here, f_1(x) = x,  f_2(x) = 0,  f_3(x) = x^3.
	double g = alpha[1] * x   +   alpha[3] * Math.pow(x,3) ;

	return g;
    }

}
