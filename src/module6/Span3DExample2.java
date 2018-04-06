// Instructions:
// (1) First, compile and execute to see two vectors.
// (2) Then un-comment the section below, compile and execute.


import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Span3DExample2 extends Application {

    String title = "Span example";

    void drawingCommands () 
    {
	// We're only going to draw u and v.
	double[] u = {-1,1,0};
	double[] v = {1,1,0};
	double[] w = {0,-1,0};

	// Draw.
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (u);
	d3.setDrawColor (Color.RED);
	d3.drawVector (v);
	d3.setDrawColor (Color.GREEN);
	d3.drawVector (w);


	// Un-comment this section after you've first taken a look
	// at the three vectors above.

	/*
	double alphaLow=-1, alphaHigh=1, alphaStep=0.1;
	double betaLow=-1, betaHigh=1, betaStep=0.1;
	
	for (double alpha=alphaLow; alpha<=alphaHigh; alpha+=alphaStep) {
	    for (double beta=betaLow; beta<=betaHigh; beta+=betaStep) {
		    double[] temp1 = MatrixTool.scalarMult (alpha, u);
		    double[] temp2 = MatrixTool.scalarMult (beta, v);
		    double[] z = MatrixTool.add (temp1, temp2);
		    // Draw
		    d3.setDrawColor (Color.LIGHTGRAY);
		    d3.drawVector (z);
	    }
	}
	*/

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
