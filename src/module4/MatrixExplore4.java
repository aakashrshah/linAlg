// In this exploration, we'll repeatedly apply a random matrix
// in sequence to a single vector.

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class MatrixExplore4 {

    public static void main (String[] argv)
    {
	// Random seed for generation.
	UniformRandom.set_seed (System.currentTimeMillis());

	// Start with a random unit length vector: a random angle.
	double theta = UniformRandom.uniform (0, 2.0*Math.PI);
	double[] x = new double [2];
	x[0] = Math.cos(theta);  x[1] = Math.sin(theta);

	// The second column will be also be generated randomly.
	theta = UniformRandom.uniform (0, 2.0*Math.PI);
	double[] y = new double [2];
	y[0] = Math.cos (theta);   y[1] = Math.sin (theta);

	// Make the matrix with x and y as columns.
	double[][] A = new double [2][2];
	A[0][0] = x[0];  A[1][0] = x[1];
	A[0][1] = y[0];  A[1][1] = y[1];

	DrawTool.display ();
	DrawTool.setXYRange (-1,1, -1,1);
	DrawTool.drawMiddleAxes (true);

	// Now start with a vector
	double[] u = {1,1};
	DrawTool.drawVector (u);
	int n = 100;
	for (int i=0; i<n; i++) {
	    // Apply A to u.
	    u = MatrixTool.matrixVectorMult (A, u);
	    // We'll normalize the length so that we stay 
	    // within the bounds of the drawing area. This is fine
	    // because we just want the direction.
	    u = normalize (u);
	    DrawTool.drawVector (u);
	}

    }

    static double[] normalize (double[] x)
    {
	double normX = MatrixTool.norm (x);
	double[] y = new double [x.length];
	for (int i=0; i<x.length; i++) {
	    y[i] = (1.0 / normX) * x[i];
	}
	return y;
    }

}


