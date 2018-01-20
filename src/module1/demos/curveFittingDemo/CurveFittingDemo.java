// CurveFittingDemo.java
//
// Author: Rahul Simha
// Nov, 2015
//
// To demonstrate curve-fitting using least-squares.
// Needs: DrawTool.java and CurveData.class in same directory.
// What to see in the code: all of the code.

import edu.gwu.lintool.*;

public class CurveFittingDemo {

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    public static void main (String[] argv)
    {
	// Un-comment one at a time, compile and execute.

	// First example: four points from a ellipse (no noise).
	ellipse (0);

	// Second example: also four points, but from different parts.
	//ellipse (1);

	// Third example: four points, now with some noise.
	//ellipse (2);

	// Fourth example: many points, with noise.
	//ellipse (3);
    }


    static void ellipse (int testNum)
    {
	// Get the data.
	CurveData data = CurveData.getEllipseData (testNum);

	// Use linear algebra to find the best fitting ellipse.
	double[] z = linMagic.leastSquaresEllipse (data.x, data.y);

	// Convert linear algebra's solution into parameters for the 
	// fitted ellipse. To understand this, you'll need to look up
	// the equation for an ellipse and then understand how
	// the least-squares parameters are related.
	double e2 = z[0];                        // e^2 = A
	double h = z[1] / (-2*e2);               // h = B/-2e^2
	double k = z[2] / (-2.0);                // k = C/-2
	double a2 = (e2*h*h + k*k - z[3]) / e2;  // a^2 = (e^2h^2 + k^2 - D)/e^2
	double a = Math.sqrt (a2);
	double b2 = e2 * a2;
	double b = Math.sqrt (b2);

	// The ellipse equation needs a,b,h,k.
	// Here: (h,k) is the center, and 2a,2b are the major,minor axes.

	// Plot the original ellipse (red), the data points from original 
	// (which may have noise), and the fitted ellipse (blue).
	CurveData result = new CurveData ();
	result.a = a;  result.b = b;  result.k = k;  result.h = h;
	CurveData.plotEllipse (data, result);
    }


}
