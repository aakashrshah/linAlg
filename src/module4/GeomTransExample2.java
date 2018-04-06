// Instructions:
// Change the A matrix to perform reflection about the origin.

import java.awt.*;
import java.util.*;

public class GeomTransExample2 {

    public static void main (String[] argv)
    {
	// Build a rectangle with 4 line segments.
	ArrayList<LineSegment> rect = makeRectangle ();

	// This is the matrix that will multiply to transform vectors.
	double[][] B = {
			{-1, 0},
		    {0, -1}
	};

	// Draw original rectangle.
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	for (LineSegment L: rect) {
	    DrawTool.setLineColor (L.color);
	    DrawTool.drawLine (L.start[0], L.start[1], L.end[0], L.end[1]);
	}

	// Apply matrix to each point (treated as a vector)
	ArrayList<LineSegment> rect2 = new ArrayList<LineSegment>();
	for (LineSegment L: rect) {
	    LineSegment L2 = L.clone ();
	    L2.start = MatrixTool.matrixVectorMult (B, L.start);   // Apply to endpoint.
	    L2.end = MatrixTool.matrixVectorMult (B, L.end);       // And to other.
	    rect2.add (L2);
	}

	// Draw.
	for (LineSegment L: rect2) {
	    DrawTool.setLineColor (L.color);
	    DrawTool.drawLine (L.start[0], L.start[1], L.end[0], L.end[1]);
	}

    }

    
    static ArrayList<LineSegment> makeRectangle ()
    {
	ArrayList<LineSegment> rect = new ArrayList<LineSegment>();
	LineSegment L = new LineSegment (2,3, 5,3, "black");
	rect.add (L);
	L = new LineSegment (5,3, 5,5, "blue");
	rect.add (L);
	L = new LineSegment (5,5, 2,5, "green");
	rect.add (L);
	L = new LineSegment (2,5, 2,3, "red");
	rect.add (L);
	return rect;
    }

}


class LineSegment {
    double[] start = new double [2];
    double[] end = new double [2];
    String color = "blue";

    public LineSegment (double x1, double y1, double x2, double y2, String color)
    {
	start[0] = x1;  start[1] = y1;
	end[0] = x2;    end[1] = y2;
	this.color = color;
    }

    public LineSegment clone ()
    {
	return new LineSegment (start[0], start[1], end[0], end[1], color);
    }
}
