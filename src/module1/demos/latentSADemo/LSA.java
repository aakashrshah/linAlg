// LSA.java
//
// Author: Rahul Simha, 2015
//
// Demonstrates LSA (Latent Semantic Analysis)
// To execute, choose either the wikinews dataset or berryPaper dataset.
// Then compile and execute.
// The output has two types of correlations between text files.
// (1) Correlations of the word-doc vectors, normalized but without LSA.
// (2) The same correlations but after applying LSA.
// The LSA correlations are greatly sharpened, as one can see by
// examining the text.
// Note: we use spearman correlation because the word-doc vectors
// are binary (and thus, the raw numbers don't indicate anything).
//
// What to read: the correlations() method.

import edu.gwu.lintool.*;
import java.io.*;
import java.util.*;


public class LSA {

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    // Words like "the, an, it ... etc" that aren't topic-specific.
    static String stopWordFile = "StopWords.txt";

    // Wikinews: M=7
    static int M = 7;
    static String dataDir = "wikinews";

    // Berry paper data: M=17
    //static int M = 17;
    //static String dataDir = "berryPaper";

    static String trainingDir = dataDir + "/trainingText";
    static String testDir = dataDir + "/testText";

    // N = length of each vector = total number o words.
    // This is calculated from the data after the data is read. All
    // files are scanned and all unique words minus stopWords are used.
    static int N;

    // The word-document matrix.
    static double[][] data;

    // Data structures for words.
    static TreeSet<String> stopWords = new TreeSet<String> ();
    static TreeSet<String>[] wordsInFile = new TreeSet [M];
    static TreeSet<String> allWords = new TreeSet<String> ();
    static HashMap<String, Integer> wordToNum = new HashMap<String, Integer> ();
    static HashMap<Integer, String> numToWord = new HashMap<Integer, String> ();

    public static void main (String[] argv)
    {
	correlations ();
    }    

    static void correlations ()
    {
	// We'll print 2 types of correlations amongst columns (documents).
	// (1) Direct correlation from the data vector X.
	// (2) Correlation in a transformed coords space, through LSA.

	// 1. Process text samples once to get the word-doc matrix.
	data = textToVectors ();

	// 3. Make normalized data matrix: N words x M documents.
	//    Note: X is N x M.
	double[][] X = normalizeData (data);

	// Part (1): standard covariance 
	double[][] covX = covariance (X);
	System.out.println ("Covariance matrix without SVD");
	for (int i=0; i<covX.length; i++) {
	    for (int j=0; j<covX[0].length; j++) {
		System.out.printf ("  %6.3f", covX[i][j]);
	    }
	    System.out.println ();
	}

	// Linear algebra returns a transformed dataset in 
	// new LSA coordinates or vectors.
	double[][] Y = linMagic.latentSemanticCoords (X);

	// Part (2): Covariance in transformed coords.
	double[][] covY = covariance (Y);
	System.out.println ("Covariance matrix in new coords:");
	for (int i=0; i<covY.length; i++) {
	    for (int j=0; j<covY[0].length; j++) {
		System.out.printf ("  %6.3f", covY[i][j]);
	    }
	    System.out.println ();
	}

	// Now identify where correlations are high. These might be
	// related documents (same topic).
	for (int i=0; i<covX.length; i++) {
	    System.out.print ("Related to text" + i + ": ");
	    for (int j=0; j<covX[0].length; j++) {
		if ( covY[i][j] > 0.9 ) {
		    // Increase in correlation => j is related to i.
		    System.out.print (" text" + j);
		}
	    }
	    System.out.println ();
	}

	// Text 6 is historically interesting. Ask me about it.
    }

    // No need to read further.
    ///////////////////////////////////////////////////////////////
    // Statistics

    static double[][] normalizeData (double[][] data)
    {
	double[] avg = computeAverageVector (data);
	double[][] X = new double [N][M];
	for (int i=0; i<N; i++) {
	    for (int j=0; j<M; j++) {
		X[i][j] = data[i][j] - avg[i];
	    }
	}
	return X;
    }


    static double[] computeAverageVector (double[][] data)
    {
	// Need column length.
	double[] avg = new double [N];

	for (int i=0; i<N; i++) {
	    double sum = 0;
	    for (int j=0; j<M; j++) {
		// Sum across the M columns.
		sum += data[i][j];
	    }
	    avg[i] = sum / M;
	}

	return avg;
    }


    static double[][] covariance (double[][] X)
    {
	int K = X[0].length;     // # cols.

	double[][] cov = new double [K][K];
	for (int i=0; i<K; i++) {
	    for (int j=0; j<K; j++) {
		cov[i][j] = spearman (X, i, j);
	    }
	}

	return cov;
    }

    static double spearman (double[][] X, int a, int b)
    {
	int n = X.length;
	double[] Xa = new double [n];
	double[] Xb = new double [n];
	for (int i=0; i<n; i++) {
	    Xa[i] = X[i][a];
	    Xb[i] = X[i][b];
	}
	double rho = spearman (Xa, Xb);
	return rho;
    }

    static double spearman (double[] a, double[] b)
    {
	if (a.length != b.length) {
	    System.out.println ("ERROR: spearman: not equal lengths");
	    System.exit (0);
	}

	int n = a.length;
	double[] rankA = computeRank (a);
	double[] rankB = computeRank (b);

	// Now compute correlation.
	double sumDiffSq = 0;
	for (int i=0; i<n; i++) {
	    double diff = rankA[i] - rankB[i];
	    sumDiffSq += (diff * diff);
	}	
	
	double rho = 1.0 - (6 * sumDiffSq) / (n * (n*n - 1));
	return rho;
    }

    static double[] computeRank (double[] x)
    {
	int n = x.length;
	double[] rank = new double [n];
	ValIndex[] data = new ValIndex [n];
	for (int i=0; i<n; i++) {
	    data[i] = new ValIndex (x[i], i);
	}
	Arrays.sort (data);
	for (int i=0; i<n; i++) {
	    data[i].rank = i;
	}
	spearmanAvg (data); 

	for (int i=0; i<n; i++) {
	    int index = data[i].index;
	    rank[index] = data[i].rank;
	}

	return rank;
    }

    static void spearmanAvg (ValIndex[] data)
    {
	int n = data.length;
	int i = 0;
	while (i < n) {
	    int j = i+1;
	    while ( (j < n) && (data[i].value == data[j].value) ) {
		j++;
	    }
	    // Now i,j is the range of equal values.
	    if (j > i+1) {
		averageRank (data, i, j-1);
	    }
	    // To the next range.
	    i = j;
	}
    }

    static void averageRank (ValIndex[] data, int i, int j)
    {
	// Set the new rank to be the average in the range i .. j.
	double s = 0;
	for (int k=i; k<=j; k++) {
	    s += data[k].rank;
	}
	double avg = s / (j-i+1);
	for (int k=i; k<=j; k++) {
	    data[k].rank = avg;
	}
    }


    ///////////////////////////////////////////////////////////////
    // Utility methods for handling text.

    static int numCommonWords (int m, int n)
    {
	int numCommon = 0;
	for (int j=0; j<N; j++) {
	    if ((data[j][m] > 0) && (data[j][n] > 0)) {
		numCommon ++;
	    }
	}
	return numCommon;
    }



    static int commonCount (double[] u, double[] v) 
    {
	int count = 0;
	for (int i=0; i<u.length; i++) {
	    if ((u[i] > 0) && (v[i] > 0)) {
		count ++;
	    }
	}
	return count;
    }


    static double[][] textToVectors ()
    {
	double[][] data = null;
	readStopWords ();
	//System.out.println ("Stop words read");

	// Now scan all files, and extract all unique non-stop words.
	readAllWords ();  
	// This is our vector length.
	N = allWords.size ();
	//System.out.println ("All words read: N=" + N);
	
	// Next, we create our mapping files.
	int wordCount = 0; 
	for (String s: allWords) {
	    wordToNum.put (s, wordCount);
	    numToWord.put (wordCount, s);
	    wordCount ++;
	}

	// Next, convert each to a vector.
	// Note: all vectors are already of length N.

	// Now place vectors in a N x M matrix.
	data = new double [N][M];
	for (int i=0; i<M; i++) {
	    double[] v = convertToWordVector (wordsInFile[i]);
	    for (int j=0; j<N; j++) {
		// Note the switch: there are N rows, M columns.
		data[j][i] = v[j];  
	    }
	}

	System.out.println ("Data matrix created");
	return data;
    }


    static double[] convertToWordVector (String text)
    {
	// First extract words.
	String[] words = text.split ("[^A-Za-z]+");
	// Build vector.
	double[] x = new double [N];
	for (int i=0; i<words.length; i++) {
	    if (wordToNum.containsKey (words[i])) {
		int index = wordToNum.get (words[i]);
		x[index] += 1;     // Frequency count.
	    }
	}
	return x;
    }

    static double[] convertToWordVector (TreeSet<String> words)
    {
	double[] x = new double [N];
	for (String s: words) {
	    if (wordToNum.containsKey (s)) {
		int index = wordToNum.get (s);
		x[index] += 1;     // Frequency count.
	    }
	}
	return x;
    }


    static void readAllWords ()
    {
	for (int i=0; i<M; i++) {
	    wordsInFile[i] = readWordsInFile (trainingDir + "/" + "text" + i + ".txt");
	}

	// Now add all words that are in at least two files.
	for (int i=0; i<M; i++) {
	    for (String w: wordsInFile[i]) {
		// See if it exists elsewhere.
		for (int j=i+1; j<M; j++) {
		    if ( wordsInFile[j].contains(w) ) {
			//System.out.println ("readAllWords: w=" + w + " is in both i=" + i + " and j=" + j);
			allWords.add (w);
		    }
		}
	    }
	}
    }

    static TreeSet<String> readWordsInFile (String fileName)
    {
	TreeSet<String> wordsInFile = new TreeSet<String> ();
	try {
	    LineNumberReader lnr = new LineNumberReader (new FileReader (fileName));
	    String line = lnr.readLine ();
	    while (line != null) {
		String[] words = line.split ("[^A-Za-z]+");
		for (int i=0; i<words.length; i++) {
		    boolean isStopWord = stopWords.contains(words[i].toLowerCase());
		    //System.out.println ("i=" + i + "[" + words[i] + "], isStop=" + isStopWord);
		    if ((words[i].length() > 0) && (! isStopWord)) {
			wordsInFile.add (words[i]);
		    }
		}
		line = lnr.readLine ();
	    }
	}
	catch (IOException e) {
	    System.out.println (e);
	}
	return wordsInFile;
    }

    static void readStopWords ()
    {
	try {
	    LineNumberReader lnr = new LineNumberReader (new FileReader (stopWordFile));
	    String line = lnr.readLine ();
	    while (line != null) {
		stopWords.add (line);
		line = lnr.readLine ();
	    }
	}
	catch (IOException e) {
	    System.out.println (e);
	}
    }

}


// For Spearman correlations

class ValIndex implements Comparable {
    double value;
    int index;
    double rank;
    public ValIndex (double v, int i) {value = v; index = i;}
    public int compareTo (Object obj)
    {
	ValIndex v = (ValIndex) obj;
	if (value < v.value) {
	    return -1;
	}
	else if (value > v.value) {
	    return 1;
	}
	else {
	    return 0;
	}
    }

    public boolean equals (Object obj)
    {
	ValIndex v = (ValIndex) obj;
	return (value == v.value);
    }
}



