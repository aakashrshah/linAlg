// Instructions:
// (1) Copy over your code for computing b(n,k,t)
// (2) Compile and execute once to see the control points alone.
// (3) Un-comment the block that computes the curve, then compile/execute.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

class BezierPoint3D {
    double x,y,z;
    public BezierPoint3D (double x, double y, double z)
    {
	this.x=x;  this.y=y;  this.z=z;
    }
}

public class Bezier3D_V1 extends Application {

    String title = "Bezier 3D";

    void drawingCommands () 
    {
	// Total of 4x4=16 control points.
	int m=3, n=3;
	BezierPoint3D[][] controlPoints = new BezierPoint3D [m+1][n+1];
	setControlPoints (m,n,controlPoints);	

	// Increments along the parametric parameters
	double delS=0.05, delT=0.05;


	// First plot the control points
	ArrayList<BezierPoint3D> controlPointSequence = new ArrayList<BezierPoint3D> ();
	for (int i=0; i<=m; i++) {
	    for (int j=0; j<=n; j++) {
		d3.drawPoint (controlPoints[i][j].x, controlPoints[i][j].y, controlPoints[i][j].z);
		controlPointSequence.add (controlPoints[i][j]);
	    }
	}

	/* UN-COMMENT the whole block

	// Now plot the surface as a collection of points. This is
	// not the best way to draw a surface, but it'll suffice for the demo.
	d3.setDrawColor (Color.BLUE);

	for (double t=0; t<=1; t+=delT) {

	    // Use each control point for coefficients.
	    double x=0, y=0, z=0;
	    int N = controlPointSequence.size();
	    for (int k=0; k<N; k++) {
		BezierPoint3D p = controlPointSequence.get (k);
		x += p.x * bernstein (N-1,k,t);
		y += p.y * bernstein (N-1,k,t);
		z += p.z * bernstein (N-1,k,t);
	    }
	    d3.drawPoint (x,y,z);
	}

	*/
	// Un-comment the above block 
    }

    static double bernstein (int n, int k, double t)
    {
	// INSERT YOUR CODE HERE:

    }

    static void setControlPoints (int m, int n, BezierPoint3D[][] controlPoints)
    {
	// We'll space the x,y values evenly on the integers, just
	// as an example. The shape drawn is a half-cylindar that
        // grows out in size.
	for (int i=0; i<=m; i++) {
	    for (int j=0; j<=n; j++) {
		double x = 1 + 2*i;
		double y = 1 + 2*j;
		double z = 2 * i * Math.sin(j*(Math.PI/n));
		controlPoints[i][j] = new BezierPoint3D (x,y,z);
	    }
	}
    }

    // No need to read further
    //////////////////////////////////////////////////////////

    Draw3D d3;

    void preambleCommands ()
    {
	d3.setAmbientLight(false);
	d3.setPointLight(true);
	d3.setCumulate(false);
	d3.setSequencingOn();
	d3.setVectorRadius(1);
        d3.setArrowRadius(1);
    }

    public void start (Stage primaryStage) 
    {
	d3 = new Draw3D ();
	Scene scene = d3.buildScene ();
	preambleCommands ();
	drawingCommands ();
	d3.setStart ();
	primaryStage.setScene (scene);
	primaryStage.setTitle (title);
	primaryStage.show ();
    }

    public static void main (String[] argv)
    {
	launch (argv);
    }

}
