
// Instructions:
// Compile and execute. Read the code to see how the projection
// is subtracted.

public class GramSchmidt {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (0,10, 0,10);
	DrawTool.drawMiddleAxes (true);

	double[] w1 = {6,2};
	double[] w2 = {4,3};
	DrawTool.drawVector (w1);
	DrawTool.drawVector (w2);

	// Start with v1 = w1
	double[] v1 = w1;
	MatrixTool.print(v1);
	// Compute projection of w2 on v1:
	double alpha = MatrixTool.dotProduct(w2,v1) / MatrixTool.dotProduct(v1,v1);
	double[] y1 = MatrixTool.scalarMult (alpha, v1);

	// Subtract the projection from w2
	double[] v2 = MatrixTool.sub (w2, y1);
	
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (v1);
	DrawTool.drawVector (v2);

	// Confirm orthogonality:
	double v1Dotv2 = MatrixTool.dotProduct (v1,v2);
	System.out.println ("v1 dot v2 = " + v1Dotv2);
    }

}
