/**
 * 
 */
package edu.illinois.ncsa.versus.measure;

import edu.illinois.ncsa.versus.descriptor.Descriptor;

/**
 * A similarity measure requires the ability to compare two features.
 * 
 * @author Luigi Marini
 * 
 */
public interface Measure {

	/**
	 * Compare two features and output a similarity value representing how
	 * similar the two features are.
	 * 
	 * @param feature
	 *            1
	 * @param feature
	 *            2
	 * @return similarity value
	 * @throws Exception
	 */
	Similarity compare(Descriptor feature1, Descriptor feature2)
			throws Exception;

	/**
	 * Normalize a similarity value to a SimilarityPercentage.
	 * 
	 * @param similarity
	 *            value
	 * @return normalized similarity value
	 * 
	 * @deprecated confusing
	 */
	@Deprecated
	SimilarityPercentage normalize(Similarity similarity);

	/**
	 * The type of the feature used by this measure.
	 * 
	 * @return a unique string identifying the type
	 */
	String getFeatureType();

	/**
	 * The pretty name for the metric to use in applications.
	 * 
	 * @return the pretty name of the measure
	 */
	String getName();

	/**
	 * Get the class type of this measure.
	 * 
	 * @return the class for this measure
	 */
	Class<?> getType();
}
