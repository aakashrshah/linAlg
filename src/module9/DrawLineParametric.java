// Instructions:
// See module

public class DrawLineParametric {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	// End points of the line segment:
	double x0=2, y0=3;
	double x1=5, y1=9;

	double delT = 0.25; // t-increment for drawing

	// First, we'll draw the line using DrawTool.
	DrawTool.setLineColor ("blue");
	DrawTool.drawLine (x0,y0, x1,y1);

	for (double t=-1; t<=2; t+=delT) {
	    // Draw points along the line using the parametric approach:
	    double x = t*x0 + (1-t)*x1;
	    double y = t*y0 + (1-t)*y1;
	    System.out.println("x: " + x + "   y: " + y);

	    DrawTool.drawPoint (x,y);
	}

    }

}
