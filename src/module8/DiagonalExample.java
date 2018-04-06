// Instructions:
// Try different values of k

public class Example {

    public static void main (String[] argv)
    {
	double sigma1=3, sigma2=2;
	double[][] A = {
	    {sigma1, 0},
            {0, sigma2}
        };

	// Corners of a square.
	int k = 1;
	double[] v1 = {0, 0};
	double[] v2 = {k, 0};
	double[] v3 = {0, k};
	double[] v4 = {k, k};

	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	DrawTool.setPointColor ("blue");
	drawPoints (v1, v2, v3, v4);

	double[] u1 = MatrixTool.matrixVectorMult (A, v1);
	double[] u2 = MatrixTool.matrixVectorMult (A, v2);
	double[] u3 = MatrixTool.matrixVectorMult (A, v3);
	double[] u4 = MatrixTool.matrixVectorMult (A, v4);

	DrawTool.setPointColor ("red");
	drawPoints (v1, u2, u3, u4);
    }

    static void drawPoints (double[] v1, double[] v2, double[] v3, double[] v4)
    {
	DrawTool.drawPoint (v1[0], v1[1]);
	DrawTool.drawPoint (v2[0], v2[1]);
	DrawTool.drawPoint (v3[0], v3[1]);
	DrawTool.drawPoint (v4[0], v4[1]);
    }

}
