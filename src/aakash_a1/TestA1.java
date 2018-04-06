import edu.gwu.lintool.*;


public class TestA1 {

    public static void main (String[] argv)
    {
	// Make an instance of your ComplexNumber class. REPLACE
	// AliceComplex below with your version.
	BombearComplex c = new BombearComplex (3, 5);
	// This tests your implementation of complex numbers.
	LinTest.testComplex(c);
	
	// REPLACE the line below with your tool, and un-comment.
	BombearLinTool lin = new BombearLinTool ();

	// Un-comment tests one by one until all are passed.
	LinTest.testVectorOperations (lin);
	LinTest.testMatrixOperations (lin);
	
    }

}
