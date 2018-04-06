// Instructions:
// Compile and execute

public class FourierExample {

    public static void main (String[] argv)
    {
	MysteryFunction M = new MysteryFunction ();
	int numPoints = 200;
	double delT = 1.0 / numPoints;
	Function F = new Function ("Mystery function");
	for (double t=0; t<=1; t+=delT) {
	    F.add (t, M.getValue(t));
	}
	F.show ();
    }

}
