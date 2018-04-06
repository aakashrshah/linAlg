
import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class AffineExample {

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

	// Apply to the affine extension of (4,3):
	double[] u = {4,3,1};
	double[] v = MatrixTool.matrixVectorMult (A, u);

	// Here, (v[0], v[1]) is the 2D vector.
	MatrixTool.print (v);

	// Draw, after extracting the 2D components:
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);
	DrawTool.drawVector (u[0], u[1]);
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (v[0], v[1]);
    }

}
