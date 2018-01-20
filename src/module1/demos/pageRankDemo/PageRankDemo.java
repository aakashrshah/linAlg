// PageRankDemo.java
//
// Author: Rahul Simha, 2015
//
// Demonstrates Google's famous Page-Rank algorithm.
//
// What to read: the theory() method at least, and more if you
// can stand it.

import edu.gwu.lintool.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import java.text.*;


public class PageRankDemo extends JPanel implements MouseListener {
    
    // The class that has the linear algebra "magic"
    LinMagic linMagic = new LinMagic ();

    // NOTE: we'll use standard node numbering that starts with 0.

    // Variables for graph data.
    int numNodes;
    ArrayList<Edge> edges;
    ArrayList<Edge> tempEdges;  // For addition, one by one.
    ArrayList<Node> nodes;

    // Estimation and matrix data.
    double[][] probMatrix;
    double[][] googleMatrix;
    double[][] simMatrix;
    double[] visitProbEstimate;

    // GUI variables.
    int radius = 20;
    Color edgeColor = Color.DARK_GRAY;
    Color nodeColor = Color.LIGHT_GRAY;
    Color currentNodeColor = Color.YELLOW;

    // Simulation variables.
    boolean isReachable = true;
    int simStartNode = 0;
    int currentNode = -1;
    Thread currentThread;
    boolean threadStopped = true;
    int slowSleep = 100;
    int fastSleep = 10;
    int numSteps = 0;
    double alpha = 1;
    JTextField alphaField = new JTextField (5);

    // For DFS.
    int visitCount = -1;
    ArrayList<Integer> componentSizes;
    int currentComponentSize = 0;

    // Constructor.
    public PageRankDemo ()
    {
        this.addMouseListener (this);
    }
    

    //------------------------------------------------------------------
    // Analysis.

    void theory ()
    {
	// Before theory() is called, the simulation runs to
	// produce an estimate of the probability that a random walk
	// visits state i. This is stored in visitProbEstimate[i].

	// Transition matrix is the same matrix used in simulation.
	double[][] A = simMatrix;

	// We'll compute page rank two different ways.
	// Note: we've already simulated a random walk. So, if
	// linear algebra does its thing correctly, it should
	// produce the same results.
	double[] x = linMagic.pageRankViaEigenvalues (A);

	// The second way.
	double[] p = linMagic.pageRankViaPowerMethod (A);
	
	// Now we'll compare the two theoretical values with the
	// actual simulation estimates.
	System.out.println ("Comparison: estimate, eigenvector, power-method");
	for (int i=0; i<numNodes; i++) {
	    System.out.printf ("i=%3d: Estimate=%6.4f  Eigenvector=%6.4f  Powermethod=%6.4f\n", i, visitProbEstimate[i], x[i], p[i]);
	}
    }

    public void analyze ()
    {
	// First step in analysis: is the network connected?
	// We will check reachability from each node.
	for (int i=0; i<nodes.size(); i++) {
	    // Start DFS at i.
	    depthFirstSearch (i);
	    // See if all are reachable.
	    boolean failed = false;
	    int unreachable = -1;
	    for (int j=0; j<nodes.size(); j++) {
		if (j != i) {
		    Node node = nodes.get (j);
		    if (node.visitOrder < 0) {
			failed = true;
			unreachable = j;
		    }
		}
	    }
	    if (failed) {
		System.out.println ("Not strongly connected: node " + unreachable + " is unreachable from node " + i);
		isReachable = false;
	    }
	}

	// Now put the jump probabilities together.
	probMatrix = new double [numNodes][numNodes];
	for (int i=0; i<numNodes; i++) {
	    Node node = nodes.get(i);
	    if (node.outgoingEdges.size() == 0) {
		// It's a dangling node if there are no outgoing edges.
		node.isDangling = true;
		continue;
	    }
	    // Otherwise, jump prob = 1.0 / numOutgoing.
	    double jumpProb = 1.0 / node.outgoingEdges.size();
	    for (Edge e: node.outgoingEdges) {
		probMatrix[i][e.end] = jumpProb;
	    }
	}

	System.out.println ("Initial prob matrix");
	for (int i=0; i<numNodes; i++) {
	    for (int j=0; j<numNodes; j++) {
		System.out.printf (" %6.4f", probMatrix[i][j]);
	    }
	    System.out.println ();
	}	

	// Initially the simulationMatrix is the probMatrix, unless
	// replaced by the google matrix.
	simMatrix = probMatrix;
    }


    //------------------------------------------------------------------
    // Simulation

    void googolize ()
    {
	// Googolization solves the dangling node problem.

	double p = 1.0 / (double) numNodes;

	// Step 1: find dangling nodes and add 1/n to their rows.
	int numDangling = 0;
	for (int i=0; i<numNodes; i++) {
	    Node node = nodes.get(i);
	    if (node.isDangling) {
		for (int j=0; j<numNodes; j++) {
		    probMatrix[i][j] = p;
		    Edge e = new Edge ();
		    e.start = i;  e.end = j;   e.isOriginal = false;
		    node.outgoingEdges.add (e);
		    System.out.println ("Added new google edge: " + e);
		}
		numDangling ++;
	    }
	}

	getAlpha ();    // This does a sanity check too.

	System.out.println ("Googolize(): alpha=" + alpha + " #dangling=" + numDangling);

	// Step 2: compute new prob matrix and add edges accordingly.
	googleMatrix = new double[numNodes][numNodes];
	for (int i=0; i<numNodes; i++) {
	    for (int j=0; j<numNodes; j++) {
		googleMatrix[i][j] = alpha*probMatrix[i][j]
		    + (1-alpha)*p;
		//System.out.println (">> i=" + i + " j=" + j + " p[i][j]=" + probMatrix[i][j] + "  g[i][j]=" + googleMatrix[i][j]);
		if ((probMatrix[i][j] == 0) && (googleMatrix[i][j] > 0)) {
		    // Need to add an edge
		    Edge e = new Edge ();
		    e.start = i;  e.end = j;   e.isOriginal = false;
		    Node node = nodes.get(i);
		    node.outgoingEdges.add (e);
		    System.out.println ("Added new google edge: " + e);
		}
	    }
	}

	// Once we've googolized, we'll use that matrix for simulation.
	simMatrix = googleMatrix;

	this.repaint ();
    }

    void getAlpha ()
    {
	try {
	    alpha = Double.parseDouble (alphaField.getText().trim());
	    // Sanity check on alpha.
	    if (alpha < 0) {
		alpha = 0;
	    }
	    else if (alpha > 1) {
		alpha = 1;
	    }
	}
	catch (Exception e) {
	    System.out.println ("Improper number in alpha field");
	    alpha = 1;
	}
    }

    // No need to read further.
    //------------------------------------------------------------------
    // Depth-first search

    void depthFirstSearch (int startNode)
    {
        for (Node node: nodes) {
            node.visitOrder = -1;
        }

        visitCount = -1;
	recursiveDFS ( nodes.get(startNode) );
    }
    
    void recursiveDFS (Node node)
    {
        visitCount ++;
        node.visitOrder = visitCount;
        // Look for an unvisited neighbor among outgoing.
        if (node.outgoingEdges != null) {
            for (Iterator iter=node.outgoingEdges.iterator(); iter.hasNext(); ) {
                Edge edge = (Edge) iter.next();
                Node otherEnd = nodes.get (edge.end);
                if (otherEnd.visitOrder < 0) {
                    recursiveDFS (otherEnd);
                }
            }
        }
    }
    
    //------------------------------------------------------------------
    // Build network

    public void startEdgeAddition (int numNodes)
    {
        this.numNodes = numNodes;
        tempEdges = new ArrayList<Edge> ();
    }
    

    public void addEdge (int startNode, int endNode, boolean isOriginal)
    {
        Edge e = new Edge ();
        e.start = startNode;
        e.end = endNode;
	e.isOriginal = isOriginal;
        tempEdges.add (e);
    }
    
    
    public void endEdgeAddition ()
    {
        this.edges = tempEdges;
        buildNodes ();
        analyze ();
    }
    


    public void readFromStream (Readable charSource)
    {
        Scanner scanner = new Scanner (charSource);
        int numNodes = scanner.nextInt ();
        System.out.println (" >> numNodes=" + numNodes);
        int lineNum = 2;
        startEdgeAddition (numNodes);

        while (scanner.hasNextInt()) {
            // Now read two ints per edge.
            int start = scanner.nextInt ();
            int end = scanner.nextInt ();
            addEdge (start, end, true);
            lineNum ++;
        }
        
        endEdgeAddition ();
        System.out.println ("Input: Num nodes=" + numNodes + " numEdges=" + edges.size());
        this.repaint ();
    }


    public String toString ()
    {
        String str = "Network [#nodes=" + numNodes + "]: ";
        for (Edge e: edges) {
            str += " " + e;
        }
        return str;
    }



    public void readFile (String fileName)
    {
        try {
            readFromStream (new FileReader (fileName));
        }
        catch (IOException e){
            System.out.println ("Bad input file: " + fileName);
            return;
        }
    }
    

    public void buildNodes ()
    {
        // Build nodes.
        nodes = new ArrayList<Node>();

	for (int k=0; k<numNodes; k++) {
            Node node = new Node ();
	    node.id = k;
            node.incomingEdges = new ArrayList<Edge>();
            node.outgoingEdges = new ArrayList<Edge>();
	    nodes.add (node);
        }

        // Build incoming and outgoing edges.
        for (Edge e : edges) {
            Node endNode = nodes.get (e.end);
            endNode.incomingEdges.add (e);
            Node startNode = nodes.get (e.start);
            startNode.outgoingEdges.add (e);
        }

    }

    //------------------------------------------------------------------
    // GUI Events

    void start ()
    {
	currentNode = simStartNode;
	numSteps = 0;
	for (Node node: nodes) {
	    node.numVisits = 0;
	}
	this.repaint ();
    }

    void stop ()
    {
	threadStopped = true;
	visitProbEstimate = new double [numNodes];
	for (Node node: nodes) {
	    double p = (double) node.numVisits / (double) numSteps;
	    visitProbEstimate [node.id] = p;
	}
	theory ();
    }

    void next ()
    {
	Node node = nodes.get (currentNode);
	Node nextNode = node;  // Default.
	if (node.outgoingEdges.size() != 0) {
	    // Use the row of the simulation matrix to decide where next.
	    nextNode = jumpNext (node);
	}
	nextNode.numVisits ++;
	numSteps ++;
	nextNode.visitProb = (double) nextNode.numVisits / (double) numSteps;
	currentNode = nextNode.id;
	this.repaint ();
    }


    Node jumpNext (Node node)
    {
	int i = node.id;
	// Use row i of simMatrix;
	double x = LinUtil.uniform ();
	int next = numNodes-1;
	double sum = 0;
	for (int j=0; j<numNodes; j++) {
	    sum += simMatrix[i][j];
	    // As soon as the cdf is greater than x, we've found the next node.
	    if (sum > x) {
		next = j;
		break;
	    }
	    // If no node is found, the entire sum is 1, which means
	    // the last node is the jump node.
	}
	Node nextNode = nodes.get(next);
	return nextNode;
    }


    void run ()
    {
	animate (slowSleep);
    }

    void fast ()
    {
	animate (fastSleep);
    }

    void stopAnimationThread ()
    {
        if (currentThread != null) {
            currentThread.interrupt ();
            currentThread = null;
        }
    }

    void animate (int sleepTime)
    {
        stopAnimationThread ();    // To ensure only one thread.
	start ();
	currentThread = new Thread (new Runnable () {
		public void run () 
		{
		    threadStopped = false;
		    while (! threadStopped) {
			try {
			    Thread.sleep (sleepTime);
			}
			catch (Exception e) {}
			next ();
		    }
		    currentNode = -1;
		}
        });
	currentThread.start ();
    }

    Color getNodeColor (Node node)
    {
	// First value is 255, others depend on visitProb.
	// p=1 => c=0
	// p=0 => c=200
	// Eqn: c = -200*p + 200
	if (node.visitProb <= 0) {
	    return Color.LIGHT_GRAY;
	}
	int c = (int) (-200 * node.visitProb + 200);
	return new Color (255, c, c);
    }


    //------------------------------------------------------------------
    // GUI stuff

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        if ((edges == null) || (nodes == null) || (nodes.size() == 0)) {
            return;
        }
        
	Dimension D = this.getSize ();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width, D.height);
        
	// Draw nodes in a circular arrangement.
	int cx = D.width / 2;
	int cy = D.height / 2;
        int minD = D.width;
        if (D.height < minD) {
            minD = D.height;
        }
        int r = minD/2 - 2*radius;

	// Angle subtended by each node from center.
	double theta = 2.0 * Math.PI / (double)numNodes;

	// Create and draw the nodes.
	for (Node node : nodes) {
            double angle = node.id * theta;
	    node.x = cx + (int) (r * Math.cos (angle));
	    node.y = cy + (int) (r * Math.sin (angle));
	    int topLeftX = node.x-radius;
	    int topLeftY = D.height-node.y-radius;
	    if (node.id == currentNode) {
		g.setColor (currentNodeColor);
	    }
	    else if (node.visitProb > 0) {
		// Some shade of red.
		g.setColor ( getNodeColor(node) );
	    }
	    else {
		g.setColor (nodeColor);
	    }
	    g.fillOval (topLeftX, topLeftY, 2*radius, 2*radius);
            g.setColor (Color.black);
            g.drawString (""+node.id, topLeftX+radius/2, topLeftY+radius);
	}
        
        // Next, the edges.
	for (Node node: nodes) {
	    for (Edge e: node.outgoingEdges) {
		Node fromNode = nodes.get (e.start);
		Node toNode = nodes.get (e.end);
		if (e.isOriginal) {
		    drawArrow (g, fromNode, toNode, edgeColor);
		}
		else {
		    drawArrow (g, fromNode, toNode, Color.LIGHT_GRAY);
		}
	    }
	}

    }
    

    void drawArrow (Graphics g, Node node1, Node node2, Color color)
    {
	Dimension D = this.getSize ();
        //Graphics2D g2 = (Graphics2D) this.getGraphics ();
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor (color);
        
	if (node1.id == node2.id) {
	    // Self-loop.
	    selfLoop (g2, node1.x+radius, D.height-node1.y, radius);
	    return;
	}
	// First compute the end points.
	double theta = Math.atan2 (node2.y-node1.y, node2.x-node1.x);
	double thetaDeg = theta*360.0 / (2*Math.PI);
	int x1 = (int) (node1.x + radius*Math.cos(theta));
	int y1 = (int) (node1.y + radius*Math.sin(theta));
	int x2 = (int) (node2.x - radius*Math.cos(theta));
	int y2 = (int) (node2.y - radius*Math.sin(theta));
	y1 = D.height - y1;
	y2 = D.height - y2;
	drawArrow (g2, x1, y1, x2, y2, 1f);
    }

    // From: http://forum.java.sun.com/thread.jspa?threadID=378460&tstart=135
    public static void drawArrow (Graphics2D g2d, int xCenter, int yCenter, int x, int y, float stroke) 
    {
	double aDir = Math.atan2 (xCenter-x, yCenter-y);
	g2d.drawLine (x,y, xCenter,yCenter);
	// make the arrow head solid even if dash pattern has been specified
	g2d.setStroke (new BasicStroke(1f));						Polygon tmpPoly=new Polygon();
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

    private static int yCor (int len, double dir) 
    {
	return (int)(len * Math.cos(dir));
    }

    private static int xCor (int len, double dir) 
    {
	return (int)(len * Math.sin(dir));
    }

    void selfLoop (Graphics2D g2, int x, int y, int radius) 
    {
	// First draw a circle, then an arrowhead.
        radius = 2*radius/3;
	g2.setColor (Color.LIGHT_GRAY);
	int topLeftX = x;
	int topLeftY = y - radius;
	g2.drawOval (topLeftX, topLeftY, 2*radius, 2*radius);
	// Arrowhead at x,y.
	drawArrow (g2, x+3, y-3, x, y, 1f);
    }
    

    //------------------------------------------------------------------
    // GUI controls


    public void mouseClicked (MouseEvent e)
    {
	// Find out if any node got clicked.
	Dimension D = this.getSize ();
	for (int k=0; k<nodes.size(); k++) {
	    Node node = (Node) nodes.get(k);
	    int d = (int) distance (node.x, node.y, e.getX(), D.height-e.getY());
	    if (d < radius) {
		// Click occured => change state.
		simStartNode = k;
		break;
	    }
	}

	repaint ();
    }

    public void mouseEntered (MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    public void mouseReleased (MouseEvent e) {}

    double distance (int x1, int y1, int x2, int y2)
    {
	return Math.sqrt ( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
    }


    public void display ()
    {
	JFrame frame = new JFrame ();
	frame.setSize (800, 500);
	frame.setTitle ("Network Tool");
	frame.getContentPane().add (makeControls(), BorderLayout.SOUTH);
	frame.getContentPane().add (this, BorderLayout.CENTER);
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.setVisible (true);
    }

    JPanel makeControls ()
    {
	JPanel panel = new JPanel ();

	panel.add (new JLabel("alpha:"));
	alphaField.setText ("1.0");
	panel.add (alphaField);

	JButton b = new JButton ("Googolize");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    googolize ();
		}
        });
	panel.add (b);

	panel.add (new JLabel("    "));

	b = new JButton ("Start");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    start ();
		}
        });
	panel.add (b);

	b = new JButton ("Next");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    next ();
		}
        });
	panel.add (b);

	b = new JButton ("Run");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    run ();
		}
        });
	panel.add (b);

	b = new JButton ("Fast-run");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    fast ();
		}
        });
	panel.add (b);

	b = new JButton ("Stop");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    stop ();
		}
        });
	panel.add (b);

	panel.add (new JLabel ("       "));

	b = new JButton ("Quit");
	b.addActionListener (new ActionListener() {
		public void actionPerformed (ActionEvent a)
		{
		    System.exit (0);
		}
        });
	panel.add (b);
	
	return panel;
    }


    public static void main (String[] argv)
    {
        PageRankDemo demo = new PageRankDemo ();
        demo.readFile ("network.txt");
	demo.display ();
        System.out.println (demo);
    }

}


class Edge {
    int start=-1, end=-1;
    boolean show = true;
    boolean isOriginal = false;

    public boolean equals (Object obj)
    {
        if (! (obj instanceof Edge) ) {
            return false;
        }
        Edge e = (Edge) obj;
        if ( (start == e.start) && (end == e.end) ) {
            return true;
        }
        return false;
    }
    

    public String toString ()
    {
        String str = "(" + start + "," + end + ")";
        return str;
    }

}


class Node {
    int id;                           // id for drawing.
    int x,y;                          // x,y location on GUI.
    ArrayList<Edge> incomingEdges;
    ArrayList<Edge> outgoingEdges;
    int visitOrder;                   // For DFS
    int numVisits = 0;
    double visitProb = 0;
    boolean isDangling = false;

    public String toString ()
    {
        return "node=[id=" + id + ",incoming=" + incomingEdges + " outgoing=" + outgoingEdges + "]";
    }
    
}

