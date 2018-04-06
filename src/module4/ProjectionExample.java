// Instructions:
// Implement projection in the proj() method of MatrixTool.java

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class ProjectionExample {

    public static void main (String[] argv)
    {
	// Test dot product.
	double[] u = {7,4};
	double[] v = {3,5};

	// Projection.
	double[] w = MatrixTool.proj (v, u);
	MatrixTool.print (w);

	// Perpendicular to the projection:
	double[] z = MatrixTool.sub (v, w);
	MatrixTool.print (z);

	// Check perpendicularity
	double uz = MatrixTool.dotProduct (u,z);
	System.out.println ("u dot z = " + uz);

	// Draw. Should produce the same results as the diagram in 
	// module notes.
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);
	DrawTool.drawVector (u);
	DrawTool.drawVector (v);
	DrawTool.setArrowColor ("red");
	DrawTool.drawVector (w);
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (z);
	DrawTool.drawArrow (w[0], w[1], v[0], v[1]);
    }


}


