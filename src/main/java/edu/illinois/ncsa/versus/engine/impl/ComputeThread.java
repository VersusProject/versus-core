/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.engine.impl.Job.ComparisonStatus;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;

/**
 * A thread dedicated to running a specific comparison between two files.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class ComputeThread extends Thread {

	/** Commons logging **/
	private static Log log = LogFactory.getLog(ComputeThread.class);

	private final File file1;

	private final File file2;

	private Adapter adapter1;

	private Extractor extractor;

	private Measure measure;

	private Adapter adapter2;

	private final Job job;

	private final PairwiseComparison pairwiseComparison;

	private final String adapterID;

	private final String extractionID;

	private final String measureID;

	private ComparisonStatusHandler handler;

	/**
	 * 
	 * @param pairwiseComparison
	 * @param comparison2
	 * @param jobStatus
	 * @param beanSession
	 */
	public ComputeThread(PairwiseComparison pairwiseComparison, Job job) {
		this.pairwiseComparison = pairwiseComparison;
		this.job = job;
		adapterID = pairwiseComparison.getAdapterId();
		extractionID = pairwiseComparison.getExtractorId();
		measureID = pairwiseComparison.getMeasureId();
		try {
			log.debug("Selected adapter is " + adapterID);
			adapter1 = (Adapter) Class.forName(adapterID).newInstance();
			adapter2 = (Adapter) Class.forName(adapterID).newInstance();
			log.debug("Selected extractor is " + extractionID);
			extractor = (Extractor) Class.forName(extractionID).newInstance();
			log.debug("Selected measure is " + measureID);
			measure = (Measure) Class.forName(measureID).newInstance();
		} catch (InstantiationException e) {
			log.error("Error setting up compute thread", e);
		} catch (IllegalAccessException e) {
			log.error("Error setting up compute thread", e);
		} catch (ClassNotFoundException e) {
			log.error("Error setting up compute thread", e);
		}
		file1 = pairwiseComparison.getFirstDataset();
		file2 = pairwiseComparison.getSecondDataset();
	}

	public ComputeThread(PairwiseComparison pairwiseComparison, Job job,
			ComparisonStatusHandler handler) {
		this(pairwiseComparison, job);
		this.handler = handler;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		try {
			handler.onStarted();
			Similarity compare = compare(file1, file2);
			job.updateSimilarityValue(pairwiseComparison.getId(),
					compare.getValue());
			if (handler != null) {
				handler.onDone(compare.getValue());
			}
			log.debug("Done computing similarity between " + file1 + " and "
					+ file2 + " = " + compare.getValue());
		} catch (Throwable e1) {
			// TODO need status in comparison
			job.updateSimilarityValue(pairwiseComparison.getId(), Double.NaN);
			if (handler != null) {
				handler.onFailed("Error computing similarity", e1);
			}
			job.setStatus(pairwiseComparison.getId(), ComparisonStatus.FAILED);
			log.error("Error computing similarity between " + file1 + " and "
					+ file2 + " [" + pairwiseComparison.getId() + "]", e1);
		}
	}

	/**
	 * 
	 * @param file1
	 * @param file2
	 * @return comparison
	 * @throws Exception
	 */
	private Similarity compare(File file1, File file2) throws Exception {

		if ((adapter1 instanceof FileLoader)
				&& (adapter2 instanceof FileLoader)) {
			FileLoader fileLoaderAdapter = (FileLoader) adapter1;
			fileLoaderAdapter.load(file1);
			Descriptor feature1 = extractor.extract(fileLoaderAdapter);
			FileLoader fileLoaderAdapter2 = (FileLoader) adapter2;
			fileLoaderAdapter2.load(file2);
			Descriptor feature2 = extractor.extract(fileLoaderAdapter2);
			Similarity value = measure.compare(feature1, feature2);
			log.debug("Compared " + file1.getName() + " with "
					+ file2.getName() + " = " + value.getValue());
			return value;
		} else {
			handler.onAborted("Unsupported types");
			throw new UnsupportedTypeException();
		}
	}
}
