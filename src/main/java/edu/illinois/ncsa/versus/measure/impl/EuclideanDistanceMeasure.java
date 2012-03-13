package edu.illinois.ncsa.versus.measure.impl;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.category.HasCategory;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.DoubleArrayFeature;
import edu.illinois.ncsa.versus.descriptor.impl.ThreeDimensionalDoubleArrayFeature;
import edu.illinois.ncsa.versus.descriptor.impl.VectorFeature;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;
import java.util.HashSet;
import java.util.Set;

/**
 * Euclidean distance of two vector features.
 * 
 * @author Luigi Marini
 * 
 */
public class EuclideanDistanceMeasure implements Measure, HasCategory {
	/**
	 * Compare two arrays of the same length.
	 * 
	 * @param feature1
	 *            an array
	 * @param feature2
	 *            another array
	 * @return the Euclidean distance between the two given arrays
	 * @throws Exception
	 */
	private SimilarityNumber compare(DoubleArrayFeature feature1,
			DoubleArrayFeature feature2) throws Exception {
		double sum = 0;

		// Check for same length
		if (feature1.getLength() != feature2.getLength()) {
			throw new Exception("Features must have the same length");
		}

		for (int i = 0; i < feature1.getLength(); i++) {
			sum += Math.pow(feature1.getValue(i) - feature2.getValue(i), 2);
		}

		return new SimilarityNumber(Math.sqrt(sum));
	}

	/**
	 * Compare two arrays of the same length.
	 * 
	 * @param feature1
	 *            an array
	 * @param feature2
	 *            another array
	 * @return the Euclidean distance between the two given arrays
	 * @throws Exception
	 */
	private SimilarityNumber compare(VectorFeature feature1, VectorFeature feature2) throws Exception {
		double sum = 0;

		// Check for same length
		if (feature1.size() != feature2.size()) {
			throw new Exception("Features must have the same length");
		}

		for (int i = 0; i < feature1.size(); i++) {
			if (!(feature1.get(i) instanceof double[]) || !(feature2.get(i) instanceof double[])) {
				throw new Exception("Features must be double[]");
			}
			double[] arr1 = (double[])feature1.get(i);
			double[] arr2 = (double[])feature2.get(i);
			if (arr1.length != arr2.length) {
				throw new Exception("Features.array must have the same length");
			}
			double subsum = 0;
			for(int j=0; j<arr1.length; j++) {
				subsum += Math.pow(arr1[j] - arr2[j], 2);
			}
			sum += Math.sqrt(subsum);
		}
		return new SimilarityNumber(Math.sqrt(sum));
	}

	/**
	 * Compare two arrays of pixels of the same size.
	 * 
	 * @param feature1
	 * @param feature2
	 * @return
	 * @throws Exception
	 */
	private SimilarityNumber compare(
			ThreeDimensionalDoubleArrayFeature feature1,
			ThreeDimensionalDoubleArrayFeature feature2) throws Exception {

		// check for same height
		if (feature1.getHeight() != feature2.getHeight()) {
			throw new Exception("Features must have the same height");
		}
		// check for same width
		if (feature1.getWidth() != feature2.getWidth()) {
			throw new Exception("Features must have the same width");
		}
		// check for same number of bands
		if (feature1.getNumBands() != feature2.getNumBands()) {
			throw new Exception("Features must have the same width");
		}

		double sum = 0;

		for (int row = 0; row < feature1.getHeight(); row++) {
			for (int col = 0; col < feature1.getWidth(); col++) {
				for (int band = 0; band < feature1.getNumBands(); band++) {
					sum += Math.pow(feature1.getValue(row, col, band)
							- feature2.getValue(row, col, band), 2);
				}
			}
		}

		double normalized = Math.sqrt(sum)
				/ Math.sqrt(feature1.getHeight() + feature1.getWidth()
						+ feature1.getNumBands());

		return new SimilarityNumber(normalized);
	}

	@Override
	public SimilarityPercentage normalize(Similarity similarity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Class<? extends Descriptor>> supportedFeaturesTypes() {
        Set<Class<? extends Descriptor>> featuresTypes = new HashSet<Class<? extends Descriptor>>(3);
        featuresTypes.add(DoubleArrayFeature.class);
        featuresTypes.add(VectorFeature.class);
        featuresTypes.add(ThreeDimensionalDoubleArrayFeature.class);
        return featuresTypes;
	}

	@Override
	public String getName() {
		return "Euclidean Distance";
	}

	@Override
	public Similarity compare(Descriptor feature1, Descriptor feature2)
			throws Exception {
		if (feature1 instanceof DoubleArrayFeature
				&& feature2 instanceof DoubleArrayFeature) {
			return compare((DoubleArrayFeature) feature1,
					(DoubleArrayFeature) feature2);
		} else if (feature1 instanceof VectorFeature
				&& feature2 instanceof VectorFeature) {
			return compare((VectorFeature)feature1, (VectorFeature)feature2);
		} else if (feature1 instanceof ThreeDimensionalDoubleArrayFeature
				&& feature2 instanceof ThreeDimensionalDoubleArrayFeature) {
			ThreeDimensionalDoubleArrayFeature arrayFeature1 = (ThreeDimensionalDoubleArrayFeature) feature1;
			ThreeDimensionalDoubleArrayFeature arrayFeature2 = (ThreeDimensionalDoubleArrayFeature) feature2;

			return compare(arrayFeature1, arrayFeature2);
		} else {
			throw new UnsupportedTypeException();
		}
	}

	@Override
	public Class<EuclideanDistanceMeasure> getType() {
		return EuclideanDistanceMeasure.class;
	}

	@Override
	public String getCategory() {
		return "Lp Minkowski family";
	}
}