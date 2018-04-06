// Instructions:
// Try adding additional terms until k=13.

public class TrigPolyLinComb {

    public static void main (String[] argv)
    {
    		int k = 1;
    		int range = 11;
    		double y = 0.0;
		Function Fsum = new Function ("sin-sum");
		for (double x=0; x<=1; x+=0.01) {
			y = 0.0;
			for(k = 1;k <= range; k += 2) {
			    y += 1.0/k * Math.sin (2*Math.PI*k*x);
			}
//		    double y = Math.sin (2*Math.PI*x)
//		    		+ 1.0/3.0 * Math.sin (2*Math.PI*3*x);
		    Fsum.add (x, y);
		}
		Fsum.show ();
    }

}
