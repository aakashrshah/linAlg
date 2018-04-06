// Instructions:
// Write code for drawing a circle using the parametric representation

import java.util.*;

public class DrawCircleParametric {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	double x0 = 2, y0 = 3; 	  // Center:
	double r = 5;             // Radius
	double delT = 0.5;        // t-increment for drawing

	ArrayList<Double> xValues = new ArrayList<Double> ();
	ArrayList<Double> yValues = new ArrayList<Double> ();

	for (double t=0; t<=2*Math.PI*r; t+=delT) {
	    // INSERT YOUR CODE HERE:
	    double x = x0 + r * Math.cos(t);
	    double y = y0 + r * Math.sin(t);
	    xValues.add (x);
	    yValues.add(y);
	    System.out.println("t " + t + "  cx: " + x + "   y: " + y);

	    DrawTool.drawPoint (x,y);
	}

	// Draw the resulting curve.
	DrawTool.drawCurve (xValues, yValues);
    }

}
