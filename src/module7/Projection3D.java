
import org.edisonwj.draw3d.*;

import edu.aakash.lintool3.MatrixTool;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class Projection3D extends Application {

    String title = "3D projection example";

    void drawingCommands () 
    {
	double[] w = {5,6,3};

	double[] v1 = {3,1,0};
	double[] v2 = {-1,3,0};
	
	d3.drawVector (w);
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (v1);
	d3.drawVector (v2);

	double alpha1 = MatrixTool.dotProduct(w,v1) / MatrixTool.dotProduct(v1,v1);
	double alpha2 = MatrixTool.dotProduct(w,v2) / MatrixTool.dotProduct(v2,v2);

	double[] y1 = MatrixTool.scalarMult (alpha1, v1);
	double[] y2 = MatrixTool.scalarMult (alpha2, v2);

	d3.setDrawColor (Color.YELLOW);
	d3.drawVector (y1);
	d3.drawVector (y2);

	double[] y = MatrixTool.add(y1,y2);
	d3.setDrawColor (Color.GREEN);
	d3.drawVector (y);
	double[] z = MatrixTool.sub (w,y);
	d3.setDrawColor (Color.RED);
	d3.drawArrow (y[0],y[1],y[2],  w[0],w[1],w[2]);

	// Confirm orthogonality:
	double zDotv1 = MatrixTool.dotProduct (z,v1);
	double zDotv2 = MatrixTool.dotProduct (z,v2);
	System.out.println ("z dot v1 = " + zDotv1);
	System.out.println ("z dot v2 = " + zDotv2);
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
