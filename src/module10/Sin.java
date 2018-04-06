// Instructions:
// Compile and execute.
// Then, change the interval to [0,4*pi]

public class Sin {

    public static void main (String[] argv)
    {
	Function F = new Function ("sin");

	int numPoints = 100;
	double endT = 4*Math.PI;
	double delT = endT / numPoints;

	for (double t=0; t<=endT; t+=delT) {
	    F.add (t, Math.sin(t));
	}

	F.show ();
    }

}
