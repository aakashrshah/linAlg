// CompressionDemo.java
//
// Author: Rahul Simha
// October 2015
//
// Demonstration of two types of compression.
// What to see in the code: all of it.

import edu.gwu.lintool.*;

public class CompressionDemo {

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    public static void main (String[] argv)
    {
	// Comment out two out of three demos, and try one at a time.

	// Demo#1: compressing a 1D array of numbers using DCT.
	dct1D ();

	// Demo#2: compressing a 2D array of numbers (an image) using DCT.
	//dct2D ();

	// Demo#3: compressing a 2D array of numbers (an image) using SVD.
	//svd ();
    }


    static void dct1D ()
    {
	System.out.println ("1D DCT compression");

	// Data: a simple array (with power-of-2 length).
	int N = 8;
	linMagic.N = 8;
	double[] data = new double [N];
	// Put anything in the array.
	for (int i=0; i<N; i++) {
	    data[i] = i;
	}
	LinUtil.print ("Original data", data);

	// Amount by which to compress.
	int K = 2;

	// Compress.
	double[] dctCoeffs = linMagic.compress1D (data, K);
	LinUtil.print ("Compressed DCT coefficients", dctCoeffs);
	System.out.println ("Original storage: N=" + N);
	System.out.println ("Reduced storage: N-K=" + (N-K));

	// Now recover the data to see how close it is to original.
	double[] invertedData = linMagic.uncompress1D (dctCoeffs);
	LinUtil.print ("Inverted Data", invertedData);
    }


    static void dct2D ()
    {
	System.out.println ("2D DCT compression");

	// Simple test
	int M=8, N=8;
	linMagic.N = 8;
	linMagic.M = 8;
	// A 2D array of numbers is an image and vice-versa.
	double[][] originalImage = makeTestImage (M, N);
	LinUtil.print ("original image", originalImage);

	// Compression factor.
	int K = 2; 

	// Compress.
	double[][] dctCoeffs = linMagic.compress2D (originalImage, K);

	LinUtil.print ("dctCoeffs-compressed", dctCoeffs);
	System.out.println ("Original storage: MxN = " + (M*N));
	System.out.println ("Reduced storage: MxN - KxK = " + (M*N - K*K));

	// Recover approximation of original.
	double[][] invertedImage = linMagic.uncompress2D (dctCoeffs);
	LinUtil.print ("Decompressed image", invertedImage);
    }


    static void svd ()
    {
	System.out.println ("2D SVD compression");

	// Make an 8x8 image.
	int M=8,N=8;
	linMagic.N = 8;
	linMagic.M = 8;
	double[][] originalImage = makeTestImage (M, N);
	LinUtil.print ("original image", originalImage);
	
	// SVD compression needs something called "rank"
	int rank = linMagic.getRank (originalImage);
	System.out.println ("Rank of image: " + rank);

	// Pick k to be a number less than the rank.
	int K = 3;
	linMagic.compressSVD (originalImage, K);

	System.out.println ("Original storage: MxN = " + (M*N));
	System.out.println ("Reduced storage: K*(M+N) = " + (K*(M+N)));
	System.out.println ("Compressed image not shown");
	// Recover to see how close to original.
	double[][] decompressedImage = linMagic.uncompressSVD (K);
	LinUtil.print ("Decompressed image", decompressedImage);
    }

    // This makes a random matrix (which serves our purpose as a test image).
    static double[][] makeTestImage (int M, int N)
    {
	double[][] signal = new double [M][N];
	for (int m=0; m<M; m++) {
	    for (int n=0; n<N; n++) {
		signal[m][n] = m*N + n + LinUtil.uniform();
	    }
	}
	return signal;
    }


}
