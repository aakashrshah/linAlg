// Instructions: 
// Compile and execute. Then try a different T. And vary n.

public class Sin3 {

    public static void main (String[] argv)
    {
	Function F = new Function ("sin(2*pi*n*t/T)");

	int n = 10;              // Frequency.
	double T = 100;          // Period.

	int numPoints = 100;
	double endT = T;
	double delT = endT / numPoints;

	for (double t=0; t<=endT; t+=delT) {
	    F.add (t, Math.sin(2*Math.PI*n*t / T));
	}

	F.show ();
    }

}
