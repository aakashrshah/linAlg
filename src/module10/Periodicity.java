// Instructions:
// (1) Compile and execute.
// (2) Un-comment the commented-out section and compile/execute

import java.util.*;

public class Periodicity {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (0,10, -2,2);

	int N = 10;
	int n = 3;

	// First, we'll draw sin(2*pi*n*t/N)
	ArrayList<Double> xValues = new ArrayList<Double> ();
	ArrayList<Double> yValues = new ArrayList<Double> ();

	for (double t=0; t<=10; t+=0.01) {
	    double y = Math.sin (2*Math.PI*n*t/N);
	    xValues.add (t);
	    yValues.add (y);
	}
	DrawTool.drawCurve(xValues, yValues);


	// (2) Un-comment this section for the next exercise.

	/*
	// Now, we'll draw a second curve at a multiple: n+p*N
	int p = 2;

	xValues = new ArrayList<Double> ();
	yValues = new ArrayList<Double> ();
	for (double t=0; t<=10; t+=0.01) {
	    double y = Math.sin (2*Math.PI*(n+p*N)*t/N);
	    xValues.add (t);
	    yValues.add (y);
	}
	DrawTool.drawCurve (xValues, yValues);
	*/

	// Lastly, we'll draw the points f(k), for k=0,1,...,N
	for (int k=0; k<=N; k++) {
	    double fk = Math.sin (2*Math.PI*n*k/N);
	    DrawTool.drawPoint (k,fk);
	}

    }

}
