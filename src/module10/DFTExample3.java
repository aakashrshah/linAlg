// Instructions: 
// Set the real and imaginary parts of F[5] and F[59] to zero.
// Compile and execute

import edu.gwu.lintool.*;

public class DFTExample3 {

    public static void main (String[] argv)
    {
	// Noisy music sample: f(0), f(1), ..., f(N-1)
	double[] signal = SignalExample.getMusicSample2 ();
	int N = signal.length;

	Function F = new Function ("song #2");
	for (int k=0; k<N; k++) {
	    F.add (k, signal[k]);
	}

	// Compute the spectrum F: F(0), F(1), F(2), ..., F(N-1)
	ComplexNumber[] spectrum = SignalExample.computeDFT (signal, N);

	// We know that F(5) and F(59) represent the significant
	// higher frequency components: noise.

	// Filter out the noise: Zero out real and imaginary parts
	// of F(5) and F(59), that is, from spectrum[5], spectrum[59].
	// INSERT YOUR CODE HERE
	spectrum[5].re = 0;
	spectrum[5].im = 0;
	spectrum[59].re = 0;
	spectrum[59].im = 0;
	// Apply the inverse DFT to get a "clean" complex signal.
	ComplexNumber[] modifiedSignal = SignalExample.computeInverseDFT (spectrum, N);

	// Extract the real part.
	double[] realModifiedSignal = SignalExample.toReal (modifiedSignal);

	Function G = new Function ("song #2 filtered");
	for (int k=0; k<N; k++) {
	    G.add (k, realModifiedSignal[k]);
	}

	// Plot both.
	Function.show (F,G);
    }

}
