/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.measure.Proximity;

/**
 * An engine that given a list of comparisons and weights, calculates the linear
 * combinations of the results.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class ComprehensiveEngine {

	private static final int EXECUTION_THREADS = 1;
	private final ExecutorService newFixedThreadPool;
	private static Log log = LogFactory.getLog(ComprehensiveEngine.class);

	public ComprehensiveEngine() {
		newFixedThreadPool = Executors.newFixedThreadPool(EXECUTION_THREADS);

	}

	/**
	 * Compute all comparisons and then linearly combine the results based on
	 * weights.
	 * 
	 * @param comparisons
	 * @param weights
	 * @return
	 */
	public Double compute(List<PairwiseComparison> comparisons,
			List<Double> weights) {
		log.debug("Submitting all comparisons");
		List<Future<Proximity>> results = new ArrayList<Future<Proximity>>(
				comparisons.size());
		for (int i = 0; i < comparisons.size(); i++) {
			ComputeCallable computeCallable = new ComputeCallable(
					comparisons.get(i));
			Future<Proximity> result = newFixedThreadPool
					.submit(computeCallable);
			results.add(result);
		}
		log.debug("Submission of comparisons done");
		Double sum = new Double(0);
		log.debug("Computing linear combination");
		for (int i = 0; i < results.size(); i++) {
			try {
				double value = results.get(i).get().getValue();
				double weight = weights.get(i);
				sum += value * weight;
				log.debug("Added proximity " + value + " with weight " + weight);
			} catch (InterruptedException e) {
				log.error("Error retrieving result of comparison", e);
			} catch (ExecutionException e) {
				log.error("Error retrieving result of comparison", e);
			}
		}
		log.debug("Linear combintation finished. Result is " + sum);
		return sum;
	}
}
