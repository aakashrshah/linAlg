
public class PlotSin {

    public static void main (String[] argv)
    {
        Function F = new Function ("sin");
	Function G = new Function ("cos");
    int count = 0;
        for (double x=0; x<=20; x+=0.2) {
            count = count + 1;
            F.add (x, Math.sin(x));
	    G.add (x, Math.cos(x));
        }
        System.out.println(count);

        Function.show (F, G);
    }
    
}
