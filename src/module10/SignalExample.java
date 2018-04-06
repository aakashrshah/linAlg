
import javax.sound.sampled.*;
import java.util.*;
import edu.gwu.lintool.*;

public class SignalExample {

    static LinMagic linMagic = new LinMagic ();

    public static double[] getMusicSample ()
    {
	int N = 64;
	int freq = 1;
	int C = 10;
	double[] signal = makeSine (N, freq, 10);

	return signal;
    }

    public static double[] getMusicSample2 ()
    {
	int N = 64;
	int freq = 1;
	int C = 10;
	double[] signal = makeSine (N, freq, 10);

	// A second one of different frequency and amplitude.
	freq = 5;
	double[] signal2 = makeSine (N, freq, 3);

	// Add.
	for (int i=0; i<N; i++) {
	    signal[i] = signal[i] + signal2[i];
	}

	return signal;
    }

    static double[] toReal (ComplexNumber[] signal)
    {
	double[] realSignal = new double [signal.length];
	for (int i=0; i<realSignal.length; i++) {
	    realSignal[i] = signal[i].re;
	}
	return realSignal;
    }


    static double[] makeSine (int N, int f, double a)
    {
	// f=0 => DC,   f=1 => full cycle starting at 0.
	// f =2 => two full cycles .. etc
	// f = N/2 => N/2 full cycles.

	double[] signal = new double [N];
	
	if (f == 0) {
	    // DC signal.
	    for (int i=0; i<N; i++) {
		signal[i] = a;
	    }
	    return signal;
	}
	
	for (int i=0; i<N; i++) {
	    signal[i] = a* Math.sin (((2*Math.PI*i*f) / (double)N));
	}

	return signal;
    }


    public static ComplexNumber[] computeDFT (double[] signal, int N) 
    {
	ComplexNumber[] spectrum = linMagic.computeDFT (signal);
	return spectrum;
    }


    public static ComplexNumber[] computeInverseDFT (ComplexNumber[] spectrum, int N) 
    {
	ComplexNumber[] signal = linMagic.computeInverseDFT (spectrum);
	return signal;
    }


}


