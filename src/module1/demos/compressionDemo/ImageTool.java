// File: ImageTool.java
//
// To convert Java's Image instances to pixel arrays and back.
// Author: Rahul Simha
// Inspired by Dick Baldwin's example: http://www.developer.com/java/other/article.php/3403921
// Modified: Aug 21, 2006
// Modified: Aug 29, 2006
// Modified: Oct, 2014 (yeah, after 8 years!)
//           => Simplify for greyscale, added PNG, scaling.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


// A JPanel to draw the image. 

class ImagePanel extends JPanel {

    Image image;
    boolean fitPanel = true;

    public ImagePanel (Image image) 
    {
	this.image = image;
    }

    public void paintComponent (Graphics g)
    {
	if (image == null) {
	    g.drawString ("No image", 50,50);
	    return;
	}
	if (fitPanel) {
	    g.drawImage (image, 0,0, this.getWidth(), this.getHeight(), this);
	}
	else {
	    g.drawImage (image, 0,0, this);
	}
    }

} // end of ImagePanel class



public class ImageTool extends JFrame {

    static int locationX = 0;
    static int locationY = 0;

    //
    /////////////////////////////////////////////////////////////
    //
    // The public methods. Not all are needed.


    public void showImage (Image image)
    {
        showImage (image, "No title");
    }
    

    public void showImage (Image image, String title)
    {
	// Make a frame and set parameters.
	JFrame f = new JFrame ();
	f.setSize (400, 300);
	f.setTitle (title);
        locationX += 20;
        locationY += 20;
        f.setLocation (locationX, locationY);

	// Add an instance of the image-drawing panel.
	ImagePanel drawPanel = new ImagePanel (image);
	Container cPane = f.getContentPane();
	cPane.add (drawPanel, BorderLayout.CENTER);

	// Display.
	f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	f.setVisible (true);
    }


    public void showImage (int[][][] pixels, String title)
    {
	showImage (pixelsToImage(pixels), title);
    }


    public int[][][] imageToPixels (Image image)
    {
	// Java's peculiar way of extracting pixels is to give them
	// back as a one-dimensional array from which we will construct
	// our version.
	int numRows = image.getHeight (this);
	int numCols = image.getWidth (this);
	int[] oneDPixels = new int[numRows*numCols];

	// This will place the pixels in oneDPixels[]. Each int in
	// oneDPixels has 4 bytes containing the 4 pieces we need.
	PixelGrabber grabber = new PixelGrabber(image, 0, 0, numCols, numRows,
						oneDPixels ,0 ,numCols);
	try {
	    grabber.grabPixels (0);
	}
	catch (InterruptedException e) {
	    System.out.println (e);
	}

	// Now we make our array.
	int[][][] pixels = new int[numRows][numCols][4];
	for(int row = 0; row < numRows; row++){
	    // First extract a row of int's from the right place.
	    int[] aRow = new int [numCols];
	    for(int col = 0; col < numCols; col++){
		int element = row * numCols + col;
		aRow[col] = oneDPixels[element];
	    }

	    // In Java, the most significant byte is the alpha value,
	    // followed by R, then G, then B. Thus, to extract the alpha
	    // value, we shift by 24 and make sure we extract only that byte.
	    for(int col=0; col < numCols; col++){
		pixels[row][col][0] = (aRow[col] >> 24) & 0xFF;  // Alpha
		pixels[row][col][1] = (aRow[col] >> 16) & 0xFF;  // Red
		pixels[row][col][2] = (aRow[col] >> 8)  & 0xFF;  // Green
		pixels[row][col][3] = (aRow[col]) & 0xFF;        // Blue
	    }
	}

	return pixels;
    }


    public int[][] imageToGreyPixels (Image image)
    {
	int[][][] pixels = imageToPixels (image);
	return pixelsToGreyPixels (pixels);
    }


    public int[][] pixelsToGreyPixels (int[][][] pixels)
    {
	int[][] greyPixels = new int[pixels.length][pixels[0].length];
	for (int row=0; row < pixels.length; row++) {
	    for (int col=0; col < pixels[row].length; col++) {
		double sum = 0;
		for (int k=1; k<4; k++) {
		    sum += pixels[row][col][k];
		}
		greyPixels[row][col] = (int) Math.round (sum/3.0);
	    }
	}	
	return greyPixels;
    }


    public int[][][] greyPixelsToPixels (int[][] greyPixels)
    {
	int[][][] pixels = new int[greyPixels.length][greyPixels[0].length][4];
	for (int row=0; row < pixels.length; row++) {
	    for (int col=0; col < pixels[row].length; col++) {
		pixels[row][col][0] = 255;  // transparency=0
		for (int k=1; k<4; k++) {
		    pixels[row][col][k] = greyPixels[row][col];
		}
	    }
	}	
	return pixels;
    }

    public Image pixelsToImage (int[][][] pixels)
    {
	int numRows = pixels.length;
	int numCols = pixels[0].length;
	int[] oneDPixels = new int [numRows * numCols];

	int index = 0;
	for(int row=0; row < numRows; row++){
	    for(int col=0; col < numCols; col++) {
		oneDPixels[index] = ((pixels[row][col][0] << 24) & 0xFF000000)
		    | ((pixels[row][col][1] << 16) & 0x00FF0000)
		    | ((pixels[row][col][2] << 8)  & 0x0000FF00)
		    | ((pixels[row][col][3]) & 0x000000FF);
		index ++;
	    }
	}

	// The MemoryImageSource class is an ImageProducer that can
	// build an image out of 1D pixels. Then, rather confusingly,
	// the createImage() method, inherited from Component, is used
	// to make the actual Image instance. This is simply Java's
	// confusing, roundabout way. An alternative is to use the
	// Raster models provided in BufferedImage.
	MemoryImageSource imSource = new MemoryImageSource(numCols, numRows, oneDPixels, 0, numCols);
	Image I = createImage (imSource);
	return I;

    }


    public Image readImageFile (String fileName)
    {
	BufferedImage image = null;
	try {
	    image = ImageIO.read (new File(fileName));
	} 
	catch (IOException e) {
	}
	/* Older code.
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image image = tk.getImage (fileName);
	MediaTracker tracker = new MediaTracker (this);
	tracker.addImage (image, 1);
	try {
	    tracker.waitForID (1);
	}
	catch (InterruptedException e) {
	    System.out.println (e);
	}
	*/
	return image;
    }


    public int[][][] imageFileToPixels (String fileName)
    {
        Image image = readImageFile (fileName);
        int[][][] pixels = imageToPixels (image);
        return pixels;
    }


    public static BufferedImage toBufferedImage (Image image)
    {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        
        // Exploit ImageIcon's code that waits for all pixels to be loaded.
        image = new ImageIcon(image).getImage();
        
	int numRows = image.getHeight (null);
	int numCols = image.getWidth (null);

        // We'll assume RGB + transparency. If not, this has to be fixed.
        BufferedImage bufImage = new BufferedImage (numCols, numRows, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = bufImage.createGraphics ();
        g.drawImage (image, 0, 0, null);
        g.dispose ();
        
        return bufImage;
    }


    public static void writeToFile (Image image, String imageType, String fileName)
    {
        try {
            BufferedImage bufImage = toBufferedImage (image);
            ImageIO.write (bufImage, imageType, new File (fileName));
        }
        catch (IOException e) {
            System.out.println (e);
        }
    }

    public static void writeToFile (Image image, String fileName)
    {
	writeToFile (image, "png", fileName);
    }

    
    public void writeToFile (int[][][] pixels, String fileName)
    {
        Image image = pixelsToImage (pixels);
        writeToFile (image, fileName);
    }


    public void writeToJPGFile (Image image, String fileName)
    {
	writeToFile (image, "jpg", fileName);
    }

    public void writeToJPGFile (int[][][] pixels, String fileName)
    {
	writeToFile (pixelsToImage (pixels), "jpg", fileName);
    }


    // 
    /////////////////////////////////////////////////////////////
    //
    // Greyscale versions

    public void showImage (int[][] greyPixels, String title)
    {
	int[][][] pixels = greyPixelsToPixels (greyPixels);
	showImage (pixelsToImage(pixels), title);
    }

    public int[][] imageFileToGreyPixels (String fileName)
    {
        Image image = readImageFile (fileName);
        int[][][] pixels = imageToPixels (image);
        return pixelsToGreyPixels (pixels);
    }
    
    public void writeToFile (int[][] greyPixels, String fileName)
    {
        Image image = pixelsToImage (greyPixelsToPixels(greyPixels));
        writeToFile (image, fileName);
    }


    // 
    /////////////////////////////////////////////////////////////
    //
    // Test code.


    public static void main (String[] argv)
    {
	ImageTool imTool = new ImageTool ();
	Image image = imTool.readImageFile ("flower.png");
	imTool.showImage (image, "BEFORE");

	// Now modify image.
	int[][][] pixels = imTool.imageToPixels (image);
	for (int row=0; row < pixels.length; row+=2) {
	    for (int col=0; col < pixels[row].length; col++) {
		// Zero it out.
		pixels[row][col][0] = 0;
		pixels[row][col][1] = 0;
		pixels[row][col][2] = 0;
		pixels[row][col][3] = 0;
	    }
	}
	Image image2 = imTool.pixelsToImage (pixels);
	imTool.showImage (image2, "AFTER");

        // Create a pure-pixel image.
        int size = 500;
        pixels = new int [size][size][4];
        for (int i=0; i<pixels.length; i++) {
            for (int j=0; j<pixels[0].length; j++) {
                for (int k=0; k<4; k++) {
                    pixels[i][j][k] = 255;
                }
            }
        }
        
        int start = size/4;
        int end = (int) (3.0*size/4.0);
        for (int i=start; i<=end; i++) {
            for (int j=start; j<=end; j++) {
                for (int k=1; k<4; k++) {
                    pixels[i][j][k] = 0;
                }
            }
        }
        
	Image image3 = imTool.pixelsToImage (pixels);
        imTool.writeToJPGFile (image3, "artificial.jpg");
        Image image4 = imTool.readImageFile ("artificial.jpg");
	imTool.showImage (image4, "ARTIFICIAL");

    }

}
