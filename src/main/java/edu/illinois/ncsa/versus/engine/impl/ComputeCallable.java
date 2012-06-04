/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.adapter.StreamLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Proximity;
import edu.illinois.ncsa.versus.measure.Similarity;

/**
 * Callable version of the ComputeThread. Currently used in
 * {@link ComprehensiveEngine}. Should be probably used in the
 * {@link ExecutionEngine} as well.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class ComputeCallable implements Callable<Proximity> {

	private final PairwiseComparison comparison;
	private final ComparisonStatusHandler handler;
	private static Log log = LogFactory.getLog(ComputeCallable.class);

	public ComputeCallable(PairwiseComparison comparison) {
		this.comparison = comparison;
		this.handler = null;
	}

	public ComputeCallable(PairwiseComparison comparison,
			ComparisonStatusHandler handler) {
		this.comparison = comparison;
		this.handler = handler;
	}

	@Override
	public Proximity call() throws Exception {
		if (handler != null) {
			handler.onStarted();
		}
		String adapterID = comparison.getAdapterId();
		String extractionID = comparison.getExtractorId();
		String measureID = comparison.getMeasureId();
		try {
			log.debug("Selected adapter is " + adapterID);
			Adapter adapter1 = (Adapter) Class.forName(adapterID).newInstance();
			Adapter adapter2 = (Adapter) Class.forName(adapterID).newInstance();
			log.debug("Selected extractor is " + extractionID);
			Extractor extractor = (Extractor) Class.forName(extractionID)
					.newInstance();
			log.debug("Selected measure is " + measureID);
			Measure measure = (Measure) Class.forName(measureID).newInstance();
			InputStream stream1 = comparison.getFirstDataset();
			InputStream stream2 = comparison.getSecondDataset();
			Proximity proximity = compare(stream1, stream2, adapter1, adapter2,
					extractor, measure);
			if (handler != null) {
				handler.onDone(proximity.getValue());
			}
			return proximity;
		} catch (Exception e1) {
			if (handler != null) {
				handler.onFailed("Error computing similarity", e1);
			}
			log.error(
					"Error computing similarity [" + comparison.getId() + "]",
					e1);
			throw e1;
		}
	}

	/**
	 * Compare the two files using specified comparison methods.
	 * 
	 * @param file1
	 * @param file2
	 * @param adapter1
	 * @return comparison
	 * @throws Exception
	 */
	private Similarity compare(InputStream stream1, InputStream stream2,
            Adapter adapter1, Adapter adapter2, Extractor extractor, Measure measure)
			throws Exception {

		if ((adapter1 instanceof StreamLoader)
				&& (adapter2 instanceof StreamLoader)) {
			StreamLoader streamLoaderAdapter = (StreamLoader) adapter1;
			streamLoaderAdapter.load(stream1);
			Descriptor feature1 = extractor.extract(streamLoaderAdapter);
			StreamLoader streamLoaderAdapter2 = (StreamLoader) adapter2;
			streamLoaderAdapter2.load(stream2);
			Descriptor feature2 = extractor.extract(streamLoaderAdapter2);
			Similarity value = measure.compare(feature1, feature2);
			return value;
		} else {
			handler.onAborted("Unsupported types");
			throw new UnsupportedTypeException("Unsupported adapters.");
		}
	}

}
