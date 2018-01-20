
public class ImageExample {

    public static void main (String[] argv)
    {
	// Image Tool has many useful methods to read/write
	// and extract pixels.
	ImageTool imageTool = new ImageTool ();

	// Read the image file into a pixel array.
	int[][] pixels = imageTool.imageFileToGreyPixels ("testimage.png");

	// Print pixels.
	for (int i=0; i<pixels.length; i++) {
	    for (int j=0; j<pixels[0].length; j++) {
		System.out.printf (" %3d", pixels[i][j]);
	    }
	    System.out.println ();
	}
    }    


    /////////////////////////////////////////////////////////////

    // For the curious, this is how we made the image ...

    static void makeTestImage ()
    {
	ImageTool imageTool = new ImageTool ();

	int N = 20;
	int[][] pixels = new int [N][N];
	for (int i=0; i<N; i++) {
	    for (int j=0; j<N; j++) {
		pixels[i][j] = 255;
	    }
	}

	for (int i=4; i<8; i++) {
	    for (int j=14; j<18; j++) {
		pixels[i][j] = 0;
	    }
	}
	imageTool.writeToFile (pixels, "testimage.png");
    }

}
