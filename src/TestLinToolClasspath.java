import edu.gwu.lintool.*;

public class TestLinToolClasspath {

    public static void main (String[] argv)
    {
    		//Test Class path
    		LinTest.testClasspath ();
    		
    		//Test LinTool
    		AakashLinTool adi = new AakashLinTool ();
    		LinTest.testRealNumberAdd (adi);
    }

}
