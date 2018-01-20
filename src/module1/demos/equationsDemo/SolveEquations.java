// SolveEquations.java
//
// Author: Rahul Simha
// November 2015
//
// To demonstrate solution of simultaneous linear equations
// What to see in the code: all of the code in main()


import java.util.*;
import java.io.*;
import edu.gwu.lintool.*;

public class SolveEquations {

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    // N = # variables, M = # equations
    static int N, M;

    // Store the coefficients of variables in a MxN 2D array:
    static double[][] variableCoeffs;

    // Store the right-hand-side values in a 1D array:
    static double[] rightHandSide;

    public static void main (String[] argv)
    {
	// Read the input data (the equations):
	readEquationData ("equations.txt");
	printData ();

	// Solve:
	String results = linMagic.solveEquations (variableCoeffs, rightHandSide);
	System.out.println (results);
    }


    static void printData ()
    {
	System.out.println ("Each row of coeffs, and RHS:");
	for (int i=0; i<M; i++) {
	    for (int j=0; j<N; j++) {
		System.out.printf (" %6.4f", variableCoeffs[i][j]);
	    }
	    System.out.printf ("     %6.4f\n", rightHandSide[i]);
	}
    }

    // No need to read further, unless you like ugly string-parsing code.
    ////////////////////////////////////////////////////////////////////

    public static void readEquationData (String fileName)
    {
        try {
	    FileReader fileReader = new FileReader (fileName);
	    LineNumberReader lnr = new LineNumberReader (fileReader);
	    String line = lnr.readLine ();
	    Scanner scanner = new Scanner (line);
	    N = scanner.nextInt ();
	    System.out.println ("Num variables=" + N);
	    line = lnr.readLine ();
	    scanner = new Scanner (line);
	    M = scanner.nextInt();
	    System.out.println ("Num equations=" + M);

	    variableCoeffs = new double [M][N];
	    rightHandSide = new double [M];

	    for (int row=0; row<M; row++) {
		line = lnr.readLine ();
		parseRow (line, row);
	    }

	}
        catch (IOException e){
            System.out.println ("Bad input file: " + fileName);
            return;
        }
    }
	
    static void parseRow (String line, int row)
    {
	try {
	    //System.out.println ("Parsing row=[" + line + "]");
	    ArrayList<Double> coeffs = new ArrayList<Double> ();
	    Scanner scanner = new Scanner (line);
	    for (int i=0; i<N; i++) {
		double d = scanner.nextDouble();
		variableCoeffs[row][i] = d;
	    }
	    // Last one is RHS
	    double d = scanner.nextDouble ();
	    rightHandSide[row] = d;
	}
	catch (Exception e) {
	    e.printStackTrace ();
	    System.out.println ("Improper input file");
	    System.exit (0);
	}
    }

}
