import edu.aakash.lintool3.MatrixTool;

// Instructions:
// Enter the inverse matrix. Also, copy over your MatrixTool.java
// from Module 4.

public class FourVariableExample {

    public static void main (String[] argv)
    {
	// The coefficient matrix A:
	double[][] A = {
	    {1,2,0,-5},
	    {-1,-1,1,4},
	    {3,7,2,-14},
	    {-2,-2,4,13}
	};

	// Right hand side:
	double[] b = {-5,6,-9,23};

	// Solution found in RREF:
	double[] x = {2,-1,3,1};

	// Inverse of A:
	double[][] Ainv = {
	    {-17 , -4, 4, -1},
	    { 29, 2, -7, 3},
	    {-20, -1,  5, -2},
	    {8 ,  0, -2, 1}
	};

	// b2 should be equal to b.
	double[] b2 = MatrixTool.matrixVectorMult (A, x);
	MatrixTool.print (b2);

	// This should give us the identity matrix.
	double[][] I = MatrixTool.matrixMult (Ainv, A);
	MatrixTool.print (I);
    }

}
