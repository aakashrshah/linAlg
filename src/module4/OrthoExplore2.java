
import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class OrthoExplore2 {

    public static void main (String[] argv)
    {
	// Random seed for generation.
	UniformRandom.set_seed (System.currentTimeMillis());

	// Start with a random unit length vector: a random angle.
	double theta = UniformRandom.uniform (0, 2.0*Math.PI);
	double[] x = new double [2];
	x[0] = Math.cos(theta);  x[1] = Math.sin(theta);

	// Find perpendicular unit length vector by adding 90-deg
	double[] y = new double [2];
	y[0] = Math.cos(theta+Math.PI/2);  y[1] = Math.sin(theta+Math.PI/2);

	// Test.
	double xDoty = MatrixTool.dotProduct (x,y);
	System.out.println ("x dot y = " + xDoty);

	// Make the matrix with x and y as columns.
	double[][] A = new double [2][2];
	A[0][0] = x[0];  A[1][0] = x[1];
	A[0][1] = y[0];  A[1][1] = y[1];

	double delTheta = 0.1;
	double[] u = new double [2];
	Function F = new Function ("alpha vs theta");
	for (theta=0; theta<=2*Math.PI; theta+=delTheta) {
	    // Generate next vector along unit circle.
	    u[0] = Math.cos(theta);
	    u[1] = Math.sin(theta);
	    // Apply A to u.
	    double[] z = MatrixTool.matrixVectorMult (A, u);
	    double alpha = Math.atan2 (z[1], z[0]);
	    // Get z's angle.
	    alpha = fixAngle (alpha);
	    // Add to data set.
	    F.add (theta, alpha);
	}

	// Plot data set.
	F.show ();
    }

    static double fixAngle (double a)
    {
	if (a < 0) {
	    a = a + 2*Math.PI;
	}
	return a;
    }


}


