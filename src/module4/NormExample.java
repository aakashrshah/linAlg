// Instructions:
// Write code for dotProduct() and norm() in MatrixTool.java
// Then compile and execute this file.

import java.awt.*;
import java.util.*;

import edu.aakash.lintool3.MatrixTool;

public class NormExample {

    public static void main (String[] argv)
    {
	// Test dot product.
	double[] u = {1,0};
	double[] v = {0,1};
//	double[] u = {7,4};
//	double[] v = {3,5};
	double uv = MatrixTool.dotProduct (u,v);
	System.out.println ("u dot v = " + uv);     // 41

	// Test norm.
	double uNorm = MatrixTool.norm (u);         // 8.0622
	double vNorm = MatrixTool.norm (v);         // 5.8309
	System.out.println ("|u| = " + uNorm + " |v|=" + vNorm);
    }


}


