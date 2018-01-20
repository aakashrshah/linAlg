// Coordinates.java
//
// Author: Rahul Simha
// Nov 29, 2015
//
// To demonstrate drawing of 3D object in 2D.
// Needs: DrawTool.java in same directory.
// What to see in the code: all of the code in main()
//
// Starting assumptions:
// (1) x,y,z axes of world coordinates with z going up, 
//     x left and out, y rightwards (righthand rule)
// (2) The eye is located at E=(xE,yE,zE) in world-coords.
//     The line from (0,0,0) to (xE,yE,0) is at an angle
//     of theta from x-axis (can be calculated). The line
//     from (0,0,0) to (xE,yE,zE) is at angle phi to the z-axis.
// (3) The (imaginary) screen will be between E and the world
//     origin. It's the 2D drawing on this screen that we want
//     to create.

import edu.gwu.lintool.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Coordinates {

    // The class that has the linear algebra "magic"
    static LinMagic linMagic = new LinMagic ();

    public static void main (String[] argv)
    {
	// Start with Eye coords: where the eye is in 3D.
	double xE=15, yE=12, zE=11;
	linMagic.setEyeCoords (xE, yE, zE);

	// First, we'll draw axes from origin to 10.
	ArrayList<LinUtil.Line> axes = new ArrayList<LinUtil.Line>();
	axes.add ( make3DLine(0,0,0, 10,0,0) );
	axes.add ( make3DLine(0,0,0, 0,10,0) );
	axes.add ( make3DLine(0,0,0, 0,0,10) );

	// Let linear algebra convert to 2D:
	ArrayList<LinUtil.Line> convertedAxes = linMagic.convertLines (axes);

	// Now draw the axes.
	DrawTool.display ();
	DrawTool.setXYRange (-20,20, -20,20);
	DrawTool.drawMiddleAxes (false);
	DrawTool.setLineColor ("gray");
	for (LinUtil.Line L: convertedAxes) {
	    DrawTool.drawLine (L.p[0],L.p[1], L.q[0], L.q[1]);
	}

	// We'll draw small dots at the ends of the axes to recognize
	// which one is x, which is y etc.
	LinUtil.Line L = convertedAxes.get(0);
	DrawTool.setPointColor ("red");
	DrawTool.drawPoint (L.q[0],L.q[1]);
	L = convertedAxes.get(1);
	DrawTool.setPointColor ("green");
	DrawTool.drawPoint (L.q[0],L.q[1]);
	L = convertedAxes.get(2);
	DrawTool.setPointColor ("blue");
	DrawTool.drawPoint (L.q[0],L.q[1]);

	// Take a cuboid of eight 3D points:
	//   (5,1,8),  (5,3,8),  (9,3,8),  (9,1,8)
	//   (5,1,11), (5,3,11), (9,3,11), (9,1,11)
	ArrayList<LinUtil.Line> cuboid = makeCuboid ();

	// Note: cuboid is axis-parallel: 4 units along x,
	// 2 units along y, 3 along z. That is, a "4 x 2 x 3" cuboid
	// with lowest corner at (5,1,8)

	// Use linear algebra to convert to 2D:
	ArrayList<LinUtil.Line> convertedCuboid = linMagic.convertLines (cuboid);

	// Draw.
	for (LinUtil.Line LL: convertedCuboid) {
	    DrawTool.drawLine (LL.p[0],LL.p[1], LL.q[0], LL.q[1]);
	}

	// We'll draw dots on two opposite corners.
	L = convertedCuboid.get(0);
	DrawTool.setPointColor ("magenta");
	DrawTool.drawPoint (L.p[0],L.p[1]);
	L = convertedCuboid.get(7);
	DrawTool.setPointColor ("cyan");
	DrawTool.drawPoint (L.p[0],L.p[1]);
    }

    // No need to read below this line.
    ///////////////////////////////////////////////////////////////

    static LinUtil.Line make3DLine (double x1, double y1, double z1, 
				    double x2, double y2, double z2)
    {
	double[] p = {x1, y1, z1};
	double[] q = {x2, y2, z2};
	return new LinUtil.Line (p,q);
    }

    static ArrayList<LinUtil.Line> makeCuboid ()
    {
	ArrayList<LinUtil.Line> lines = new ArrayList<LinUtil.Line> ();
	double[] p1 = {5,1,8};
	double[] p2 = {5,3,8};
	double[] p3 = {9,3,8};
	double[] p4 = {9,1,8};

	double[] p5 = {5,1,11};
	double[] p6 = {5,3,11};
	double[] p7 = {9,3,11};
	double[] p8 = {9,1,11};

	// Base.
	lines.add (new LinUtil.Line (p1,p2));
	lines.add (new LinUtil.Line (p2,p3));
	lines.add (new LinUtil.Line (p3,p4));
	lines.add (new LinUtil.Line (p4,p1));

	// Top
	lines.add (new LinUtil.Line (p5,p6));
	lines.add (new LinUtil.Line (p6,p7));
	lines.add (new LinUtil.Line (p7,p8));
	lines.add (new LinUtil.Line (p8,p5));

	// Remaining four lines
	lines.add (new LinUtil.Line (p1,p5));
	lines.add (new LinUtil.Line (p2,p6));
	lines.add (new LinUtil.Line (p3,p7));
	lines.add (new LinUtil.Line (p4,p8));

	return lines;
    }


}
