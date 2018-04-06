// This program generates a random orthonormal matrix and
// applies it to a few vectors. Each time the program is run
// new random matrix is generated.
//
// What to observe: the result vectors are rotated with their
// length unchanged.

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class OrthoExplore {

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
	A[0][0] = x[0];  A[0][1] = y[0];
	A[1][0] = x[1];  A[1][1] = y[1];

	// We'll now draw some sample unit vectors and apply.

	DrawTool.display ();
	DrawTool.setXYRange (-1,1, -1,1);
	DrawTool.drawMiddleAxes (true);

	theta = Math.PI/3;
	double[] u  = new double [2];
	u[0] = Math.cos(theta);  u[1] = Math.sin(theta);
	double[] v = MatrixTool.matrixVectorMult (A, u);
	DrawTool.drawVector (u);
	DrawTool.drawVector (v);

	DrawTool.setArrowColor ("blue");
	theta = Math.PI/4;
	u[0] = Math.cos(theta);  u[1] = Math.sin(theta);
	v = MatrixTool.matrixVectorMult (A, u);
	DrawTool.drawVector (u);
	DrawTool.drawVector (v);

	DrawTool.setArrowColor ("red");
	theta = Math.PI/6;
	u[0] = Math.cos(theta);  u[1] = Math.sin(theta);
	v = MatrixTool.matrixVectorMult (A, u);
	DrawTool.drawVector (u);
	DrawTool.drawVector (v);
    }


}


