package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ComprehensiveEngineTest extends TestCase {

	private static Log log = LogFactory.getLog(ComprehensiveEngineTest.class);

	@org.junit.Test
	public void testCompute() {
		ComprehensiveEngine engine = new ComprehensiveEngine();
		List<PairwiseComparison> comparisons = new ArrayList<PairwiseComparison>();
		List<Double> weights = new ArrayList<Double>();
		weights.add(2.0);
		weights.add(2.0);

		// FIXME change to relative paths to local test files
		File file1 = new File(
				"/Users/lmarini/Desktop/nara-test/jpg/0/24p073.jpg");
		File file2 = new File(
				"/Users/lmarini/Desktop/nara-test/jpg/0/24p073.jpg");

		String prefix = "edu.illinois.ncsa.versus.";

		// create comparison 1
		PairwiseComparison comparison1 = new PairwiseComparison();
		comparison1.setFirstDataset(file1);
		comparison1.setSecondDataset(file2);
		comparison1.setAdapterId(prefix + "adapter.impl.BytesAdapter");
		comparison1.setExtractorId(prefix + "extract.impl.MD5Extractor");
		comparison1.setMeasureId(prefix + "measure.impl.MD5DistanceMeasure");
		comparisons.add(comparison1);

		// create comparison 2
		PairwiseComparison comparison2 = new PairwiseComparison();
		comparison2.setFirstDataset(file1);
		comparison2.setSecondDataset(file2);
		comparison2.setAdapterId(prefix + "adapter.impl.BytesAdapter");
		comparison2.setExtractorId(prefix + "extract.impl.MD5Extractor");
		comparison2.setMeasureId(prefix + "measure.impl.MD5DistanceMeasure");
		comparisons.add(comparison2);

		// compute linear combination
		Double result = engine.compute(comparisons, weights);
		log.debug("Results is " + result);

		assertEquals(4.0, result);
	}
}
