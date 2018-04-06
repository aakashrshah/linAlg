// DrawTool.java
//
// Author: Rahul Simha (Jan 2011)
// Modified: James Marshall (March 2011)
// Modified: Rahul Simha (November 2014)
//
// A simple widget for drawing, animation and text input.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.text.*;
import java.io.*;


class DrawObject {
    double x,y;            // For Points, one end of lines,
                           // and top-left of ovals/rectangles.
    double x2,y2;          // For the other end of lines, arrows.
    double width,height;   // For ovals/rectangles.
    int diameter = 6;      // For points.
    Color color;           // Desired color.
    boolean isArrow = false;
    String str;            // For labels.
    int[][][] pixels;      // Image.
    int startX, startY;    // Bottom-left of image.
    int sequenceNum = 0;   // For sequenced displays (not used by students).
    int scribbleNum = 0;
    int scribbleX, scribbleY;
    double a, b, c;        // For the line ax+by+c=0
    BasicStroke drawStroke = new BasicStroke (1.0f);
}

public class DrawTool extends JPanel {

    // What we draw: points, lines, ovals and rectangles.
    static java.util.List<DrawObject> points, lines, ovals, rectangles, images, labels, scribbles, eqnLines;

    // Animated versions: these will clear between successive frames.
    static java.util.List<DrawObject> animPoints, animLines, animOvals, animRectangles, animLabels;

    // One can change the color. These are the defaults.
    static Color pointColor = Color.red;
    static Color lineColor = Color.black;
    static Color lineEqnColor = Color.magenta;
    static Color arrowColor = Color.darkGray;
    static Color ovalColor = Color.blue;
    static Color rectangleColor = Color.green;
    static Color backgroundColor = Color.white;
    static Color labelColor = Color.lightGray;
    //static Color scribbleColor = Color.red;
    static Color scribbleColor = new Color (0,76,153);
    // dkblue=(0,76,153), reddish-brown=(153,0,0)

    static BasicStroke drawStroke = new BasicStroke(1f);
    static BasicStroke lineStroke = new BasicStroke(1f);
    static float dash1[] = {8.0f};
    static BasicStroke dashedStroke =
        new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f);

    static double minX=0, maxX=10, minY=0, maxY=10;      // Bounds.
    static int numIntervals = 10;                        // # tick marks.
    static int pointDiameter = 6;                        // Size of dot.

    // To prettify text:
    static DecimalFormat df = new DecimalFormat();
    static Font plusFont = new Font ("Serif", Font.PLAIN, 25);
    static Font minusFont = new Font ("Serif", Font.PLAIN, 40);

    // GUI stuff.
    static JPanel drawArea = new DrawTool ();
    static Dimension D;             // Size of drawing area.
    static int inset=60;            // Inset of axes and bounding box.
    static boolean drawMiddleAxes = false;
    static boolean sequencingOn = false;

    // GUI elements.
    static JFrame frame;
    static JLabel statusLabel = new JLabel (" ");
    static JLabel outputLabel = new JLabel (" ");
    static JTextField inputField = new JTextField (40);
    static int currentSequenceNum = 0;
    static int currentSequenceNumDisplay = 0;
    static int currentScribbleNum = 0;

    // Very light blue:
    static Color inputPanelColor = new Color (240, 240, 255);

    // Has input occurred? That is, has "Enter" been clicked?
    static boolean hasEntered = false;

    // Animation stuff. The current object (in each category).
    static boolean animationMode = false;



    // Static initializer.

    static
    {
        points = Collections.synchronizedList (new ArrayList<DrawObject>());
        lines = Collections.synchronizedList (new ArrayList<DrawObject>());
        ovals = Collections.synchronizedList (new ArrayList<DrawObject>());
        rectangles = Collections.synchronizedList (new ArrayList<DrawObject>());
        images = Collections.synchronizedList (new ArrayList<DrawObject>());
        labels = Collections.synchronizedList (new ArrayList<DrawObject>());
        scribbles = Collections.synchronizedList (new ArrayList<DrawObject>());
        eqnLines = Collections.synchronizedList (new ArrayList<DrawObject>());

        animPoints = Collections.synchronizedList (new ArrayList<DrawObject>());
        animLines = Collections.synchronizedList (new ArrayList<DrawObject>());
        animOvals = Collections.synchronizedList (new ArrayList<DrawObject>());
        animRectangles = Collections.synchronizedList (new ArrayList<DrawObject>());
        animLabels = Collections.synchronizedList (new ArrayList<DrawObject>());

	df.setMaximumFractionDigits (4);
    }

    ////////////////////////////////////////////////////////////////
    //
    // The public methods.

    public static void setXYRange (double minXvalue, double maxXvalue, double minYvalue, double maxYvalue)
    {
	minX = minXvalue;   maxX = maxXvalue;
	minY = minYvalue;   maxY = maxYvalue;
    }

    public static void drawMiddleAxes (boolean drawThem)
    {
	drawMiddleAxes = drawThem;
    }

    public static void setSequencingOn ()
    {
	sequencingOn = true;
	currentSequenceNum = 0;
    }

    public static void incrSequence ()
    {
	currentSequenceNum ++;
    }

    public static void display ()
    {
	// Store reference to frame for use in dialogs.
        frame = new JFrame ();
	buildGUI ();
        frame.setVisible (true);
    }


    public static void drawPoint (double x, double y)
    {
	DrawObject p = new DrawObject ();
	p.color = pointColor;
	p.x = x;  p.y = y;
	p.diameter = pointDiameter;
	p.sequenceNum = currentSequenceNum;
	if (animationMode) {
	    synchronized(animPoints) { animPoints.add (p); }
	}
	else {
	    synchronized(points) { points.add (p); }
	}
	drawArea.repaint ();
    }

    public static void setDashedLines (boolean set)
    {
	if (set) {
	    drawStroke = dashedStroke;
	}
	else {
	    drawStroke = lineStroke;
	}
    }

    public static void drawLine (double x1, double y1, double x2, double y2)
    {
	drawLine (x1, y1, x2, y2, false);
    }

    public static void drawArrow (double x1, double y1, double x2, double y2)
    {
	drawLine (x1, y1, x2, y2, true);
    }

    public static void drawVector (double x, double y)
    {
	drawLine (0, 0, x, y, true);
    }

    public static void drawVector (double[] x)
    {
	drawVector (x[0], x[1]);
    }

    public static void drawLineFromEquation (double a, double b, double c)
    {
	// Draw the equation ax+by+c=0 in the available range.
	DrawObject L = new DrawObject ();
	L.color = lineEqnColor;
	L.a = a;   L.b = b;  L.c = c;
	L.sequenceNum = currentSequenceNum;
	L.drawStroke = drawStroke;
	synchronized(eqnLines) { eqnLines.add (L); }
	drawArea.repaint ();
    }

    public static void drawLine (double x1, double y1, double x2, double y2, boolean isArrow)
    {
	DrawObject L = new DrawObject ();
	L.color = lineColor;
	L.x = x1;  L.y = y1;  L.x2 = x2;  L.y2 = y2;
	if (isArrow) {
	    L.color = arrowColor;
	    L.isArrow = true;
	}
	L.sequenceNum = currentSequenceNum;
	L.drawStroke = drawStroke;
	if (animationMode) {
	    synchronized(animLines) { animLines.add (L); }
	}
	else {
	    synchronized(lines) { lines.add (L); }
	}
	drawArea.repaint ();
    }

    public static void drawOval (double x1, double y1, double width, double height)
    {
	DrawObject R = new DrawObject ();
	R.color = ovalColor;
	R.x = x1;  R.y = y1;  R.width = width;  R.height = height;
	R.sequenceNum = currentSequenceNum;
	R.drawStroke = drawStroke;
	if (animationMode) {
	    synchronized(animOvals) { animOvals.add (R); }
	}
	else {
	    synchronized(ovals) { ovals.add (R); }
	}
	drawArea.repaint ();
    }


    public static void drawRectangle (double x1, double y1, double width, double height)
    {
	DrawObject R = new DrawObject ();
	R.color = rectangleColor;
	R.x = x1;  R.y = y1;  R.width = width;  R.height = height;
	R.sequenceNum = currentSequenceNum;
	R.drawStroke = drawStroke;
	if (animationMode) {
	    synchronized(animRectangles) { animRectangles.add (R); }
	}
	else {
	    synchronized(rectangles) { rectangles.add (R); }
	}
	drawArea.repaint ();
    }

    public static void drawImage (int[][][] pixels, int startX, int startY)
    {
	// Key idea: draw a bunch (lots of rectangles) with the appropriate color
	DrawObject R = new DrawObject ();
	R.pixels = pixels;
	R.startX = startX;
	R.startY = startY;
	R.sequenceNum = currentSequenceNum;
	images.add (R);
	// Rescale if needed.
	int leftX = startX;
	int rightX = startX + pixels.length;
	int lowY = startY;
	int highY = startY + pixels[0].length;
	if (minX > leftX) {
	    minX = leftX;
	}
	if (maxX < rightX) {
	    maxX = rightX;
	}
	if (minY > lowY) {
	    minY = lowY;
	}
	if (maxY < highY) {
	    maxY = highY;
	}
	drawArea.repaint ();
    }

    public static void drawLabel (double x, double y, String str)
    {
	DrawObject L = new DrawObject ();
	L.color = labelColor;
	L.x = x;  L.y = y;  
	L.str = str;
	L.sequenceNum = currentSequenceNum;
	if (animationMode) {
	    synchronized(animLabels) { animLabels.add (L); }
	}
	else {
	    synchronized(labels) { labels.add (L); }
	}
	drawArea.repaint ();
    }

    public static void setPointColor (String colorString)
    {
	pointColor = toColor (colorString, pointColor);
    }


    public static void setLineColor (String colorString)
    {
	lineColor = toColor (colorString, lineColor);
    }

    public static void setArrowColor (String colorString)
    {
	arrowColor = toColor (colorString, arrowColor);
    }


    public static void setOvalColor (String colorString)
    {
	ovalColor = toColor (colorString, ovalColor);
    }


    public static void setRectangleColor (String colorString)
    {
	rectangleColor = toColor (colorString, rectangleColor);
    }


    public static void setBackgroundColor (String colorString)
    {
	backgroundColor = toColor (colorString, backgroundColor);
    }


    public static void setPointSize (int diameter)
    {
	if (diameter > 0) {
	    pointDiameter = diameter;
	}
    }


    public static void writePrompt (String s)
    {
	if (s == null) {
	    return;
	}
	outputLabel.setForeground (Color.black);
	outputLabel.setText ("  " + s);
	hasEntered = false;
    }


    public static void writeTopString (String s)
    {
	if (s == null) {
	    return;
	}
	statusLabel.setForeground (Color.black);
	statusLabel.setText ("  " + s);
    }

    public static void writeTopValue (double v)
    {
	writeTopString ("" + df.format(v));
    }

    public static String getInputString ()
    {
	return waitForInputString();
    }


    public static int getInputInteger ()
    {
	try {
	    String inputString = waitForInputString ();
	    int i = Integer.parseInt (inputString.trim());
	    return i;
	}
	catch (Exception e) {
	    JOptionPane.showMessageDialog (frame, null, "Improper integer", JOptionPane.ERROR_MESSAGE);
	    return 0;
	}
    }


    public static double getInputDouble ()
    {
	try {
	    String inputString = waitForInputString ();
	    double d = Double.parseDouble (inputString.trim());
	    return d;
	}
	catch (Exception e) {
	    JOptionPane.showMessageDialog (frame, null, "Improper double", JOptionPane.ERROR_MESSAGE);
	    return 0;
	}
    }


    public static void startAnimationMode ()
    {
	animationMode = true;
    }


    public static void endAnimationMode ()
    {
	animationMode = false;
    }


    public static void animationPause (int pauseTime)
    {
	if ((pauseTime < 1) || (pauseTime > 1000)) {
	    pauseTime = 100;
	}
	try {
	    Thread.sleep (pauseTime);
	    synchronized(animPoints) { animPoints.clear (); }
	    synchronized(animLines) { animLines.clear (); }
	    synchronized(animOvals) { animOvals.clear (); }
	    synchronized(animRectangles) { animRectangles.clear (); }
	}
	catch (InterruptedException e) {
	}
    }


    ////////////////////////////////////////////////////////////////
    //
    // GUI and drawing

    static Color toColor (String colorString, Color defaultColor)
    {
	// Convert standard colors.
	if (colorString.equalsIgnoreCase ("black")) {
	    return Color.black;
	}
	else if (colorString.equalsIgnoreCase ("blue")) {
	    return Color.blue;
	}
	else if (colorString.equalsIgnoreCase ("cyan")) {
	    return Color.cyan;
	}
	else if (colorString.equalsIgnoreCase ("dark gray")) {
	    return Color.darkGray;
	}
	else if (colorString.equalsIgnoreCase ("gray")) {
	    return Color.gray;
	}
	else if (colorString.equalsIgnoreCase ("green")) {
	    return Color.green;
	}
	else if (colorString.equalsIgnoreCase ("light gray")) {
	    return Color.lightGray;
	}
	else if (colorString.equalsIgnoreCase ("magenta")) {
	    return Color.magenta;
	}
	else if (colorString.equalsIgnoreCase ("orange")) {
	    return Color.orange;
	}
	else if (colorString.equalsIgnoreCase ("pink")) {
	    return Color.pink;
	}
	else if (colorString.equalsIgnoreCase ("red")) {
	    return Color.red;
	}
	else if (colorString.equalsIgnoreCase ("white")) {
	    return Color.white;
	}
	else if (colorString.equalsIgnoreCase ("yellow")) {
	    return Color.yellow;
	}
	else {
	    return defaultColor;
	}

    }

    static String waitForInputString ()
    {
	try {
	    while (! hasEntered) {
		Thread.sleep (100);
	    }
	    return inputField.getText();
	}
	catch (Exception e) {
	    return "";
	}
    }


    static void buildGUI ()
    {
	// Need this size to balance axes.
        frame.setSize (520, 690); 
	frame.setTitle ("DrawTool");
        frame.addWindowListener (
            new WindowAdapter () {
                public void windowClosing (WindowEvent e) 
                {
                    System.exit(0);
                }
            }
        );

	Container cPane = frame.getContentPane ();

	// Status label on top. Unused for now.
	statusLabel.setOpaque (true);
	statusLabel.setBackground (Color.white);
	cPane.add (statusLabel, BorderLayout.NORTH);

	// Build the input/output elements at the bottom.
	JPanel panel = new JPanel ();
	panel.setBorder (BorderFactory.createLineBorder (Color.black));
	panel.setBackground (inputPanelColor);
	panel.setLayout (new GridLayout (2,1));
	panel.add (outputLabel);
	JPanel bottomPanel = new JPanel ();
	bottomPanel.setBackground (inputPanelColor);
	bottomPanel.add (inputField);
	bottomPanel.add (new JLabel ("   "));
	JButton enterButton = new JButton ("Enter");
	enterButton.addActionListener (
            new ActionListener () {
		public void actionPerformed (ActionEvent a)
		{
		    hasEntered = true;
		}
	    }
        );
	bottomPanel.add (enterButton);
	panel.add (bottomPanel);
	if (! sequencingOn) {
	    cPane.add (panel, BorderLayout.SOUTH);
	}

	// Drawing in the center.
	drawArea = new DrawTool ();
	if (sequencingOn) {
	    frame.addKeyListener (
   	       new KeyAdapter () {
		   public void keyTyped (KeyEvent e)
		   {
		       handleKeyTyped (e);
		   }
	       }
            );
	}
        cPane.add (drawArea, BorderLayout.CENTER);

	drawArea.addMouseListener (
	   new MouseAdapter () 
	   {
	       public void mouseClicked (MouseEvent e)
	       {
		   handleMouseClick (e);
	       }
	       public void mouseReleased (MouseEvent e)
	       {
		   handleMouseReleased (e);
	       }
	   }
        );

	drawArea.addMouseMotionListener (
	   new MouseMotionAdapter () 
	   {
	       public void mouseDragged (MouseEvent e)
	       {
		   handleMouseDragged (e);
	       }
	   }
        );

    }

    static void handleMouseClick (MouseEvent e)
    {
	double midX = (maxX + minX) / 2;
	double midXDist = midX - minX;
	double midY = (maxY + minY) / 2;
	double midYDist = midY - minY;
	// See if any of the navigation icons were under the mouse.
	if (withinBounds (e.getX(),e.getY(), D.width-20, D.width-15, 10, 20)) {
	    // Plus.
	    setXYRange (minX, midX, minY, midY);
	}
	else if (withinBounds (e.getX(),e.getY(), D.width-20, D.width-15, 40, 50)) {
	    // Minus.
	    setXYRange (minX, 2*maxX, minY, 2*maxY);
	}
	else if (withinBounds (e.getX(),e.getY(), D.width-75, D.width-65, 0, 20)) {
	    //
	    setXYRange (minX, maxX, minY+midYDist, maxY+midYDist);
	}
	else if (withinBounds (e.getX(),e.getY(), D.width-75, D.width-65, 30, 50)) {
	    //
	    setXYRange (minX, maxX, minY-midYDist, maxY-midYDist);
	}
	else if (withinBounds (e.getX(),e.getY(), D.width-65, D.width-45, 20, 30)) {
	    //
	    setXYRange (minX+midXDist, maxX+midXDist, minY, maxY);
	}
	else if (withinBounds (e.getX(),e.getY(), D.width-95, D.width-75, 20, 30)) {
	    //
	    setXYRange (minX-midXDist, maxX-midXDist, minY, maxY);
	}
	drawArea.repaint ();
    }

    static boolean withinBounds (int x, int y, int xLow, int xHigh, int yLow, int yHigh)
    {
	return ((xLow <= x) && (x <= xHigh) && (yLow <= y) && (y <= yHigh));
    }

    static void handleKeyTyped (KeyEvent e)
    {
	if (e.getKeyChar() == ' ') {
	    currentSequenceNumDisplay ++;
	    scribbles = Collections.synchronizedList (new ArrayList<DrawObject>());
	    currentScribbleNum = 0;
	}
	drawArea.repaint ();
    }

    static void handleMouseReleased (MouseEvent e)
    {
	currentScribbleNum ++;
    }

    static void handleMouseDragged (MouseEvent e)
    {
	DrawObject L = new DrawObject ();
	L.scribbleX = e.getX();  L.scribbleY = e.getY();
	L.scribbleNum = currentScribbleNum;
	scribbles.add (L);
	drawArea.repaint ();
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

	Graphics2D g2d = (Graphics2D)g;
	RenderingHints rh = g2d.getRenderingHints();
	rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHints(rh);

        // Background.
        D = this.getSize ();
	g.setColor (backgroundColor);
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

	// Zoom+move
	Font savedFont = g.getFont();
	g.setFont (plusFont);
	g.drawString ("+", D.width-25, 20);  
	g.setFont (minusFont);
	g.drawString ("-", D.width-25, 50);
	drawArrow (g2d, D.width-70, 20, D.width-70, 0, 1.0f, lineStroke);   // Up
	drawArrow (g2d, D.width-70, 30, D.width-70, 50, 1.0f, lineStroke);  // Down
	drawArrow (g2d, D.width-65, 25, D.width-45, 25, 1.0f, lineStroke);  // Right
	drawArrow (g2d, D.width-75, 25, D.width-95, 25, 1.0f, lineStroke);  // Left
	g.setFont (savedFont);

	// See if standard axes are in the middle.
	g.setColor (Color.gray);
	if ((minX < 0) && (maxX > 0) && (drawMiddleAxes)) {
	    // Draw y-axis
	    int x = (int) ( (0-minX)/(maxX-minX) * (D.width-2*inset) );
	    g.drawLine (inset+x, D.height-inset, inset+x, inset);
	}
	if ((minY < 0) && (maxY > 0) && (drawMiddleAxes)) {
	    // Draw x-axis
	    int y = (int) ((0-minY) / (maxY-minY) * (D.height-2.0*inset) );
	    g.drawLine (inset, D.height-y-inset, D.width-inset, D.height-y-inset);
	}

	// Draw the objects.
	drawObjects (g, points, lines, ovals, rectangles, images, labels, eqnLines);
	if (animationMode) {
	    drawObjects (g, animPoints, animLines, animOvals, animRectangles, null, labels, eqnLines);
	    // No images in animation mode.
	}

	drawScribbles (g);
    }


    void drawObjects (Graphics g, java.util.List<DrawObject> points, java.util.List<DrawObject> lines, java.util.List<DrawObject> ovals, java.util.List<DrawObject> rectangles, java.util.List<DrawObject> images, java.util.List<DrawObject> labels, java.util.List<DrawObject> eqnLines)
    {
	try {
	    // Images
	    synchronized(images) {	 
		for (DrawObject I: images) {
		    drawImage (g, I);
		}
	    }

	    // Labels
	    synchronized(labels) {	 
		for (DrawObject L: labels) {
		    drawLabel (g, L);
		}
	    }

	    // Eqn Lines
	    synchronized(eqnLines) {	 
		for (DrawObject L: eqnLines) {
		    g.setColor (L.color);
		    drawEqnLine (g, L);
		}
	    }

	    // Lines
	    synchronized(lines) {	 
		for (DrawObject L: lines) {
		    g.setColor (L.color);
		    drawLine (g, L);
		}
	    }

	    // Rectangles
	    synchronized(rectangles) {	 	 
		for (DrawObject R: rectangles) {
		    g.setColor (R.color);
		    drawOvalOrRectangle (g, R, true);
		}
	    }

	    // Ovals
	    synchronized(ovals) {
		for (DrawObject R: ovals) {
		    g.setColor (R.color);
		    drawOvalOrRectangle (g, R, false);
		}
	    }

	    // Points.
	    synchronized(points) {
		for (DrawObject p: points) {
		    g.setColor (p.color);
		    drawPoint (g, p);
		}
	    }
	}
	catch (ConcurrentModificationException e) {
	    e.printStackTrace();
	    System.exit (0);
	}
    }


    void drawPoint (Graphics g, DrawObject p) 
    {
	if (p == null) {
	    return;
	}
	if ((sequencingOn) && (p.sequenceNum!=currentSequenceNumDisplay)) {
	    return;
	}
	int x = (int) ( (p.x-minX)/(maxX-minX) * (D.width-2*inset) );
	int y = (int) ((p.y-minY) / (maxY-minY) * (D.height-2.0*inset) );
	if (p.diameter > 1) {
	    int r = p.diameter/2;
	    g.fillOval (inset+x-r, D.height-y-inset-r, 2*r, 2*r);
	}
	else {
	    g.fillRect (inset+x, D.height-y-inset, 1, 1);
	}
    }

    void drawOvalOrRectangle (Graphics g, DrawObject R, boolean isRect)
    {
	if (R == null) {
	    return;
	}
	if ((sequencingOn) && (R.sequenceNum!=currentSequenceNumDisplay)) {
	    return;
	}
	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke (R.drawStroke);
	int x1 = (int) ( (R.x-minX)/(maxX-minX) * (D.width-2*inset) );
	int y1 = (int) ((R.y-minY) / (maxY-minY) * (D.height-2.0*inset) );
	double x = R.x + R.width;
	double y = R.y - R.height;
	int x2 = (int) ( (x-minX)/(maxX-minX) * (D.width-2*inset) );
	int y2 = (int) ((y-minY) / (maxY-minY) * (D.height-2.0*inset) );
	if (isRect) {
	    g.drawRect (inset+x1, D.height-y1-inset, x2-x1, y1-y2);
	}
	else {
            g.drawOval (inset+x1, D.height-y1-inset, x2-x1, y1-y2);
	}
    }

    void drawLine (Graphics g, DrawObject L)
    {
	if (L == null) {
	    return;
	}
	if ((sequencingOn) && (L.sequenceNum!=currentSequenceNumDisplay)) {
	    return;
	}
	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke (L.drawStroke);
	int x1 = (int) ( (L.x-minX)/(maxX-minX) * (D.width-2*inset) );
	int y1 = (int) ( (L.y-minY)/(maxY-minY) * (D.height-2.0*inset) );
	int x2 = (int) ( (L.x2-minX)/(maxX-minX) * (D.width-2*inset) );
	int y2 = (int) ( (L.y2-minY)/(maxY-minY) * (D.height-2.0*inset) );
	if (L.isArrow) {
	    drawArrow ((Graphics2D)g, inset+x1, D.height-y1-inset, inset+x2, D.height-y2-inset, 1.0f, L.drawStroke);
	}
	else {
	    g.drawLine (inset+x1, D.height-y1-inset, inset+x2, D.height-y2-inset);
	}
    }

    // From: http://forum.java.sun.com/thread.jspa?threadID=378460&tstart=135
    void drawArrow (Graphics2D g2d, int xCenter, int yCenter, int x, int y, float stroke, BasicStroke drawStroke) 
    {
	double aDir = Math.atan2 (xCenter-x, yCenter-y);
	// Line can be dashed.
	g2d.setStroke (drawStroke);
	g2d.drawLine (x,y, xCenter,yCenter);
	// make the arrow head solid even if dash pattern has been specified
	g2d.setStroke (lineStroke);
	Polygon tmpPoly=new Polygon();
	int i1=12+(int)(stroke*2);
	// make the arrow head the same size regardless of the length length
	int i2=6+(int)stroke;
        tmpPoly.addPoint(x,y);
	tmpPoly.addPoint(x+xCor(i1,aDir+.5),y+yCor(i1,aDir+.5));
	tmpPoly.addPoint(x+xCor(i2,aDir),y+yCor(i2,aDir));
	tmpPoly.addPoint(x+xCor(i1,aDir-.5),y+yCor(i1,aDir-.5));
	tmpPoly.addPoint(x,y); // arrow tip
	g2d.drawPolygon(tmpPoly);

	// Remove this line to leave arrow head unpainted:
	g2d.fillPolygon(tmpPoly);
    }

    private int yCor (int len, double dir) 
    {
	return (int)(len * Math.cos(dir));
    }

    private int xCor (int len, double dir) 
    {
	return (int)(len * Math.sin(dir));
    }

    void drawImage (Graphics g, DrawObject I) 
    {
	if ((sequencingOn) && (I.sequenceNum!=currentSequenceNumDisplay)) {
	    return;
	}
	for (int i=0; i<I.pixels.length; i++) {
	    for (int j=0; j<I.pixels[i].length; j++) {
		Color c = new Color (I.pixels[i][j][1], I.pixels[i][j][2], I.pixels[i][j][3]);
		g.setColor (c);
		// Note: i starts at the top row of the image.
		int x = j;  // x along the x-axis is the column coord of pixels
		int y = I.pixels[i].length - i;
		int x1 = (int) ( (x-minX)/(maxX-minX) * (D.width-2*inset) );
		int y1 = (int) ((y-minY) / (maxY-minY) * (D.height-2.0*inset) );
		int x2 = (int) ( (x+1-minX)/(maxX-minX) * (D.width-2*inset) );
		int y2 = (int) ((y-1-minY) / (maxY-minY) * (D.height-2.0*inset) );
		g.fillRect (inset+x1, D.height-y1-inset, x2-x1, y1-y2);

	    }
	}
    }


    void drawLabel (Graphics g, DrawObject L) 
    {
	if ((sequencingOn) && (L.sequenceNum!=currentSequenceNumDisplay)) {
	    return;
	}
	int x = (int) ( (L.x-minX)/(maxX-minX) * (D.width-2*inset) );
	int y = (int) ( (L.y-minY)/(maxY-minY) * (D.height-2.0*inset) );
	g.drawString (L.str, inset+x, D.height-y-inset);
    }


    void drawScribbles (Graphics g) 
    {
	if ((scribbles == null) || (scribbles.size() == 0)) {
	    return;
	}
	DrawObject L = (DrawObject) scribbles.get (0);
	int scribbleCounter = L.scribbleNum;
	g.setColor (scribbleColor);
	((Graphics2D)g).setStroke (new BasicStroke(2f));
	int prevX = L.scribbleX;  int prevY = L.scribbleY;
	for (int i=1; i<scribbles.size(); i++) {
	    L = (DrawObject) scribbles.get (i);
	    if (L.scribbleNum == scribbleCounter) {
		// Keep drawing.
		g.drawLine (prevX, prevY, L.scribbleX, L.scribbleY);
		prevX = L.scribbleX;  prevY = L.scribbleY;
	    }
	    else {
		scribbleCounter = L.scribbleNum;
		prevX = L.scribbleX;  prevY = L.scribbleY;
	    }
	}
    }


    void drawEqnLine (Graphics g, DrawObject L)
    {
	if (L == null) {
	    return;
	}
	if ((sequencingOn) && (L.sequenceNum!=currentSequenceNumDisplay)) {
	    return;
	}
	// First, special cases: 
	if ((L.a == 0) && (L.b == 0)) {
	    return;
	}
	double px=0, py=0, qx=0, qy=0;
	double leftX=px, rightX=qx, leftY=py, rightY=qy;
	if (L.a == 0) {
	    // Case 2: a=0 => horizontal line.
	    leftX = minX;
	    rightX = maxX;
	    py = -L.c/L.b;
	    qy = -L.c/L.b;
	    if ((py < minY) || (py > maxY)) {
		// Cannot display.
		return;
	    }
	    leftY = rightY = py;
	}
	else if (L.b == 0) {
	    // Case 3: b=0 => vertical line.
	    leftX = -L.c/L.a;
	    rightX = -L.c/L.a;
	    leftY = minY;
	    rightY = maxY;
	    if ((leftX < minX) || (leftX > maxX)) {
		return;
	    }
	}
	else {
	    // Case 4: regular.
	    // Note: the line could intersect the drawable region in weird ways.
	    px = (-L.c - L.b*minY) / L.a;
	    qx = (-L.c - L.b*maxY) / L.a;
	    py = (-L.c - L.a*minX) / L.b;
	    qy = (-L.c - L.a*maxX) / L.b;
	    //System.out.println ("px=" + px + " qx=" + qx + " py=" + py + " qy=" + qy);
	    // Find leftmost point to draw.
	    if ((py >= minY) && (py <= maxY)) {
		// Leftmost point is on the x=minX line.
		leftX = minX;
		leftY = py;
		//System.out.println ("case(a): leftmost on minX-line");
	    }
	    else {
		// Leftmost point is not on the x=minX line.
		// Find leftmost point intersecting with low or high
		if (px < qx) {
		    leftX = px;  leftY = minY;
		    //System.out.println ("case(a.1): on minY-line");
		}
		else {
		    leftX = qx;  leftY = maxY;
		    //System.out.println ("case(a.2): on maxY-line");
		}
	    }

	    // Now find rightmost point.
	    if ((qy >= minY) && (qy <= maxY)) {
		// Rightmost point is on the x=maxX line.
		rightX = maxX;
		rightY = qy;
		//System.out.println ("case(b): rightmost on maxX-line");
	    }
	    else {
		if (px > qx) {
		    rightX = px;
		    rightY = minY;
		    //System.out.println ("case(b.1): rightmost on minY-line");
		}
		else {
		    rightX = qx;
		    rightY = maxY;
		    //System.out.println ("case(b.1): rightmost on maxY-line");
		}
	    }
	}

	//System.out.println ("left:(" + leftX + "," + leftY + ")  right:(" + rightX + "," + rightY + ")");

	int x1 = (int) ( (leftX-minX)/(maxX-minX) * (D.width-2*inset) );
	int y1 = (int) ( (leftY-minY)/(maxY-minY) * (D.height-2.0*inset) );
	int x2 = (int) ( (rightX-minX)/(maxX-minX) * (D.width-2*inset) );
	int y2 = (int) ( (rightY-minY)/(maxY-minY) * (D.height-2.0*inset) );
	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke (L.drawStroke);
	g.drawLine (inset+x1, D.height-y1-inset, inset+x2, D.height-y2-inset);
    }


}
