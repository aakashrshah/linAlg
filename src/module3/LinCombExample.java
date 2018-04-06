// Instructions:
// 1. Read the code in main() to understand how DrawTool is used.
// 2. Compile and execute to see.
// 3. Draw the remaining arrows to complete the parallelogram.

public class LinCombExample {

    public static void main (String[] argv) 
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	double[] u = {1.5, 6};
	double[] v = {6, 4};
	double[] z = {7.5,10};
	DrawTool.drawVector (u);
	DrawTool.drawVector (v);
	// Alternatively: DrawTool.drawArrow (0,0, 6,4);
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (z);
	
	DrawTool.setArrowColor ("orange");
	DrawTool.drawArrow (1.5,6, 7.5,10);
	DrawTool.drawArrow (6,4,7.5,10);


    }

}
