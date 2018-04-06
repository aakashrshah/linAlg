// In this exploration, we'll repeatedly apply a matrix
// in sequence to a single vector.

import edu.aakash.lintool3.MatrixTool;
import edu.gwu.lintool.*;
import java.awt.*;
import java.util.*;

public class MatrixExplore3 {

    public static void main (String[] argv)
    {
	double[][] A = new double [2][2];
	A[0][0] = 10;  A[0][1] = 10;
	A[1][0] = 10;  A[1][1] = 1.0;

	DrawTool.display ();
	DrawTool.setXYRange (-2,2, -2,2);
	DrawTool.drawMiddleAxes (true);

	// Now start with a vector
	double[] u = {1,1};
	DrawTool.drawVector (u);
	int n = 1000;
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


