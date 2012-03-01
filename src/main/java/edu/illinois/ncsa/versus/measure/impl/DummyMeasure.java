/**
 * 
 */
package edu.illinois.ncsa.versus.measure.impl;

import edu.illinois.ncsa.versus.category.HasCategory;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature;
import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature2;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lmarini
 * 
 */
public class DummyMeasure implements Measure, HasCategory {

	private static final long SLEEP = 10000;

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
	public Set<Class<? extends Descriptor>> supportedFeaturesTypes() {
        Set<Class<? extends Descriptor>> featuresTypes = new HashSet<Class<? extends Descriptor>>(1);
        featuresTypes.add(DummyFeature.class);
        featuresTypes.add(DummyFeature2.class);
        return featuresTypes;
	}

	@Override
	public String getName() {
		return "Dummy Measure";
	}

	@Override
	public Class<DummyMeasure> getType() {
		return DummyMeasure.class;
	}

	@Override
	public String getCategory() {
		return "Test";
	}

}
