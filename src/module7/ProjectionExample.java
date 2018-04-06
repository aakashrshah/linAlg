// Instructions:
// Type in the values for v

public class ProjectionExample {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (0,10, 0,10);
	DrawTool.drawMiddleAxes (true);

	double[] w = {4,3};
	DrawTool.drawVector (w);

	// Enter v here:
	double[] v = {3,1};
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (v);
	
	double alpha = MatrixTool.dotProduct(w,v) / MatrixTool.dotProduct(v,v);
	System.out.println ("alpha = " + alpha);
	double[] y = MatrixTool.scalarMult (alpha, v);
	
	DrawTool.setArrowColor ("red");
	DrawTool.drawVector (y);

	double[] z = MatrixTool.sub (w,y);
	System.out.println ("z=(" + z[0] + "," + z[1] + ")");
	DrawTool.setArrowColor ("green");
	DrawTool.drawVector (z);
	DrawTool.drawArrow (y[0],y[1], w[0],w[1]);

	// Confirm z perpendicular to v
	double zDotv = MatrixTool.dotProduct (z,v);
	System.out.println ("z dot v = " + zDotv);
    }

}
