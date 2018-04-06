import edu.gwu.lintool.LinToolEmpty;

public class BombearLinTool extends LinToolEmpty {
    	
    		//Vector operations;
        public double[] add (double[] u, double[] v){
        		if(u == null || v == null) {
        			return null;
        		}
        		if (u.length != v.length)
                return null;
        		
            double[] c = new double[u.length];
            
            for (int i = 0; i < c.length; i++)
                c[i] = u[i] + v[i];
            
			return c;
        }
        
        public double norm (double[] v){
        		if(v == null) {
        			return -1;
        		}
//        	Return the L-2 norm
        		double norm = 0.0;
            for (int i = 0; i < v.length; i++)
            		norm = norm + Math.pow(v[i], 2);
            return Math.sqrt(norm);
        }
        
        public double dotProduct (double[] u, double[] v){
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
        
        public double[] scalarProduct (double alpha, double[] v){
        		if(v == null) {
        			return null;
        		}
        		double[] scalarProduct = new double[v.length];
            for (int i = 0; i < v.length; i++)
                scalarProduct[i] =  alpha * v[i];
            return scalarProduct;
        }
        
        public boolean approxEquals (double[] u, double[] v, double errorTolerance){
        		if(u == null || v == null) {
        			return false;
        		}
        		if(u.length != v.length)
				return false;
        		
			double diff = Math.abs(norm(u) - norm(v));
			if(errorTolerance<diff) {
				return true;
			}
			
			return false;
        }
        
        public double cosine (double[] u, double[] v){
			return dotProduct(u,v)/(norm(u)*norm(v));
        }
        
        
        // Matrix operations:
        public double[][] add (double[][] A, double[][] B){
        		if(A == null || B == null) {
        			return null;
        		}
        		
        		if (A.length != B.length || A[0].length != B[0].length)
                return null;
        		
        		double[][] sum = new double[A.length][A[0].length];
        		
        		for (int i = 0; i < A.length; i++)
        			for (int j = 0; j < A[0].length; j++)
                    sum[i][j] =  A[i][j] + B[i][j];
        		
			return sum;
        }
        
        public double[][] scalarProduct (double alpha, double[][] A){
        		if(A == null) {
        			return null;
        		}
        		double[][] scalarProduct = new double[A.length][A[0].length];
        		
        		for (int i = 0; i < A.length; i++)
        			for (int j = 0; j < A[0].length; j++)
        				scalarProduct[i][j] =  A[i][j] * alpha;
        		
			return scalarProduct;

        }
        
        public double[][] mult (double[][] A, double[][] B){
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
        
        public double[] matrixVectorMult (double[][] A, double[] v){
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
        
        public double[] vectorMatrixMult (double[] v, double[][] A){
	        	if(A == null||v == null) {
					return null;
			}
			if (A.length != v.length){
				return null;
			}
				
			double[] vectorMatrixProduct = new double[A[0].length];
			double sum = 0.0;
			for (int i = 0; i < A[0].length; i++)
			{
					sum = 0;
					for (int j = 0; j < A.length; j++)
					{
						sum = sum + A[j][i] * v[j];
					}
					vectorMatrixProduct[i]=sum;
			}
			return vectorMatrixProduct;

        }
        public double[][] vectorLeftMult (double[] u, double[] v){
        		if(u == null || v == null) {
				return null;
			}
			
			double[][] result = new double[u.length][v.length];
			
			for (int i = 0; i < u.length; i++) {
				for (int j = 0; j < v.length; j++)
				{
					result[i][j] = u[i] * v[j];
				}
			}	
			return result;
        }
        
        public double[][] transpose (double[][] A){ 
        		if(A == null) {
        			return null;
        		}
	        	double[][] transpose = new double[A[0].length][A.length];
	    		
	    		for (int i = 0; i < A[0].length; i++)
	    			for (int j = 0; j < A.length; j++)
	    				transpose[i][j] =  A[j][i];
	    		
			return transpose;
        }
        
        public double frobeniusNorm (double[][] A){
	    		if(A == null){
				return -1;
			}
	    		
	        	double sum = 0.0;
	        	
	    		for (int i = 0; i < A.length; i++)
	    			for (int j = 0; j < A[0].length; j++)
	    				sum +=  Math.pow(A[i][j], 2);
	   
			return Math.sqrt(sum);
        }
        
        public boolean approxEquals (double[][] A, double[][] B, double errorTolerance){
			if(A == null || B == null) {
				return false;
			}
			double diff = Math.abs(frobeniusNorm(A) - frobeniusNorm(B));
			if(errorTolerance < diff) {
				return true;
			}
			
			return false;
        }
        
        public double[] getColumnAsVector (double[][] A, int col){
	    		if(A == null){
				return null;
			}
	    		
        		if (col > A[0].length || col <= 0)
                return null;
   
        		double[] colVector = new double[A.length];
        		
        		for (int i = 0; i < A.length; i++)
    				colVector[i] = A[i][col-1];
        	
			return colVector;

        }
        
        public double[] getRowAsVector (double[][] A, int row){
        		if(A == null){
				return null;
			}
        		
        		if (row > A.length || row <= 0)
        			return null;
   
        		double[] rowVector = new double[A[0].length];
        		
        		for (int j = 0; j < A[0].length; j++)
    				rowVector[j] = A[row-1][j];
        	
			return rowVector;

        }
    }