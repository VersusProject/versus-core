/**
 * 
 */
package edu.illinois.ncsa.versus.measure.impl;

import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;

/**
 * @author lmarini
 * 
 */
public class DummyMeasure implements Measure {

	private static final long SLEEP = 1000;

	@Override
	public Similarity compare(Descriptor feature1, Descriptor feature2)
			throws Exception {
		Thread.sleep(SLEEP);
		return new SimilarityNumber(0);
	}

	@Override
	public SimilarityPercentage normalize(Similarity similarity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFeatureType() {
		return DummyFeature.class.getName();
	}

	@Override
	public String getName() {
		return "Dummy Measure";
	}

	@Override
	public Class<DummyMeasure> getType() {
		return DummyMeasure.class;
	}

}
