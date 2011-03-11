/**
 * 
 */
package edu.illinois.ncsa.versus.measure;

/**
 * A generic term used to indicate both similarity and dissimilarity.
 * 
 * @author lmarini
 *
 */
public interface Proximity {

	/**
	 * Pretty name.
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * Longer description.
	 * 
	 * @return
	 */
	String getDescription();
	
	/**
	 * Get value as double. 1 indicates identical, 0 very different.
	 * 
	 * @return similarity value as a {@link Double}
	 */
	double getValue();
	
	/**
	 * The value for which two objects are the same.
	 * 
	 * @return
	 */
	double getEqualityValue();
	
	/**
	 * The minimum of the range of possible values.
	 * 
	 * @return
	 */
	double getSimilarityMin();
	
	/**
	 * The max for the range of possible values.
	 * 
	 * @return
	 */
	double getSimilarityMax();
}
