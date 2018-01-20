// BezierDemo.java
//
// Author: Rahul Simha
// October 2015
//
// Demonstration of Bezier curves.
// What to see in the code: the method computeBezierCurve()
//
// Note: The paintComponent method makes a call to computeBezierCurve.

import edu.gwu.lintool.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.text.*;
import java.io.*;


class BezierPoint extends Point2D.Double {
    // Inherited from Point2D.Double:
    // double x,y;         // For Points, one end of lines,
                           // and top-left of ovals/rectangles.
    Color color;           // Different color during mouse-drag.
}

public class BezierDemo extends JPanel {

    // What we draw: points, lines.
    ArrayList<BezierPoint> points;
    ArrayList<Point2D.Double> linePoints;
    Color pointColor = Color.red;
    Color clickColor = Color.blue;
    Color lineColor = Color.black;
    BasicStroke lineStroke = new BasicStroke(1f);
    double minX=0, maxX=10, minY=0, maxY=10;      // Bounds.
    int numIntervals = 10;                        // # tick marks.
    double pointRadius = 0.1;                     // Size of dot.
    int numControlPoints = 3;
    int numCurvePoints = 100;

    // GUI stuff.
    Dimension D;             // Size of drawing area.
    int inset=60;            // Inset of axes and bounding box.

    // GUI elements.
    JFrame frame;
    JLabel statusLabel = new JLabel ("");
    JTextField controlField = new JTextField (4);
    BezierPoint clickPoint = null;
    static DecimalFormat df = new DecimalFormat();

    // The class that has the linear algebra "magic"
    LinMagic linMagic = new LinMagic ();

    //////////////////////////////////////////////////////////////
    // Computation of curve

    void computeBezierCurve ()
    {
	// What's needed: to take the control points and return
	// a list of curve points. The number of curve points merely
	// determines the aesthetics: more curve points means finer
	// resolution. The number of control points is set by the user
	// in the GUI.
	linePoints = linMagic.computeBezierCurve (points, numCurvePoints);

	// For clarity, let's print the size of each:
	System.out.println ("\nNumber of control points: " + points.size());
	System.out.println ("Number of curve points:   " + linePoints.size());
    }


    public void paintComponent (Graphics g)
    {
	//*********************************************************
	// This is standard paint stuff: draw axes, box etc.
	// No need to read - skip to ****
        super.paintComponent (g);

	Graphics2D g2d = (Graphics2D)g;
	RenderingHints rh = g2d.getRenderingHints();
	rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHints(rh);

        // Background.
        D = this.getSize ();
	g.setColor (Color.WHITE);
	g.fillRect (0,0, D.width, D.height);
        Graphics2D g2 = (Graphics2D) g;
	g2.setStroke (lineStroke);

        // Axes, bounding box.
	g.setColor (Color.gray);
        g.drawLine (inset,D.height-inset, D.width-inset, D.height-inset);
        g.drawLine (D.width-inset,inset, D.width-inset,D.height-inset);
        g.drawLine (inset,inset, inset,D.height-inset);
        g.drawLine (inset,inset, D.width-inset, inset);

        
        double xDelta = (maxX-minX) / numIntervals;

        // X-ticks and labels.
        for (int i=1; i<=numIntervals; i++) {
            double xTickd = i*xDelta;
            int xTick = (int) ( xTickd/(maxX-minX) * (D.width-2*inset));
            g.drawLine (inset+xTick,D.height-inset-5, inset+xTick,D.height-inset+5);
            double x = minX + i*xDelta;
            g.drawString (df.format(x), xTick+inset-5, D.height-inset+20);
        }

        // Y-ticks
        double yDelta = (maxY-minY) / numIntervals;
        for (int i=0; i<numIntervals; i++) {
            int yTick = (i+1) * (int) ((D.height-2*inset) / (double)numIntervals);
            g.drawLine (inset-5, D.height-yTick-inset, inset+5, D.height-yTick-inset);
            double y = minY + (i+1)  * yDelta;
            g.drawString (df.format(y), 1, D.height-yTick-inset);
        }

	// Draw the control points
	for (BezierPoint p: points) {
	    g.setColor (p.color);
	    drawPoint (g, p);
	}

	// Compute and draw the lines.
	if ((points == null) || (points.size() == 0)) {
	    return;
	}

	// end skip
	//*********************************************************
	// Pay attention here:
	
	// The Bezier points have been drawn. What remains is
	// to compute the curve:
	computeBezierCurve ();
	
	// The above method returns a list of points that make up
	// the curve (lots of them).

	// We now draw the curve itself by "joining the dots"
	g.setColor (lineColor);
	Point2D.Double prevPoint = linePoints.get(0);
	for (int i=1; i<linePoints.size(); i++) {
	    Point2D.Double point = linePoints.get(i);
	    drawLine (g, prevPoint, point);
	    prevPoint = point;
	}
    }

    //////////////////////////////////////////////////////////////
    // GUI etc. All of this can be ignored.

    public BezierDemo () 
    {
        points = new ArrayList<BezierPoint>();
        linePoints = new ArrayList<Point2D.Double>();
	controlField.setText ("3");
	buildGUI ();
    }


    void buildGUI ()
    {
	// Need this size to balance axes.
	frame = new JFrame ();
        frame.setSize (520, 690); 
	frame.setTitle ("Bezier demo");
        frame.addWindowListener (
            new WindowAdapter () {
                public void windowClosing (WindowEvent e) 
                {
                    System.exit(0);
                }
            }
        );

	Container cPane = frame.getContentPane ();

	// Build the input/output elements at the bottom.
	JPanel panel = new JPanel ();
	panel.add (new JLabel ("# control points:"));
	panel.add (controlField);
	panel.add (new JLabel ("   "));
        JButton resetButton = new JButton ("Reset");
	resetButton.addActionListener (
            new ActionListener () {
		public void actionPerformed (ActionEvent a)
		{
		    reset ();
		}
	    }
        );
        panel.add (resetButton);
	cPane.add (panel, BorderLayout.SOUTH);

	// Drawing in the center.
        cPane.add (this, BorderLayout.CENTER);

	this.addMouseListener (
	   new MouseAdapter () 
	   {
	       public void mouseClicked (MouseEvent e)
	       {
		   handleMouseClick (e);
	       }
	       public void mousePressed (MouseEvent e)
	       {
		   handleMouseClick (e);
	       }
	       public void mouseReleased (MouseEvent e)
	       {
		   handleMouseReleased (e);
	       }
	   }
        );

	this.addMouseMotionListener (
	   new MouseMotionAdapter () 
	   {
	       public void mouseDragged (MouseEvent e)
	       {
		   handleMouseDragged (e);
	       }
	   }
        );

	frame.setVisible (true);
    }

    void reset ()
    {
	try {
	    numControlPoints = Integer.parseInt (controlField.getText().trim());
	}
	catch (Exception e) {
	    numControlPoints = 3;
	}
	if (numControlPoints < 2) {
	    System.out.println ("Must have at least 2 control points");
	    numControlPoints = 2;
	}

	// Place points equidistant along x=y line. 
	points = new ArrayList<BezierPoint>();
	double xInt = (maxX - minX) / (numControlPoints + 2);
	double yInt = (maxY - minY) / (numControlPoints + 2);
	for (int n=0; n<numControlPoints; n++) {
	    BezierPoint b = new BezierPoint ();
	    b.x = minX + xInt + n*xInt;
	    b.y = minY + yInt + n*yInt;
	    b.color = pointColor;
	    points.add (b);
	}
	this.repaint ();
    }

    void handleMouseClick (MouseEvent e)
    {
	// See if a point has been clicked.
	if (clickPoint != null) {
	    clickPoint.color = pointColor;
	}
	clickPoint = null;
	for (BezierPoint p: points) {
	    double x = javaToRealX (e.getX());
	    double y = javaToRealY (e.getY());
	    double dist = Math.sqrt( (p.x-x)*(p.x-x) + (p.y-y)*(p.y-y) );
	    if (dist <= pointRadius) {
		clickPoint = p;
		clickPoint.color = clickColor;
	    }
	}
	this.repaint ();
    }

    void handleMouseDragged (MouseEvent e)
    {
	double x = javaToRealX (e.getX());
	double y = javaToRealY (e.getY());
	if (clickPoint != null) {
	    clickPoint.x = x;
	    clickPoint.y = y;
	}
	this.repaint ();
    }


    void handleMouseReleased (MouseEvent e)
    {
	if (clickPoint != null) {
	    clickPoint.color = pointColor;
	    clickPoint = null;
	}
	this.repaint ();
    }

    int realToJavaX (double x)
    {
	int xInt = inset + (int) ( (x-minX)/(maxX-minX) * (D.width-2*inset) );
	return xInt;
    }

    double javaToRealX (int xInt)
    {
	double realX = minX + (maxX-minX)*(xInt-inset) / (D.width-2.0*inset);
	return realX;
    }

    int realToJavaY (double y)
    {
	int yInt = inset + (int) ((y-minY) / (maxY-minY) * (D.height-2.0*inset) );
	return (D.height - yInt);
    }

    double javaToRealY (int yInt)
    {
	double yRev = D.height - yInt;
	double realY = minY + (maxY-minY)*(yRev-inset) / (D.height-2.0*inset);
	return realY;
    }

    void drawPoint (Graphics g, BezierPoint p) 
    {
	if (p == null) {
	    return;
	}
	int x = realToJavaX (p.x);
	int y = realToJavaY (p.y);
	int r = realToJavaX(pointRadius) - realToJavaX(0);
	g.fillOval (x-r, y-r, 2*r, 2*r);
    }

    void drawLine (Graphics g, Point2D.Double p, Point2D.Double q)
    {
	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke (lineStroke);
	int x1 = realToJavaX (p.x);
	int y1 = realToJavaY (p.y);
	int x2 = realToJavaX (q.x);
	int y2 = realToJavaY (q.y);
	g.drawLine (x1, y1, x2, y2);
    }


    public static void main (String[] argv)
    {
	BezierDemo bezier = new BezierDemo ();
    }

}
