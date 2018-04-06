// Instructions:
// Write code to complete the circle

import java.util.*;

public class DrawCircle {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);


	double x0 = 2, y0 = 3; 	  // Center:
	double r = 5;             // Radius
	double delX = 0.5;        // x-increment for drawing

	ArrayList<Double> xValues = new ArrayList<Double> ();
	ArrayList<Double> yValues = new ArrayList<Double> ();

	// Note: we start x on the left.
	for (double x=x0-r; x<=x0+r; x+=delX) {
	    double y = y0 + Math.sqrt (r*r - (x-x0)*(x-x0));
	    xValues.add (x);
	    yValues.add(y);
	    DrawTool.drawPoint (x,y);
	}

	// INSERT YOUR CODE HERE:

	// Draw the resulting curve.
	DrawTool.drawCurve (xValues, yValues);
    }

}
