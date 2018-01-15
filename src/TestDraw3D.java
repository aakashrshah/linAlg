import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class TestDraw3D extends Application {

    Draw3D d3;
    String title = "Test";

    void drawingCommands () 
    {
	// Do all your 3D drawing in here.

	// Begin-drawing-commands.

	d3.setDrawColor (Color.BLUE);
	d3.drawVector (1,3,1);
	d3.drawVector (4,1,0);
	d3.drawVector (3,2,6);

	// End-drawing-commands.
    }

    ///////////////////////////////////////////////////////////////
    // No need to read further ...

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
