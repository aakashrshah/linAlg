
public class PlotSin2 {

    public static void main (String[] argv)
    {
        Function F = new Function ("sin-1");
	Function G = new Function ("sin-2");
	Function H = new Function ("sin-3");
        for (double x=0; x<=1.005; x+=0.005) {
            F.add (x, Math.sin(2*Math.PI*x));
            G.add (x, Math.sin(2*Math.PI*2*x));
            H.add (x, Math.sin(2*Math.PI*3*x));
        }
        Function.show (F, G, H);
    }
    
}