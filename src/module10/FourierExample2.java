// Instructions:
// (1) Write code below to compute the Fourier sum. Then compile/execute.
// (2) Set a[k]=0 for k>5 to remove high frequencies.

public class FourierExample2 {

    public static void main (String[] argv)
    {
	int numPoints = 200;
	double delT = 1.0 / numPoints;
	Function F = new Function ("Fourier approx to mystery function");
	for (double t=0; t<=1; t+=delT) {
	    F.add (t, getFourierSum(t));
	}
	F.show ();
    }

    static double getFourierSum (double t)
    {
	double a0 = 0.0;
	double[] a = {0,0,2,0,5,0,0,0,0,2,0};   // a[2]=2, a[4]=5, a[9]=2
	double[] b = {0,0,0,0,0,0,0,0,0,0,0};
	int N = a.length-1;

	// INSERT YOUR CODE HERE to compute 
	
//	 a0 + sum_i ( a[i]*sin(2*pi*i*t) + b[i]*cos(2*pi*i*t) )
//	 i varies from 1 to N (including N).
	double sum_i = a0;
	for(int i=1;i<=N;i++) {
		sum_i += (a[i] * Math.sin(2*Math.PI*i*t)) +  (b[i] * Math.cos(2*Math.PI*i*t));
		System.out.println(sum_i);
	}
//	double af = 0.0;
	return sum_i;
//	return af;

    }

}
