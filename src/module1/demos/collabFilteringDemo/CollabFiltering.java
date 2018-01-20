// CollabFiltering.java
//
// Author: Rahul Simha
// October 2015
//
// Demonstration of Collaborative Filtering (Recommendation engine)
// What to see in the code: the main method.

import edu.gwu.lintool.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class CollabFiltering {

    // numItems = numMovies in Netflix.
    static int numUsers, numItems;

    // The known ratings are read from a textfile. The goal, of course,
    // is to predict the missing ratings.
    static double[][] knownRatings, predictedRatings1, predictedRatings2;

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    public static void main (String[] argv)
    {
	readRatingsFile ("ratings.txt");
	// Two different approaches:
	// (1) Use a reduced Singular Value Decomposition to fill 
	//     missing entries.
	predictedRatings1 = linMagic.predictRatingsSVD (knownRatings);

	// (2) Use vector-cosines to find closest user that has 
	//     missing ratings.
	predictedRatings2 = linMagic.predictRatingsUserCosines (knownRatings);

	print ();
    }

    // No need to read below this
    /////////////////////////////////////////////////////////////

    static void print ()
    {
	for (int i=0; i<numUsers; i++) {
	    System.out.println ("\nRatings for user " + i);
	    System.out.printf ("Known:         ");
	    for (int j=0; j<numItems; j++) {
		System.out.printf ("  %4.1f", knownRatings[i][j]);
	    }
	    System.out.printf ("\n");
	    System.out.printf ("Predicted #1:  ");
	    for (int j=0; j<numItems; j++) {
		System.out.printf ("  %4.1f", predictedRatings1[i][j]);
	    }
	    System.out.printf ("\n");
	    System.out.printf ("Predicted #2:  ");
	    for (int j=0; j<numItems; j++) {
		System.out.printf ("  %4.1f", predictedRatings2[i][j]);
	    }
	    System.out.printf ("\n");
	}
    }

    public static void readRatingsFile (String fileName)
    {
        try {
	    FileReader fileReader = new FileReader (fileName);
	    Scanner scanner = new Scanner (fileReader);
	    numUsers = scanner.nextInt ();
	    System.out.println (" >> numUsers=" + numUsers);
	    numItems = scanner.nextInt ();
	    System.out.println (" >> numItems=" + numItems);

	    knownRatings = new double [numUsers][numItems];

	    for (int i=0; i<numUsers; i++) {
		for (int j=0; j<numItems; j++) {
		    knownRatings[i][j] = scanner.nextInt ();
		}
	    }

	    System.out.println ("File read successful");

	}
        catch (IOException e){
            System.out.println ("Bad input file: " + fileName);
            return;
        }

    }
    

}
