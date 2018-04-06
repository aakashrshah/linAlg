
import org.edisonwj.draw3d.*;

import edu.aakash.lintool3.MatrixTool;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Affine3DExample extends Application {

    String title = "Affine example";

    void drawingCommands () 
    {
	// Affine extension of 2D rotation
	double theta = Math.PI/3.0;
	double a11 = Math.cos (theta);
	double a12 = -Math.sin (theta);
	double a21 = Math.sin (theta);
	double a22 = Math.cos (theta);
	double[][] A = {
	    {a11, a12, 0},
	    {a21, a22, 0},
	    {0,   0,   1}
	};

	double[] u = {4,3,1};
	double[] v = MatrixTool.matrixVectorMult (A, u);

	MatrixTool.print (v);

	// Draw.
	d3.drawVector (u);
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (v);
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
