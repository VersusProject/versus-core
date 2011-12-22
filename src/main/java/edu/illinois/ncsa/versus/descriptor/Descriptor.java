/**
 * 
 */
package edu.illinois.ncsa.versus.descriptor;

/**
 * A descriptor is a representation of a particular multimedia item. Extractors
 * produce descriptors and similarity measures compare two descriptors.
 * 
 * There are two main kinds of descriptors: feature descriptors and signature
 * descriptors. Feature descriptors represent a particular aspect of a
 * multimedia item while signature descriptors represent collections of feature
 * descriptors for a particular multimedia item.
 * 
 * @author Luigi Marini
 * 
 */
public interface Descriptor {

	/**
	 * Get the pretty name of this feature.
	 * 
	 * @return pretty name
	 */
	public String getName();

	/**
	 * Get the unique type of this feature.
	 * 
	 * @return a unique string
	 */
	public String getType();
		
}
