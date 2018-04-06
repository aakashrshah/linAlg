
import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class CoordChange {

    public static void main (String[] argv)
    {
	// Rotation by 60
	double theta = Math.PI/3.0;
	double a11 = Math.cos(theta);
	double a12 = -Math.sin(theta);
	double a21 = Math.sin(theta);
	double a22 = Math.cos(theta);
	double[][] A = {
	    {a11, a12},
	    {a21, a22}
	};

	MatrixTool.print(A);

	// A_inv: rotation by -60
	theta = -Math.PI/3.0;
	double b11 = Math.cos(theta);
	double b12 = -Math.sin(theta);
	double b21 = Math.sin(theta);
	double b22 = Math.cos(theta);
	double[][] B = {
	    {b11, b12},
	    {b21, b22}
	};
	MatrixTool.print(B);

	// Check that BA = I
	double[][] I = MatrixTool.matrixMult (B, A);
	MatrixTool.print (I);

	double[] v = {1,3};
	double[] u = MatrixTool.matrixVectorMult (B, v);
	MatrixTool.print (u);
    }

}


