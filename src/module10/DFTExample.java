// Note: this will work only if lintool.jar is in your CLASSPATH.
// If it's not, you can download lintool.jar and use the java -cp option.
//
// Instructions:
// (1) First read the code. Then compile/execute.
// (2) Un-comment the code inside the print-loop to only print 
//     the significant spectrum components.

import edu.gwu.lintool.*;

public class DFTExample {

    public static void main (String[] argv)
    {
	// Get a piece of digitized music.
	double[] signal = SignalExample.getMusicSample ();
	// This is the signal f(0), f(1), f(2), ..., f(N-1).

	int N = signal.length;

	// Plot
	Function F = new Function ("song #1");
	for (int k=0; k<N; k++) {
	    F.add (k, signal[k]);
	}
	F.show ();

	// Compute the spectrum F: F(0), F(1), F(2), ..., F(N-1)
	ComplexNumber[] spectrum = SignalExample.computeDFT (signal, N);

	// Print the spectrum. Initially, all of them, then only the
	// significant ones.

	for (int k=0; k<N; k++) {
	    // Print every F(k)
	    System.out.println ("F(" + k + ") = " + spectrum[k]);

	    // Un-comment this part and comment out the above println().
	    
	    if (spectrum[k].magnitude() > 0.01) {
		System.out.println ("F(" + k + ") = " + spectrum[k]);
	    }
	    
	}
    }

}
