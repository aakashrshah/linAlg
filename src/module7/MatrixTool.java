

import edu.gwu.lintool.LinResult;
import edu.gwu.lintool.LinTool;

public class MatrixTool {

    public static double[] matrixVectorMult (double[][] A, double[] v)
    {
      	if(A == null||v == null) {
			return null;
      	}
		if (A[0].length != v.length){
			return null;
		}
			
		double[] matrixVectorProduct = new double[A.length];
		double sum = 0.0;
		for (int i = 0; i < A.length; i++)
		{
				sum = 0;
				for (int j = 0; j < A[0].length; j++)
				{
					sum = sum + A[i][j] * v[j];
				}
				matrixVectorProduct[i]=sum;
		}
		return matrixVectorProduct;
    }

    public static double[][] matrixMult (double[][] A, double[][] B)
    {
	    	if(A == null || B == null) {
				return null;
			}
	    	if (A[0].length != B.length)
	            return null;
	    		
	    	double[][] product = new double[A.length][B[0].length];
	    	for (int i = 0; i < A.length; i++){
	    	    for (int j = 0; j < B[0].length; j++){
	    	        for (int k = 0; k < A[0].length; k++){
	    	            product[i][j] += A[i][k]* B[k][j];
	    	        }
	    	    }
	    	}
	    	
	    	return product;
    }


    public static void print (double[] x)
    {
		System.out.print ("Vector:");
		for (int i=0; i<x.length; i++) {
		    System.out.printf (" %6.3f", x[i]);
		}
			System.out.println ();
    }

    public static void print (double[][] A)
    {
	System.out.println ("Matrix (" + A.length + "x" + A[0].length + "):");
	for (int i=0; i<A.length; i++) {
	    for (int j=0; j<A[0].length; j++) {
		System.out.printf (" %6.3f", A[i][j]);
	    }
	    System.out.println ();
	}
    }
    
    public static double dotProduct (double[] v, double[] u)
    {
        if(u == null || v == null) {
    		return -1;
	    }
	    
	    if(u.length != v.length) {
	    		return -1;
	    }
	    double sum = 0.0;
	    for (int i = 0; i < u.length; i++)
	        sum = sum + (u[i] * v[i]);
	    
	    return sum;
    }

    public static double norm (double[] u){
	    	if(u == null) {
				return -1;
			}
	    	//	Return the L-2 norm
			double norm = 0.0;
	    for (int i = 0; i < u.length; i++)
	    		norm = norm + Math.pow(u[i], 2);
	    return Math.sqrt(norm);
    }

    public static double[] proj (double[] v, double[] u)
    {
    		if(v.length != u.length) {
    			return null;
    		}
    		double a = MatrixTool.dotProduct(u, v);
    		double d = MatrixTool.dotProduct(u, u);
    		double alpha = a/d;
    		double proj[] = MatrixTool.scalarMult(alpha, u);
    		return proj;
    }

    public static double[] scalarMult (double alpha, double[] v)
    {
		double[] w = new double [v.length];
		for (int i=0; i<w.length; i++) {
		    w[i] = alpha * v[i];
		}
		return w;
    }
    
    public static double[] sub (double[] u, double[] v)
    {
		double[] w = new double [u.length];
		for (int i=0; i<w.length; i++) {
		    w[i] = u[i] - v[i];
		}
		return w;
    }
    
    public static void main(String args[]) {
    	double[][] A = {
    			{-0.2,0.3},
    			{0.4,-0.1}
    	};
    	
    	double[][] B = {
    			{1,3},
    			{4,2}
    	};
    	
    	LinResult lr = LinTool.makeInstance().inverse(A);
    double[][] a = lr.A;
    MatrixTool.print(A);
    MatrixTool.print(B);
    MatrixTool.print(MatrixTool.matrixMult(A, B));
    
    }

	public static double[] add(double[] u, double[] v) {
		double[] w = new double [u.length];
		for (int i=0; i<w.length; i++) {
		    w[i] = u[i] + v[i];
		}
		return w;
	}

	public static double[][] generateIdentity(int row, int col) {

		double[][] identity = new double[row][col];
		for (int i=0; i<row; i++) {
		    for(int j=0;j<col;j++) {
		    		if(i == j) {
		    			identity[i][j] = 1.0;
		    		}else {
		    			identity[i][j] = 0.0;
		    		}
		    }
		}
		return identity;
	}

	public static void swapRows(double[][] identity, int row1, int row2) {
		for(int j=0;j< identity[0].length; j++) {
			double temp = identity[row1][j];
			identity[row1][j] = identity[row2][j];
			identity[row2][j] = temp;
		}
	}

	public static boolean compareMatrix(double[][] A, double[][] B) {
		if(A == null || B == null ) {
			return false;
		}
		
		if(A.length != B.length && A[0].length != B[0].length) {
			return false;
		}
		
		for(int i=0;i<A.length;i++) {
			for(int j=0;j<B[0].length;j++) {
				if(A[i][j] != B[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
}
