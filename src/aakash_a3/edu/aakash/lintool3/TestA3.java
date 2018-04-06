package edu.aakash.lintool3;

import edu.gwu.lintool.LinTest;
import edu.gwu.lintool.LinTool;
import edu.gwu.lintool.LinToolImpl;

public class TestA3 {

    public static void main (String[] argv)
    {

	BombearLinTool lin =  new BombearLinTool();
	
	LinTest.testAreColumnsLI (lin);
	LinTest.testGramSchmidt (lin);
	LinTest.testQR (lin);
	
	// Test complex matrix operations.
	LinTool clin = new LinToolImpl ();
	LinTest.testComplexMatrices (clin);

    }

}
