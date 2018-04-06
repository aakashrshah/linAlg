// Instructions:
// Insert the values of the three columns

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class EquationExample3D extends Application {

    String title = "Equation example";

    void drawingCommands () 
    {
	// The RHS b of Ax=b:
	double[] b = {5,6,3};
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (b);

	// INSERT YOUR CODE HERE: the three columns as vectors.
	double[] c1 = {};
	double[] c2 = {};
	double[] c3 = {};

	// Draw the vectors.
	d3.drawVector (c1);
	d3.drawVector (c2);
	d3.drawVector (c3);
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
