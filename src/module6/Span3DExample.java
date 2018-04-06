// Instructions:
// (1) First, compile and execute to see three vectors.
// (2) Write code in MatrixTool for scalarMult(), add().
// OR
// (2) Use your implementation of LinTool.
// (3) Un-comment the section of code that tries linear combinations
//     below, compile and execute.

import org.edisonwj.draw3d.*;

import edu.aakash.lintool3.MatrixTool;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Span3DExample extends Application {

    String title = "Span example";

    void drawingCommands () 
    {
	double[] u = {1,1,0};
	double[] v = {-1,1,0};
//	double[] w = {0,-1,0};

	// Draw.
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (u);
	d3.drawVector (v);
//	d3.drawVector (w);

	// Un-comment this section after you've first taken a look
	// at the three vectors above.


	double alphaLow=-1, alphaHigh=1, alphaStep=0.5;
	double betaLow=-1, betaHigh=1, betaStep=0.1;
	double gammaLow=-1, gammaHigh=1, gammaStep=0.5;
	
	for (double alpha=alphaLow; alpha<=alphaHigh; alpha+=alphaStep) {
	    for (double beta=betaLow; beta<=betaHigh; beta+=betaStep) {
		for (double gamma=gammaLow; gamma<=gammaHigh; gamma+=gammaStep) {	    
		    double[] temp1 = MatrixTool.scalarMult (alpha, u);
		    double[] temp2 = MatrixTool.scalarMult (beta, v);
//		    double[] temp3 = MatrixTool.scalarMult (gamma, w);
		    double[] temp4 = MatrixTool.add (temp1, temp2);
		    double[] z = MatrixTool.add (temp4, temp3);
		    // Draw
		    d3.setDrawColor (Color.LIGHTGRAY);
		    d3.drawVector (temp4);
		}

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
