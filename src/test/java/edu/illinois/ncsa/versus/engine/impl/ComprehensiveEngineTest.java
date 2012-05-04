package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.junit.Assert.*;
import org.junit.Test;

public class ComprehensiveEngineTest {

	private static Log log = LogFactory.getLog(ComprehensiveEngineTest.class);

	@Test
	public void testCompute() {
		ComprehensiveEngine engine = new ComprehensiveEngine();
		List<PairwiseComparison> comparisons = new ArrayList<PairwiseComparison>();
		List<Double> weights = new ArrayList<Double>();
		weights.add(2.0);
		weights.add(2.0);

		File file1 = new File("data/test_1.jpg");
		File file2 = new File("data/test_1.jpg");

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

        assertEquals(4.0, result, 0);
	}
}
