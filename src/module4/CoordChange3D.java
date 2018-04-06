// Instructions:
// Compile and execute.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class CoordChange3D extends Application {

    String title = "3D to 2D example";

    void drawingCommands () 
    {
	drawCuboid ();
	drawEye ();
    }

    void drawCuboid ()
    {
	// Base.
	d3.drawLine (5,1,8, 5,3,8);
	d3.drawLine (5,3,8, 9,3,8);
	d3.drawLine (9,3,8, 9,1,8);
	d3.drawLine (9,1,8, 5,1,8);

	// Top
	d3.drawLine (5,1,11, 5,3,11);
	d3.drawLine (5,3,11, 9,3,11);
	d3.drawLine (9,3,11, 9,1,11);
	d3.drawLine (9,1,11, 5,1,11);

	// Remaining four lines
	d3.drawLine (5,1,8, 5,1,11);
	d3.drawLine (5,3,8, 5,3,11);
	d3.drawLine (9,3,8, 9,3,11);
	d3.drawLine (9,1,8, 9,1,11);
    }

    void drawEye ()
    {
	d3.drawSphere (15, 12, 11, 0.1);
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
