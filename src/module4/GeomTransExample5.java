// Instructions:
// (1) First multiply each data point (vector) by A. Then multiply
//     the resulting vector by the matrix B.
// (2) Separately, multiply A by B (the product BA) and print the result.

import java.awt.*;
import java.util.*;

public class GeomTransExample5 {

    public static void main (String[] argv)
    {
	// Build a rectangle with 4 line segments.
	ArrayList<LineSegment> rect = makeRectangle ();

	// The matrix A (rotation by 60 degrees)
	double a11 = 0.5;
	double a12 = -Math.sqrt(3)/2.0;
	double a21 = Math.sqrt(3)/2.0;
	double a22 = 0.5;
	double[][] A = {
	    {a11, a12},
	    {a21, a22}
	};

	// The matrix B 
	double b11 = 0.5;
	double b12 = Math.sqrt(3)/2.0;
	double b21 = -Math.sqrt(3)/2.0;
	double b22 = 0.5;
	double[][] B = {
	    {b11, b12},
	    {b21, b22}
	};

	// The product BA
	double[][] C = MatrixTool.matrixMult (B,A);
	double[] v = {-1,-1};
	double [] vm = MatrixTool.matrixVectorMult(C, v);
	System.out.println ("The matrices A, B and C:");
	MatrixTool.print (A);
	MatrixTool.print (B);
	MatrixTool.print (C);
	MatrixTool.print (vm);

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
	    // First apply A to get result vectors
	    L2.start = MatrixTool.matrixVectorMult (A, L.start);   // Apply to endpoint.
	    L2.end = MatrixTool.matrixVectorMult (A, L.end);       // And to other.
	    LineSegment L3 = L2.clone ();
	    // Multiply those result vectors by B.
	    L3.start = MatrixTool.matrixVectorMult (B, L2.start);   // Apply to endpoint.
	    L3.end = MatrixTool.matrixVectorMult (B, L2.end);       // And to other.
	    rect2.add (L3);
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
