// Function.java
//
// Rahul Simha
// Spring 2008
// Updated 2015

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.text.*;

public class Function {

    String name = "Func";
    String xLabel = "x";
    Vector<Point2D.Double> points = new Vector<Point2D.Double>();

    double minX, maxX, minY, maxY;

    public Function (String name)
    {
	if (name != null)
	    this.name = name;
    }

    public Function (String name, String xLabel)
    {
	if (name != null)
	    this.name = name;
	if (xLabel != null) 
	    this.xLabel = xLabel;
    }

    public String getName ()
    {
	return name;
    }

    public void add (double x, double y)
    {
	// Find right place for it.
	int k = points.size();
	for (int i=0; i<points.size(); i++) {
	    Point2D.Double p = points.get(i);
	    if (p.x == x) {
		// Duplicate: error
		System.out.println ("ERROR in Function.add(x=" + x + "," + y + "): x already exists with p.y=" + p.y);
		return;
	    }
	    if (p.x > x) {
		// Insert at i.
		k = i;
                break;
	    }
	}
	points.add (k, new Point2D.Double(x,y));

        // Update max and min.
        if (x < minX) {
            minX = x;
        }
        if (x > maxX) {
            maxX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (y > maxY) {
            maxY = y;
        }
    }

    public double get (double x)
    {
	for (int i=0; i<points.size(); i++) {
	    Point2D.Double p = points.get(i);
	    if (p.x == x) {
		return p.y;
	    }
	    else if (p.x > x) {
		if (i == 0) {
		    // Already less than leftmost.
		    return p.y;
		}
		else {
		    // It's between p.x and previous p[i-1].x
		    Point2D.Double q = points.get(i-1);
		    // Now interpolate.
		    double y = q.y + (x - q.x) * (p.y - q.y) / (p.x - q.x);
		    // Note: divide by zero can't happen because we
		    // ensure x values can't be duplicated.
		    return y;
		}
	    }
	}

	Point2D.Double p = points.get (points.size()-1);
	return p.y;
    }

    public int getNumValues ()
    {
	return points.size();
    }

    public double maxX ()
    {
	return maxX;
    }

    public double minX ()
    {
	return minX;
    }

    public double maxY ()
    {
	return maxY;
    }

    public double minY ()
    {
	return minY;
    }


    public double distance (Function F, int numPoints)
    {
        // First compute the common domain.
        double leftX = Math.max (minX, F.minX());
        double rightX = Math.min (maxX, F.maxX());
        if (leftX >= rightX) {
            // Not possible.
            return 0;
        }
        
        double interval = (rightX - leftX) / numPoints;
        double sum = 0;
        for (double x=leftX; x<=rightX; x+=interval) {
            sum = sum + interval*Math.abs (get(x) - F.get(x));
        }
        return sum;
    }
    



    public String toString ()
    {
        // First, set format string for each of x and y.
        if ((points == null) || (points.size() == 0)) {
            return "Function " + name + " has no points";
        }
        
        String str = name + " (X:[" + format(minX) + "," + format(maxX) + "]   Y:["
                     + format(minY) + "," + format(maxY) + "])\n";
        
	for (int i=0; i<points.size(); i++) {
	    Point2D.Double p = points.get(i);
            str += "  " + format(p.x) + "   " + format(p.y) + "\n";
        }

        return str;
    }
    
    String format (double x)
    {
        if (x == 0) {
            return "0.0";
        }
        
        double y = Math.abs(x);
        int k = (int) y;
        String s = "#.##";
        int count = 0;
        while (k < 1) {
            s += "#";
            y = y  * 10;
            k = (int) y;
            count ++;
        }
        DecimalFormat df = null;
        if (count > 8) {
            df = new DecimalFormat ("#.##E0");
        }
        else {
            df = new DecimalFormat (s);
        }
        return df.format (x);
    }
    

    public void show ()
    {
	SimplePlotPanel.makePlotFrame (points, name, 600, 480);
    }

    public static void show (Function ... fSet)
    {
        ArrayList<Vector> pointSets = new ArrayList<Vector> ();
        ArrayList<String> names = new ArrayList<String> ();
        for (Function F: fSet) {
            pointSets.add (F.points);
            names.add (F.name);
        }
	SimplePlotPanel.makePlotFrame (pointSets, names, fSet[0].xLabel, 600, 480);
    }

    public static void show (ArrayList<Function> fSet)
    {
        ArrayList<Vector> pointSets = new ArrayList<Vector> ();
        ArrayList<String> names = new ArrayList<String> ();
        for (Function F: fSet) {
            pointSets.add (F.points);
            names.add (F.name);
        }
	SimplePlotPanel.makePlotFrame (pointSets, names, fSet.get(0).xLabel, 600, 480);
    }



    public static void main (String[] argv)
    {
        //test1();
        test2 ();
    }

    static void test1 ()
    {
	Function F = new Function ("Test");
	F.add (1, 2);
	F.add (2, 4);
	F.add (3, 6);
	System.out.println ("F(1.5)=" + F.get(1.5) + "  F(2.2)=" + F.get(2.2) + "  F(3.0)=" + F.get(3.0) + "  F(5)=" + F.get(5.0));
	F.show ();
    }
    
    static void test2 ()
    {
        Function F = new Function ("Test1");
        for (double x=0; x<=10; x+=0.1) {
            F.add (x, 3*x+5);
        }
        
        Function G = new Function ("Test2");
        for (double x=0; x<=10; x+=0.1) {
            G.add (x, 4*x+5);
        }


        Function zero = new Function ("Zero");
        for (double x=0; x<=10; x+=0.1) {
            zero.add (x, 0);
        }

        double d = F.distance (G, 100);
        System.out.println ("dist=" + d);

        double d2 = F.distance (G, 1000);
        System.out.println ("dist2=" + d2);

        double d3 = F.distance (zero, 1000);
        System.out.println ("dist3=" + d3);
    }
    

}
