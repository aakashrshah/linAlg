import java.awt.Image;
import java.io.IOException;

// Instructions:
// To blur an image, we'll use a simple idea. Consider the first
// 4 x 4 block of pixels in the image at the top left. Suppose
// we compute the average intensity of the 16 pixels in that block.
// Call this average avg. Then, in the blurred image, all 16 of
// those pixels will have the same intensity, equal to avg. Thus,
// we replace the intensity of each pixel with the average intensity
// of the k x k block it belongs to. Clearly, large values of k will
// cause significant blurring.
//
// In the code below, your job is to take an image and blur it using
// the above approach.

public class ImageBlur {
	
	static int[][] blurredImage;

    public static void main (String[] argv) throws IOException
    {
	ImageTool imTool = new ImageTool ();
	int[][] pixels = imTool.imageFileToGreyPixels("/Users/aakashshah/Documents/Java/workspace/linAlg/src/module2/ace.jpg");
	imTool.showImage (pixels, "original");
	
	ImageBlur.blurredImage = pixels;
	
	// Each block of k x k pixels has the same color.
	int k = 4;
	blur (pixels, k);
	imTool.showImage (blurredImage, "blurred");
    }

    static int[][] blur (int[][] pixels, int blurSize)
    {
    	
    		for(int i=0; i < pixels.length;i=i+blurSize) {
    			for(int j=0; j < pixels[0].length ; j=j+blurSize) {
    				int avg = averagePixelIntensities(pixels, blurSize,i,j);
    				setBlurPixels(blurSize,i,j,avg);
    			}
    		}
    		System.out.println(pixels.length + " " + pixels[0].length);
    		return pixels;
    }
    
    private static void setBlurPixels(int blurSize, int startI, int startJ, int avg) {
			for(int i = startI; i < startI+blurSize; i++) {
				for(int j = startJ; j< startJ+blurSize; j++) {
					if(i< blurredImage.length && j<blurredImage[0].length) {
						blurredImage[i][j] = avg;
					}
				}
			}
	}

	public static int averagePixelIntensities(int[][] pixels, int blurSize,int startI,int startJ) {
    		int sum = 0;
    		for(int i = startI; i < startI+blurSize; i++) {
    			for(int j = startJ; j< startJ+blurSize; j++) {
    				if(i< pixels.length && j<pixels[0].length) {
    					sum += pixels[i][j];
    				}
    			}
    		}
    		return sum / (blurSize * blurSize);
    }

}
