package edu.aakash.lintool3;

import edu.gwu.lintool.ComplexNumber;
import edu.gwu.lintool.LinResult;
import edu.gwu.lintool.LinToolEmpty;

public class BombearLinTool  extends LinToolEmpty {

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
    		// Return the L-2 norm
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
    
    public  double norm1 (double[] u)
    {
	if(u!=null)
			{
			double sqSum=0.0;
			for(int i=0;i<u.length;i++)
			{
				sqSum += Math.pow(u[i],2);
			}
			return Math.sqrt(sqSum);
			}
			else
			{
				return -1;
			}
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
    		
    		if (col > A[0].length || col < 0)
            return null;

    		double[] colVector = new double[A.length];
    		
    		for (int i = 0; i < A.length; i++)
				colVector[i] = A[i][col];
    	
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
    
    
	//Complex operations
    public ComplexNumber[] add(ComplexNumber[] u, ComplexNumber[] v) {
        if (u == null || v == null) {
            return null;
        } else if (u.length != v.length) {
            return null;
        } else {
            ComplexNumber[] c = new ComplexNumber[u.length];
            for (int i = 0; i < u.length; i++) {
                ComplexNumber sum = ComplexNumber.makeComplexNumber(0, 0);
                sum.re = u[i].re + v[i].re;
                sum.im = u[i].im + v[i].im;
                c[i] = sum;
            }
            return c;
        }
    }

    public ComplexNumber[] scalarProduct(ComplexNumber alpha, ComplexNumber[] v) {
        if (v == null) {

            System.out.println("Null Vector!");
            return null;
        } else if (alpha == null) {

            //System.out.println("Alpha is 0!");
            return null;
        } else {
            ComplexNumber[] scalarProduct = new ComplexNumber[v.length];
            for (int i = 0; i < v.length; i++) {
                ComplexNumber prod = ComplexNumber.makeComplexNumber(0, 0);
                prod.re = (alpha.re * v[i].re) - (alpha.im * v[i].im);
                prod.im = (alpha.re * v[i].im) + (alpha.im * v[i].re);
                scalarProduct[i] = prod;
            }

            return scalarProduct;
        }
    }

    public ComplexNumber dotProduct(ComplexNumber[] u, ComplexNumber[] v) {
        if (v == null || u == null) {
            return null;
        } else {
            ComplexNumber prod = ComplexNumber.makeComplexNumber(0, 0);
            ComplexNumber sum = ComplexNumber.makeComplexNumber(0, 0);
            for (int i = 0; i < v.length; i++) {
                v[i].im = -1 * v[i].im;
                prod.re = (u[i].re * v[i].re) - (u[i].im * v[i].im);
                prod.im = (u[i].re * v[i].im) + (u[i].im * v[i].re);
                sum.re = sum.re + prod.re;
                sum.im = sum.im + prod.im;
            }
            return sum;
        }
    }

    public double norm(ComplexNumber[] v) {

        if (v == null) {
            System.out.println("Null Vector!");
            return -1;
        } else {
            double len;
            double sumOfSquares = 0;
            for (int i = 0; i < v.length; i++) {

                sumOfSquares = sumOfSquares + ((v[i].re * v[i].re) + (v[i].im * v[i].im));

            }
            len = Math.sqrt(sumOfSquares);
            return len;
        }
    }
    
    
    
    
    //REF and RREF Operations
    public LinResult computeREF(double[][] A, double[] b) {
	    LinResult result= new LinResult();
	    result=  blahREF(A,b);
	    return result;
	}
	
	public LinResult computeRREF(double[][] Am, double[] b) {
	    LinResult result = new LinResult();
	    result =  blahRREF(Am,b);
	    return result;
	}
	
	public LinResult solveFromREF(double[][] A, double[] b) {
	   return this.solveForEchleon(A,b,true);
	}
	
	public LinResult solveFromRREF(double[][] A, double[] b) {
	    return this.solveForEchleon(A,b,false);
	}
	
	public LinResult inverse(double[][] A) {
	    double[] b = new double[A.length];
	    return this.computeRREF(A, b);
	}

	

//	Let's now refine our RREF-based algorithm to handle the special cases:
//
//	Algorithm: computeRREF (A, b)
//	Input:: matrix A, vector b
//
//	  1.   A+ = computeAugmentedMatrix (A, b)
//	  2.   A-1  = identity matrix of size n
//	  3.   currentRow = 1, currentCol = 1
//
//	       // Now search for the next column that has a non-zero element
//	       // below the currentRow
//	  4.   (r,c) = findNextPivot (currentRow, currentCol)
//
//	  5.   while r > 0 and c > 0
//
//	          // Swap into currentRow if needed
//	  6.      if r > currentRow
//	  7.         swap (r, currentRow)
//	  8.      endif
//
//	  9.      currentCol = c
//
//	          // The next pivot is now at (currentRow, currentCol)
//	  10.     isPivotColumn[currentCol] = true
//	  11.     numPivots = numPivots + 1
//
//	          // Make the coefficient of the pivot equal to 1
//	  12.     α = A+[currentRow][currentCol]
//	  13.     for j=1 to n
//	  14.        A+[currentRow][j] = A+[currentRow][j] / α
//	  15.        A-1[currentRow][j] = A-1[currentRow][j] / α
//	  16.     endfor
//	 
//	          // Zero out the coefficients below the pivot    
//	  17.     for R=currentRow+1 to n
//	  18.         apply the appropriate row-operation to row R of A+
//	  19.         do the same for A-1
//	  20.     endfor
//
//	          // All pivots will be in successive rows from the top, but
//	          // need not occur in successive columns.
//	  21.     currentRow = currentRow + 1    
//	  22.     currentCol = currentCol + 1
//	  23.     (r,c) = findNextPivot (currentRow, currentCol)
//
//	  24.  endwhile
//	  
//	       // This produces the REF in A+, but nothing useful (yet) in A-1
//	       // Continue to the RREF ...
//
//	  25.  for k=1 to numPivots
//	          // Zero out the coefficients above the pivot
//	  26.     (rp,cp) = findPivot (k)
//	  27.     for r=rp-1 to 1
//	  28.         apply the appropriate row-operation to row r of A+
//	  29.         do the same for A-1
//	  30.     endfor
//	  31.  endfor
//	  
//	       // Now analyze RREF
//	  32.  if existsContradictionRow ()
//	  33.     return no solution
//	  34.  else if existsAllZeroRows ()
//	  35.     // ... handle multiple solutions ...
//	  36.  else
//	  37.     x = last column of A+
//	  38.     return x, A-1
//	  40.  endif
//	  
//	L.ref = computeREF(L.A , L.b); //the REF
//	L.rref, the RREF
//	L.isPivotColumn: Here, L.isPivotColumn[k] is true of column k is a pivot column.
//	L.pivotRow: Here, L.pivotRow[k]=r if the r is the row corresponding to the k-th pivot. Use -1 otherwise.
//	L.rank is the rank of the matrix (or of the augmented matrix). That is, the number of pivots.
//	L.solutionExists should be set to true if either there is a unique solution or if multiple solutions exist.
//	L.isUniqueSolution should be set to true if there is a unique solution.
//	L.x (an array) should contain a solution, if one exists. It should be set to null otherwise. If there are multiple solutions, any solution will suffice.
//	L.Ainv should contain the inverse, if it exists (for the case that a unique solution exists).
    
	
	
	
	//Helper functions
	public LinResult solveForEchleon(double[][] A, double[] b, boolean flag) {

	    LinResult result;
	    if (flag) {
	        result = this.computeREF(A, b);
	    } else {
	        result = this.computeRREF(A, b);
	    }

	    for (int i = A.length - 1; i >= result.rank; --i) {
	        if (result.ref[i][A[0].length] != 0.0D) {
	            boolean checkZero = true;

	            for (int j = 0; j < A[0].length; ++j) {
	                if (Math.abs(result.ref[i][j]) > Math.pow(2.0D, -20.0D)) {
	                    checkZero = false;
	                }
	            }

	            if (checkZero) {
	                result.solutionExists = false;
	                result.x = null;
	                return result;
	            }
	        }
	    }

	    if (A.length >= A[0].length && result.rank == A[0].length) {
	        result.solutionExists = true;
	        result.isUniqueSolution = true;
	        result.x = new double[A[0].length];
	        if (!flag) {
	            for (int i = 0; i < A[0].length; ++i) {
	                result.x[i] = result.rref[i][A[0].length];
	            }

	            return result;
	        } else {
	            result.x[result.rank - 1] = result.ref[result.rank - 1][A[0].length];

	            for (int i = result.rank - 2; i >= 0; --i) {
	                double value = result.ref[i][A[0].length];

	                for (int k = i + 1; k < A[0].length; ++k) {
	                    value -= result.ref[i][k] * result.x[k];
	                }

	                result.x[i] = value;
	            }

	            return result;
	        }
	    } else {
	        result.solutionExists = true;
	        result.isUniqueSolution = false;
	        result.x = null;
	        return result;
	    }
	}

	public LinResult blahREF(double[][] A, double[] b) {
	    if (A != null && b != null && A.length == b.length) {

	        double[][] ref = new double[A.length][1 + A[0].length];


	        for (int i = 0; i < ref.length; ++i) {

	            System.arraycopy(A[i], 0, ref[i], 0, ref[0].length - 1);

	            ref[i][ref[0].length - 1] = b[i];
	        }

	        int row = 0;
	        int col = 0;
	        LinResult result;

	        (result = new LinResult()).A = A;
	        result.b = b;
	        result.ref = ref;
	        result.isPivotColumn = new boolean[A[0].length];
	        result.pivotRow = new int[A[0].length];

	        for (int k = 0; k < result.pivotRow.length; ++k) {
	            result.pivotRow[k] = -1;
	        }

	        double[][] Ainverse = new double[A.length][1 + A[0].length];

	        int c;
	        for (int k = 0; k < A.length; ++k) {
	            if (k < A[0].length) {
	                Ainverse[k][k] = 1.0D;
	            }
	        }

	        while (row < ref.length && col < ref[0].length) {
	            int r = row;
	            double[][] ref1 = ref;
	            c = col;

	            int tester;

	            justAnotherLabel:
	                while (true) {
	                    if (c >= ref[0].length - 1) {
	                        tester = -1;
	                        break;
	                    }

	                    for (int i = r; i < ref.length; ++i) {
	                        if (Math.abs(ref1[i][c]) > Math.pow(2.0D, -10.0D)) {
	                            tester = c;
	                            break justAnotherLabel;
	                        }
	                    }

	                    ++c;
	                }

	            c = tester;
	            if (tester < 0) {
	                break;
	            }

	            result.isPivotColumn[c] = true;
	            result.pivotRow[c] = row;


	            int c1 = c;
	            int r1 = row;

	            ref1 = ref;

	            while (true) {
	                if (r1 >= ref.length) {
	                    tester = -1;
	                    break;
	                }

	                if (ref1[r1][c1] != 0.0D) {
	                    tester = r1;
	                    break;
	                }

	                ++r1;
	            }

	            int tester1 = tester;

	            if (row != tester1) {

	                for (int i = 0; i < ref[0].length; ++i) {
	                    double value = ref[row][i];
	                    ref[row][i] = ref[tester1][i];
	                    ref[tester1][i] = value;
	                }

	            }

	            if (row != tester1) {

	                for (int i = 0; i < Ainverse[0].length; ++i) {
	                    double inverseValue = Ainverse[row][i];
	                    Ainverse[row][i] = Ainverse[tester1][i];
	                    Ainverse[tester1][i] = inverseValue;
	                }

	            }
	            double value1 = ref[row][c];

	            for (r = c; r < ref[0].length; ++r) {
	                ref[row][r] /= value1;
	            }

	            for (r = 0; r < Ainverse[0].length; ++r) {
	                Ainverse[row][r] /= value1;
	            }

	            for (r = row + 1; r < ref.length; ++r) {
	                double value2 = ref[r][c];

	                for (int i = 0; i < ref[0].length; ++i) {
	                    ref[r][i] -= value2 * ref[row][i];
	                }

	                for (int i = 0; i < Ainverse[0].length; ++i) {
	                    Ainverse[r][i] -= value2 * Ainverse[row][i];
	                }
	            }

	            row = row + 1;
	            col = col + 1;
	        }

	        c = 0;

	        for (int i = 0; i < result.isPivotColumn.length; ++i) {
	            if (result.isPivotColumn[i]) {
	                ++c;
	            }
	        }

	        result.rank = c;

	        if (A.length == A[0].length && A.length == result.rank) {
	            result.Ainv = new double[A.length][A[0].length];

	            for (int i = 0; i < A.length; ++i) {
	                for (int j = 0; j < A[0].length; ++j) {
	                    result.Ainv[i][j] = Ainverse[i][j];
	                }
	            }
	        } else {
	            result.Ainv = null;
	        }

	        return result;
	    } else {
	        return null;
	    }
	}

	public LinResult blahRREF(double[][] A, double[] b) {

	    LinResult result;

	    if ((result = this.computeREF(A, b)) == null) {
	        return null;
	    } else {
	        result.rref = new double[result.ref.length][result.ref[0].length];

	        int pivot;
	        for (int i = 0; i < result.ref.length; ++i) {
	            System.arraycopy(result.ref[i], 0, result.rref[i], 0, result.ref[0].length);
	        }

	        for (int i = 0; i < result.rref[0].length - 1; ++i) {
	            if (result.isPivotColumn[i]) {
	                pivot = result.pivotRow[i];

	                for (int j = 0; j < pivot; ++j) {
	                    double value = result.rref[j][i];

	                    for (int k = 0; k < result.rref[0].length; ++k) {
	                        result.rref[j][k] -= value * result.rref[pivot][k];
	                    }


	                    if (result.Ainv != null) {
	                        for (int k = 0; k < result.Ainv[0].length; ++k) {
	                            result.Ainv[j][k] -= value * result.Ainv[pivot][k];
	                        }
	                    }
	                }
	            }
	        }
	        return result;
	    }
	}
	
	// --------------------------------------------------------------------------------
    // Orthogonality:
    public boolean areColumnsLI (double[][] A) {
    		LinResult result = new LinResult();
    		double[] b = new double[A.length];
		result = solveFromRREF(A, b);

		if(MatrixTool.compareMatrix(result.rref , MatrixTool.generateIdentity(A.length, A[0].length))){
			return true;
		}
		
		return false;
    }
    
	public LinResult gramSchmidt (double[][] A) {
		LinResult result = new LinResult();
		result.V = null;
		result.Q = null;
		if(A == null) {
			return result;
		}

		result.V = new double[A.length][A[0].length];
		result.Q = new double[A.length][A[0].length];
			
		double[] a0 = getColumnAsVector(A, 0);
		double[] v0 = a0;
		addToV(result.V,v0,0);

		double denom = norm1(a0);
		double[] qPrevVector = new double[A.length];
 		for(int eachRow = 0; eachRow < a0.length ; eachRow++) {
			qPrevVector[eachRow] = v0[eachRow] / denom;
		}
		addToQ(result.Q,qPrevVector,0);
		double alpha = MatrixTool.dotProduct(v0, qPrevVector);
		double[] scalarMult = MatrixTool.scalarMult(alpha, qPrevVector);
			
 		double sum[] = null;
 		for(int eachColumn = 1; eachColumn < A[0].length ; eachColumn++) {
 			double[] aVector = getColumnAsVector(A, eachColumn);
 			
 			sum = new double[A.length];
 			for(int i=0; i <= eachColumn-1 ; i++) {
 				alpha = MatrixTool.dotProduct(aVector, getColumnAsVector(result.Q, i));
 	 			scalarMult = MatrixTool.scalarMult(alpha, getColumnAsVector(result.Q, i));
 	 			sum = MatrixTool.add(sum, scalarMult);
 			}
 			
 			double[] v1 = MatrixTool.sub(aVector,sum);
 			addToV(result.V,v1,eachColumn);
 			
 			denom = norm1(v1);
 			qPrevVector = new double[A.length];
 	 		for(int eachRow = 0; eachRow < v1.length ; eachRow++) {
 				qPrevVector[eachRow] = v1[eachRow] / denom;
 			}
 			addToQ(result.Q,qPrevVector,eachColumn);

 		}
		return result;
	}
	
    private void addToQ(double[][] q, double[] qPrevVector, int eachColumn) {
		if(q.length == qPrevVector.length) {
			for(int i=0;i<q.length;i++) {
				q[i][eachColumn] = qPrevVector[i];
			}
		}
	}

	private void addToV(double[][] v, double[] v1, int eachColumn) {
		if(v.length == v1.length) {
			for(int i=0;i< v1.length;i++) {
				v[i][eachColumn] = v1[i];
			}
		}
	}


	public LinResult computeQR (double[][] A) {
		LinResult result = new LinResult();
		result.V = null;
		result.Q = null;
		if(A == null) {
			return result;
		}
 		MatrixTool.print(A);

		result.V = new double[A.length][A[0].length];
		result.Q = new double[A.length][A[0].length];
		result.R = new double[A.length][A[0].length];
		double[] sum = null;
		double alpha = 0.0;
		double[] scalarMult = null;
		
		double[] a0 = getColumnAsVector(A, 0);
		double[] v0 = a0;
		addToV(result.V,v0,0);
		double denom = norm1(a0);
		
		double[] qPrevVector = new double[A.length];
 		for(int eachRow = 0; eachRow < a0.length ; eachRow++) {
			qPrevVector[eachRow] = v0[eachRow] / denom;
		}
		addToQ(result.Q,qPrevVector,0);
		
		for(int eachColumn=0; eachColumn<A.length; eachColumn++) {
			double[] aVector = getColumnAsVector(A, eachColumn);
			denom = 	norm1(aVector);
			
			sum = new double[A.length];
 			for(int eachRow=0; eachRow <= eachColumn-1 ; eachRow++) {
 				alpha = MatrixTool.dotProduct(aVector, getColumnAsVector(result.Q, eachRow));
 	 			scalarMult = MatrixTool.scalarMult(alpha, getColumnAsVector(result.Q, eachRow));
 	 			sum = MatrixTool.add(sum, scalarMult);
 			}
 			
 			double[] v1 = MatrixTool.sub(aVector,sum);
 			addToV(result.V,v1,eachColumn);
 			denom = 	norm1(v1);
 			
			for(int eachRow=0; eachRow < v1.length; eachRow++) {
				result.Q[eachRow][eachColumn] = v1[eachRow] / denom;
			}
			
			MatrixTool.print(result.Q);
			for(int eachRow=0; eachRow <= eachColumn; eachRow++) {
				result.R[eachRow	][eachColumn] = MatrixTool.dotProduct(aVector, getColumnAsVector(result.Q, eachRow));
			}
			MatrixTool.print(result.R);

		}
		MatrixTool.print(result.Q);
		MatrixTool.print(result.R);

		return result;
    }

	// Complex matrix operations:
	public ComplexNumber[][] add (ComplexNumber[][] A, ComplexNumber[][] B){
		if(A==null || B==null)
		{
			return null;
		}
		else
		{
			BombearComplex c = new BombearComplex();
			return c.addMatrix(A,B);
		}
	}
	
	public ComplexNumber[][] scalarProduct (double alpha, ComplexNumber[][] A){
		if(A==null)
		{
			return null;
		}
		else
		{
			BombearComplex c = new BombearComplex();
			return c.ScalarMatrixMult(alpha,A);
		}
		
		
	}
	
	public ComplexNumber[][] mult (ComplexNumber[][] A, ComplexNumber[][] B){
		if(A==null || B==null)
		{
			return null;
		}
		else
		{
			BombearComplex c = new BombearComplex();
			return c.ComplexMatMult(A,B);
		}
	}
	
	public ComplexNumber[] matrixVectorMult (ComplexNumber[][] A, ComplexNumber[] v){
		if(A==null || v==null)
		{
			return null;
		}
		else
		{
			BombearComplex c = new BombearComplex();
			return c.ComplexMatVMult(A,v);
		}

	}
	
	public ComplexNumber[][] hermitianTranspose (ComplexNumber[][] A){
		if(A==null)
		{
			return null;
		}
		else
		{
			BombearComplex c = new BombearComplex();
			return c.HamTrans(A);
			
		}
	}
	

}
