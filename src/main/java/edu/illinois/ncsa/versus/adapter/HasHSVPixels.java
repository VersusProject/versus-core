/**
 * 
 */
package edu.illinois.ncsa.versus.adapter;

/**
 * A multimedia object that has pixels in HSV space. First dimension is rows,
 * second is columns, third is bands.
 * 
 * @author Luigi Marini
 * 
 */
public interface HasHSVPixels extends Adapter {

	/**
	 * Get all pixels in document.
	 * 
	 * @return First dimension is rows, second is columns, third is bands
	 */
	double[][][] getHSVPixels();

	/**
	 * Get one pixel at a time.
	 * 
	 * @param row
	 *            the row in the image - top to bottom
	 * @param column
	 *            column in the image - left to right
	 * @param band
	 *            pixel's band
	 * @return value of the band for the pixel
	 */
	double getHSVPixel(int row, int column, int band);
}
