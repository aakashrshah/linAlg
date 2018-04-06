// Instructions:
// (1) Compile and execute once with rotation and once without.
// (2) Instead of adding the center's coordinates, implement
//     translation via matrix multiplication.

import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class DrawEllipseParametric {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	double x0 = 2, y0 = 2; 	  // Center
	double a = 7;             // Major axis
	double b = 4;             // Minor axis
	double delT = 0.25;       // t-increment for drawing

	// A rotation matrix.
	double theta = Math.PI/3.0;
	double a11 = Math.cos (theta);
	double a12 = -Math.sin (theta);
	double a21 = Math.sin (theta);
	double a22 = Math.cos (theta);
	double[][] A = {
	    {a11, a12},
	    {a21, a22}
	};


	for (double t=0; t<=2*Math.PI+delT; t+=delT) {

	    double[] x = new double[2];

	    // Parametric equations:
	    x[0] = x0 + a * Math.cos(t);
	    x[1] = y0 + b * Math.sin(t);

	    // Without rotation:
	    //double[] z = x;

	    // With rotation:
	    double[] z = MatrixTool.matrixVectorMult (A,x);

	    // Draw the point.
	    DrawTool.drawPoint (z[0],z[1]);

	}

    }

}
