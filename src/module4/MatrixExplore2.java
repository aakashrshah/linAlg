// A simple application of matrix A to u produces v.
// u is given. What is v?

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class MatrixExplore2 {

    public static void main (String[] argv)
    {
	double[][] A = new double [2][2];
	A[0][0] = 5;  A[0][1] = -2;
	A[1][0] = 0;  A[1][1] = 1;

	DrawTool.display ();
	DrawTool.setXYRange (-2,2, -2,2);
	DrawTool.drawMiddleAxes (true);

	// Try u=(1,2). Then try u=(1,0).
	double[] u = {1,2};
	MatrixTool.print (u);
	DrawTool.drawVector (u);     // Draw in black.
	// Apply A to u
	double[] v = MatrixTool.matrixVectorMult (A, u);
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (v);
	MatrixTool.print (v);
    }

}


