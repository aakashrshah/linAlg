package edu.aakash.lintool;
import edu.aakash.lintool.BombearComplex;
import edu.aakash.lintool.BombearLinTool;
import edu.gwu.lintool.LinTest;

public class TestA2 {

    public static void main (String[] argv)
    {
    	
	BombearLinTool lin = new BombearLinTool();
	BombearComplex com = new BombearComplex(2,1);

	LinTest.testREF (lin);
	LinTest.testRREF (lin);
	LinTest.testSolveFromREF (lin);
	LinTest.testSolveFromRREF (lin);
	LinTest.testInverse (lin);
	
    LinTest.testComplex(com);

    }

}
