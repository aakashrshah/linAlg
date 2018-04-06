// Instructions:
// (1) Compile and execute.
// (2) Examine code in the computeTransform() method.

import edu.aakash.lintool3.MatrixTool;
import edu.gwu.lintool.*;
import java.awt.*;
import java.util.*;

public class CoordChange2D {

    // Eye coords: where the eye is in 3D.
    static double xE=15, yE=12, zE=11;

    // The combined transform.
    static double[][] Trans;

    public static void main (String[] argv)
    {
	// The main work of linear algebra: compute the affine matrices:
	computeTransform ();

	// The rest is drawing ...

	// First, we'll draw axes from origin to 10.
	ArrayList<LinUtil.Line> axes = new ArrayList<LinUtil.Line>();
	axes.add ( make3DLine(0,0,0, 10,0,0) );
	axes.add ( make3DLine(0,0,0, 0,10,0) );
	axes.add ( make3DLine(0,0,0, 0,0,10) );

	// Convert from 2D to 3D
	ArrayList<LinUtil.Line> convertedAxes = convertLines (axes);

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

	// Convert to 2D.
	ArrayList<LinUtil.Line> convertedCuboid = convertLines (cuboid);

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

    static void computeTransform ()
    {
	// Polar:
	double rho = Math.sqrt (xE*xE + yE*yE + zE*zE);
	double theta = Math.atan2 (yE, xE);
	double phi = Math.acos (zE/rho);

	// Step 1. Write the affine matrix that reverse-moves the origin from 
	// (0,0,0) to (xE,yE,zE), i.e., undoes that:
	double[][] T_E = {
	    {1, 0, 0, -xE},
	    {0, 1, 0, -yE},
	    {0, 0, 1, -zE},
	    {0, 0, 0, 1}
	};

	// Step 2: the matrix for a rotation around the z-axis by
	// (90-theta) = (pi/2-theta)
	double rotAngle = (Math.PI/2 - theta);
	double b11 = Math.cos (rotAngle);
	double b12 = -Math.sin (rotAngle);
	double b21 = Math.sin (rotAngle);
	double b22 = Math.cos (rotAngle);
	double[][] R_z1 = {
	    {b11, b12, 0, 0},
	    {b21, b22, 0, 0},
	    {0,   0,   1, 0},
	    {0,   0,   0, 1}
	};

	// Step 3: the matrix for a rotation around the z-axis by 180.
	rotAngle = Math.PI;
	b11 = Math.cos (rotAngle);
	b12 = -Math.sin (rotAngle);
	b21 = Math.sin (rotAngle);
	b22 = Math.cos (rotAngle);
	double[][] R_z2 = {
	    {b11, b12, 0, 0},
	    {b21, b22, 0, 0},
	    {0,   0,   1, 0},
	    {0,   0,   0, 1}
	};

	// Step 4: rotation about x by -90
	rotAngle = -Math.PI/2;
	double c22 = Math.cos (rotAngle);
	double c23 = -Math.sin (rotAngle);
	double c32 = Math.sin (rotAngle);
	double c33 = Math.cos (rotAngle);
	double[][] R_x1 = {
	    {1,   0,   0, 0},
	    {0, c22, c23, 0},
	    {0, c32, c33, 0},
	    {0,   0,   0, 1}
	};

	// Step 5: rotation about x by 90-phi
	rotAngle = Math.PI/2 - phi;
	c22 = Math.cos (rotAngle);
	c23 = -Math.sin (rotAngle);
	c32 = Math.sin (rotAngle);
	c33 = Math.cos (rotAngle);
	double[][] R_x2 = {
	    {1,   0,   0, 0},
	    {0, c22, c23, 0},
	    {0, c32, c33, 0},
	    {0,   0,   0, 1}
	};

	// Now multiply in sequence: Rx2 * Rx1 * Rz2 * Rz1 * TE
	double[][] S1 = MatrixTool.matrixMult (R_z1, T_E);
	double[][] S2 = MatrixTool.matrixMult (R_z2, S1);
	double[][] S3 = MatrixTool.matrixMult (R_x1, S2);
	double[][] S4 = MatrixTool.matrixMult (R_x2, S3);

	Trans = S4;

    }


    static ArrayList<LinUtil.Line> convertLines (ArrayList<LinUtil.Line> inLines)
    {
	ArrayList<LinUtil.Line> affineLines = convertToAffine (inLines);
	ArrayList<LinUtil.Line> affineLines2 = applyMatrixLines (Trans, affineLines);
	return affineLines2;
    }

    static ArrayList<LinUtil.Line> convertToAffine (ArrayList<LinUtil.Line> inLines)
    {
	ArrayList<LinUtil.Line> outLines = new ArrayList<LinUtil.Line> ();
	for (int i=0; i<inLines.size(); i++) {
	    LinUtil.Line L = inLines.get (i);
	    double[] p = new double [L.p.length + 1];
	    double[] q = new double [L.q.length + 1];
	    for (int j=0; j<L.p.length; j++) {
		p[j] = L.p[j];
		q[j] = L.q[j];
	    }
	    p[p.length-1] = 1;
	    q[q.length-1] = 1;
	    outLines.add ( new LinUtil.Line(p,q) );
	}
	return outLines;
    }

    static ArrayList<LinUtil.Line> applyMatrixLines (double[][] A, ArrayList<LinUtil.Line> lines)
    {
	LinToolImpl linImpl = new LinToolImpl ();
	ArrayList<LinUtil.Line> lines2 = new ArrayList<LinUtil.Line> ();
	for (int i=0; i<lines.size(); i++) {
	    LinUtil.Line L = lines.get (i);
	    double[] p = MatrixTool.matrixVectorMult (A, L.p);
	    double[] q = MatrixTool.matrixVectorMult (A, L.q);
	    lines2.add ( new LinUtil.Line(p,q) );
	}
	return lines2;
    }

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
