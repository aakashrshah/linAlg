// Instructions:
// Add code to draw the four points: (a0,b0), (a1,b1), (a2,b2), (a3,b3)

import java.util.*;

public class DrawBernsteinParametric2 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	// Four coefficients for x(t)
	double[] a = {1, 2, 3, 6};
	// Four coefficients for y(t)
	double[] b = {1, 5, 6, 2};

	// This is the "n" in b_{n,k}(t)
	int n = 3;

	double delT = 0.1;        // t-increment for drawing

	// First, we'll draw the line using DrawTool.
	ArrayList<Double> xValues = new ArrayList<Double> ();
	ArrayList<Double> yValues = new ArrayList<Double> ();

	for (double t=0; t<=1; t+=delT) {

	    // x(t) = a0*b(n,0,t) + a1*b(n,1,t) + a2*b(n,2,t) + a3*b(n,3,t)
	    // y(t) is similar, but with b0, b1, b2, b3.
	    double x = 0,  y = 0;
	    for (int k=0; k<=n; k++) {
		x += a[k] * bernstein(n,k,t);
		y += b[k] * bernstein(n,k,t);
	    }

	    xValues.add (x);
	    yValues.add(y);
	}

	// Draw the line.
	DrawTool.setLineColor ("blue");
	DrawTool.drawCurve(xValues, yValues);

	DrawTool.setPointColor ("red");

	// INSERT YOUR CODE HERE to draw the points (a_i,b_i)
	// using DrawTool.drawPoint (a[i],b[i])

    }

    static double bernstein (int n, int k, double t)
    {
		return t;
	// INSERT YOUR CODE HERE:

    }

}
