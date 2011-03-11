/**
 * 
 */
package edu.illinois.ncsa.versus.measure;

/**
 * Simple similarity measure returning the similarity value as a
 * {@link SimilarityPercentage}.
 * 
 * @author Luigi Marini
 * 
 */
public abstract class BaseMeasure implements Measure {

	/**
	 * Load files using adapters provided. Extract features using the extractor
	 * and compare the two.
	 * 
	 * @param obj1
	 * @param obj2
	 * @param extractor
	 * @param adapter
	 * @return
	 * @throws Exception
	 */
	// public SimilarityValue compare(File obj1, File obj2,
	// Extractor<F, A> extractor) throws Exception {
	// FileLoader newAdapter1 = (FileLoader) extractor.newAdapter();
	// newAdapter1.load(obj1);
	// FileLoader newAdapter2 = (FileLoader) extractor.newAdapter();
	// newAdapter2.load(obj2);
	// F feature1 = extractor.extract(newAdapter1);
	// F feature2 = extractor.extract(newAdapter2);
	// V similarityValue = compare(feature1, feature2);
	// SimilarityPercentage similarityPercentage = normalize(similarityValue);
	// return similarityPercentage;
	// }
}
