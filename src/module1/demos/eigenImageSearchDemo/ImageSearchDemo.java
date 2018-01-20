// ImageSearch.java
//
// Author: Rahul Simha
// July, 2015
//
// To demonstrate the eigenfaces algorithm.
// Needs: ImageTool.java in same directory.
// What to see in the code: all of the code up to findClosestImage().

import edu.gwu.lintool.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class ImageSearchDemo {

    // Where the data is stored.
    static String trainingDir = "trainingImages";
    static String eigenDir = "eigenImages";

    // Number of images.
    static int M = 10;

    // For the actual image data, imageNumStart=1. For Geometric, it is 0.
    static int imageNumStart = 0;
    static int imageNumEnd = imageNumStart + M;

    // #rows, cols of images: assumed to be identical. Set after reading images.
    static int numImageRows, numImageCols;

    // Size of each vector: N = numImageRows*numImageCols.
    static int N;

    // ImageTool handles the reading, writing and display of images.
    static ImageTool imTool = new ImageTool ();

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();


    //////////////////////  DEMO CODE //////////////////////////////

    public static void main (String[] argv)
    {
	// Read and process training data. Typically, this occurs
	// just once in an application.
	processTrainingImages ();

	// We'll perform two tests: get a test image and find the closest
	// match in the training data.
	// Test #1:
	int k = findClosestImage ("queryImages/testimage0.png");
	System.out.println ("Best match for testimage0: image" + k + " in the training data");

	// Test #2
	k = findClosestImage ("queryImages/testimage1.png");
	System.out.println ("Best match for testimage1: image" + k + " in the training data");
    }

    static void processTrainingImages ()
    {
	// 1. Process images in training data.
	double[][] data = imagesToVectors ();
	linMagic.N = N;
	linMagic.M = M;

	// 2. Let linear algebra do its thing.
	double[][] U = linMagic.processTrainingImages (data);

	// 3. This is not really needed, but interesting.
	storeEigenImages (U);
    }


    static int findClosestImage (String fileName)
    {
	// 1. Read in test image.
	int[][] testPixels = imTool.imageFileToGreyPixels (fileName);

	// 2. Convert to vector: N x 1
	double[] w = imageToVector (testPixels);

	// 3. Let linear algebra do its thing.
	int k = linMagic.findClosestImage (w);

	// 4. k is the closest image found.
	return k;
    }

    // No need to read further.
    ///////////////////////////////////////////////////////////////
    // BELOW: useful methods but not essential to understanding.

    static void storeEigenImages (double[][] U)
    {
	// Each column of U is an image. 
	for (int j=0; j<linMagic.R; j++) {
	    double[] v = extractColumn (U, j);
	    for (int i=0; i<N; i++) {
		v[i] += linMagic.avg[i];
	    }
	    int[][] greyPixels = vectorToImage (v, numImageRows, numImageCols);
	    imTool.writeToFile (greyPixels, eigenDir + "/eigen" + j + ".png");
	}
    }

    static double[] extractColumn (double[][] U, int j)
    {
	// Extract j-th eigen-image
	// Length of column is # rows.
	double[] v = new double [U.length];
	double max = Double.MIN_VALUE;
	for (int i=0; i<U.length; i++) {
	    v[i] = U[i][j];
	    if (v[i] > max) max = v[i];
	}
	return v;
    }


    static double[][] imagesToVectors ()
    {
	ArrayList<double[]> imagesAsVectors = new ArrayList<double[]> ();
	int minLength = Integer.MAX_VALUE; 
	int maxLength = -1;
	for (int i=imageNumStart; i<imageNumEnd; i++) {
	    String fileName = trainingDir + "/image" + i + ".png";
	    int[][] greyPixels = imTool.imageFileToGreyPixels (fileName);
	    numImageRows = greyPixels.length;
	    numImageCols = greyPixels[0].length;
	    double[] v = imageToVector (greyPixels);
	    imagesAsVectors.add (v);
	    if (v.length > maxLength) maxLength = v.length;
	    if (v.length < minLength) minLength = v.length;
	}
	
	// Fix lengths if they aren't all the same.
	if (minLength != maxLength) {
	    //** For now, we'll just note the error and stop.
	    System.out.println ("min=" + minLength + " max=" + maxLength);
	    System.exit (0);
	}

	// This is the length of each vector.
	N = minLength;

	// Now place vectors in a N x M matrix.
	double[][] data = new double [N][M];
	for (int i=0; i<M; i++) {
	    double[] v = imagesAsVectors.get (i);
	    for (int j=0; j<N; j++) {
		// Note the switch: there are N rows, M columns.
		data[j][i] = v[j];  
	    }
	}

	return data;
    }


    static int[][] vectorToImage (double[] v, int numRows, int numCols)
    {
	// Check length:
	if (numRows*numCols != v.length) {
	    System.out.println ("ERROR: incompatible conversion to image");
	    System.exit (0);
	}
	int[][] greyPixels = new int [numRows][numCols];
	int k = 0;
	for (int i=0; i<greyPixels.length; i++) {
	    for (int j=0; j<greyPixels[0].length; j++) {
		greyPixels[i][j] = (int) v[k++];
	    }
	}
	return greyPixels;
    }

    static double[] imageToVector (int[][] greyPixels)
    {
	int len = greyPixels.length * greyPixels[0].length;
	double[] v = new double [len];
	int k = 0;
	for (int i=0; i<greyPixels.length; i++) {
	    for (int j=0; j<greyPixels[0].length; j++) {
		v[k++] = (double) greyPixels[i][j];
	    }
	}
	return v;
    }

}
