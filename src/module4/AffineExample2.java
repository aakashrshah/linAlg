// Instructions:
// Compile and execute once just to see the transformed vector.
// Compile and execute after un-commenting the "red arrow" code.

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class AffineExample2 {

    public static void main (String[] argv)
    {
	// Build the entries of the 2D rotation matrix 
	double theta = Math.PI/3.0;
	double a11 = Math.cos (theta);
	double a12 = -Math.sin (theta);
	double a21 = Math.sin (theta);
	double a22 = Math.cos (theta);

	// Insert into 3D affine extension:
	double[][] A = {
	    {a11, a12, 0},
	    {a21, a22, 0},
	    {0,   0,   1}
	};

	// Translation by (3,4)
	double[][] B = {
            {1, 0, 3},
		    {0, 1, 4},
		    {0, 0, 1}
	};

	// Combine rotation and translation with the affine versions.
	double[][] C = MatrixTool.matrixMult (B, A);

	// Examine the matrix C in comparison to A and B.
	MatrixTool.print (A);
	MatrixTool.print (B);
	MatrixTool.print (C);

	// Apply to the affine extension of (5,1):
	double[] u = {5,1,1};
	double[] v = MatrixTool.matrixVectorMult (C, u);

	// Here, (v[0], v[1]) is the 2D vector.

	// Draw, after extracting the 2D components:
	DrawTool.display ();
	DrawTool.setXYRange (0,10, 0,10);
	DrawTool.drawMiddleAxes (true);
	DrawTool.drawVector (u[0], u[1]);
//	DrawTool.setArrowColor ("blue");
//	DrawTool.drawVector (v[0], v[1]);
//
////	 Draw an arrow from the translated origin.
//	 DrawTool.setArrowColor ("red");
//	 DrawTool.drawArrow (3,4, v[0], v[1]);
    }

}
