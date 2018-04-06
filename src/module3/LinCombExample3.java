// Instructions:
// Write code to systematically search over alpha, beta values.


public class LinCombExample3 {

    public static void main (String[] argv) 
    {
	double[] u = {1,4};
	double[] v = {3,2};
	double[] z = {7.5,10};
	double f = findMagnitude(z);
	LinCombExample2 ll = new LinCombExample2();
	ll.
	for (double alpha=0; alpha<=10; alpha+=0.1) {
	    for (double beta=0; beta<=10; beta+=0.1) {
		// See if alpha*u + beta*v is approximately z.
	    	double[] ans = add (scalarMult(alpha, u), scalarMult(beta,v));
	    	
	    		double temp = findMagnitude(ans);
	    		if(temp == f) {
	    			System.out.println("Yes");
	    		
	    		}

	    }
    }
    }
    
    static double[] add (double[] u, double[] v)
    {
    	
    	
    	int max;
		if(v.length >= u.length) {
    		max = v.length;
    	}
    	else {
    		max = u.length;
    	}
    	double[] z = new double[max];
    	int i = 0;
    	for(double each:u){
    		z[i] = u[i] + v[i];
    		i++;
    	}

		return z;
    }

    static double[] scalarMult (double alpha, double[] v)
    {
	    	double[] z =  new double[v.length];
	    	int i = 0;
	    	for(double each:v){
	    		z[i] = alpha * each;
	    		i++;
	    	}
		return z;
    }
    
    static double findMagnitude(double[] f) {
    	
	    	double a=0.0;
	    for(int i = 0;i<f.length;i++) {
	    	a = a+ (f[i])*(f[i]);
	    }
	    
	    return Math.sqrt(a);
    }
}
