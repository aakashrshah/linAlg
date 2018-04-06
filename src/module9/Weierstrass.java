// Instructions:
// Try the original Weierstrass function first over [-2,2]
// and observe the shape. Then try [0.9,1.8] and observe
// the same fractal like behavior.

public class Weierstrass {

    public static void main (String[] argv)
    {
	original ();
	//variation ();
    }

    static void original ()
    {
	Function W = new Function ("Weierstrass");

	double a=0.5, b=3;
	int n=100;

	// Use alternate interval and delta
//	double xLeft=-2, xRight=2, deltaX=0.01;
	double xLeft=0.9, xRight=1.8, deltaX=0.001;

	for (double x=xLeft; x<=xRight; x+=deltaX) {

	    double sum = 0;
	    for (int i=0; i<=n; i++) {
		double term = Math.pow(a,i) * Math.cos (Math.pow(b,i) *x);
		sum += term;
	    }
	    W.add (x, sum);
	}

	W.show ();
    }

    static void variation ()
    {
	Function W = new Function ("Weierstrass");
	double a=0.5, b=2;
	int n=100;
	double xLeft=0, xRight=2*Math.PI, deltaX=0.1;
	for (double x=xLeft; x<=xRight; x+=deltaX) {
	    double sum = 0;
	    for (int i=0; i<=n; i++) {
		double term = Math.pow(0.5,i) * Math.sin (Math.pow(b,i) *x);
		sum += term;
	    }
	    W.add (x, sum);
	}
	W.show ();
    }

}
