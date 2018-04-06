// Instructions: 
// Compile and execute

import edu.gwu.lintool.*;

public class DFTExample2 {

    public static void main (String[] argv)
    {
	// Get another piece of digitized music: f(0), f(1), ..., f(N-1).
	double[] signal = SignalExample.getMusicSample2 ();

	int N = signal.length;

	// Plot
	Function F = new Function ("song #2");
	for (int k=0; k<N; k++) {
	    F.add (k, signal[k]);
	}
	F.show ();

	// Compute the spectrum F: F(0), F(1), F(2), ..., F(N-1)
	ComplexNumber[] spectrum = SignalExample.computeDFT (signal, N);

	// Print the significant elements of the spectrum.
	for (int k=0; k<N; k++) {
	    if (spectrum[k].magnitude() > 0.01) {
	    	if(k == 5) {
	    		
	    	}
		System.out.println ("F(" + k + ") = " + spectrum[k]);
	    }
	}

    }

}
