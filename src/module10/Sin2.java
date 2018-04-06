// Instructions
// Compile and execute with n=1. Then try n=2, n=3.

public class Sin2 {

    public static void main (String[] argv)
    {
	Function F = new Function ("sin(2*pi*n*t)");

	// Try n=2, n=3.
	int n = 3;

	int numPoints = 100;
	double endT = 1;
	double delT = endT / numPoints;

	for (double t=0; t<=endT; t+=delT) {
	    F.add (t, Math.sin(2*Math.PI*n*t));
	}

	F.show ();
    }

}
