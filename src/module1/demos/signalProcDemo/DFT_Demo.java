// DFT_Demo.java
//
// Author: Rahul Simha, 2015
//
// Demonstrates elementary signal processing via the Discrete Fourier Transform
//
// What to read: 

import edu.gwu.lintool.*;

public class DFT_Demo {

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    // 8192 = 2^13 samples per second. 
    static int samplesPerSecond = 8192;
    static byte[] samples;

    public static void main (String[] argv)
    {
	// Play once with commenting out playFiltered(). Then
	// un-comment playFiltered(), and comment out playUnfiltered().
	// Compile each time.

	makeSound ();
	playUnfiltered ();
	//playFiltered ();
    }


    static void makeSound ()
    {
	// Two octaves of A. We'll take low A to be 220 Hz, high A as 440Hz.
	byte[] lowA = SignalProc.makeSampledSound (220, samplesPerSecond, 3);
	byte[] highA = SignalProc.makeSampledSound (440, samplesPerSecond, 3);
	samples = SignalProc.addSounds (lowA, highA);
    }


    static void playUnfiltered ()
    {
        SignalProc.playSoundBytes (samples, samplesPerSecond);
    }


    static void playFiltered ()
    {
	// Window size of N=1024
	int N = 1024;

	int numWindows = samples.length / N;
	System.out.println ("Number of windows: " + numWindows);

	byte[] filteredBytes = new byte [samples.length];

        // Process each window separately.
        for (int w=0; w<numWindows; w++) {
	    // Make space for the complex signal and fill it with N samples in window.
	    double[] realSignal = new double [N];
            for (int i=0; i<N; i++) {
		realSignal[i] = (double) samples[w*N + i];
            }

	    // Get DFT of signal using linear algebra.
	    ComplexNumber[] spectrum = linMagic.computeDFT (realSignal);

	    // Filter out 57, 967 (where high A is concentrated)
	    spectrum[57].re = spectrum[57].im = 0;
	    spectrum[967].re = spectrum[967].im = 0;

	    // Convert back to signal domain.
	    ComplexNumber[] filteredSignal = linMagic.computeInverseDFT (spectrum);

            // Copy to filtered bytes.
            byte[] filteredWindow = SignalProc.convertComplexSignalToBytes (filteredSignal);
            for (int i=0; i<N; i++) {
                filteredBytes[w*N+i] = filteredWindow[i];
            }
	    System.out.println ("Processed window w= " + w);
        }

	// Now play the sound.
        SignalProc.playSoundBytes (filteredBytes, samplesPerSecond);
    }

}
