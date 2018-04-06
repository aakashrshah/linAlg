// Instructions:
// Insert the affine matrix entries for Binv

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class CoordChange3 {

    public static void main (String[] argv)
    {
	// If A is the matrix that rotates 60 degrees,
	// A_inv rotates -60.
	double theta = -Math.PI/3.0;
	double b11 = Math.cos(theta);
	double b12 = -Math.sin(theta);
	double b21 = Math.sin(theta);
	double b22 = Math.cos(theta);
	double[][] Ainv = {
	    {b11, b12, 0},
	    {b21, b22, 0},
	    {0, 0, 1} 
	};
	MatrixTool.print(Ainv);

	// If B translates by (1,2), then Binv is the inverse.
	double[][] Binv = {
			{b11, b12, 0},
		    {b21, b22, 0},
		    {0, 0, 1}
	};
	MatrixTool.print(Binv);

	// First apply change of coordinates by translation.
	// Then apply rotation.
	double[][] D = MatrixTool.matrixMult (Ainv, Binv);
	MatrixTool.print (D);
	double[][] E = MatrixTool.matrixMult (Binv, Ainv);
	MatrixTool.print (E);

	// Test vector (the point (4,3))
	double[] x = {4,3,1};

	// Get coords in translated frame.
	double[] u = MatrixTool.matrixVectorMult (Binv, x);
	// Get coords in rotated frame from there.
	double[] v = MatrixTool.matrixVectorMult (Ainv, u);
	MatrixTool.print (v);

	// Compare with matrix product that gives the same result.
	v = MatrixTool.matrixVectorMult (D, x);
	MatrixTool.print (v);
    }

}


