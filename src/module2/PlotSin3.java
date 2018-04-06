
public class PlotSin3 {

    public static void main (String[] argv)
    {
        Function F1 = new Function ("ph=0");
	Function F2 = new Function ("ph=p/4");
	Function F3 = new Function ("ph=p/2");
	Function F4 = new Function ("ph=p");
    Function F5 = new Function ("sin ph=p/2");
    Function F6 = new Function ("cos ph=p/2");

        for (double x=0; x<=1; x+=0.005) {
            // F1.add (x, Math.cos(2*Math.PI*x + 0));
            // F2.add (x, Math.cos(2*Math.PI*x + Math.PI/4));
            // F3.add (x, Math.cos(2*Math.PI*x + Math.PI/2)); 
            // F4.add (x, Math.cos(2*Math.PI*x + Math.PI)); 

            F5.add (x, Math.sin(2*Math.PI*x + Math.PI/2)); 
            F6.add (x, Math.cos(2*Math.PI*x + Math.PI/2)); 
        }
        // Function.show (F1, F2, F3, F4);
        Function.show (F5, F6);

    }
    
}