/*******************************************************************************
 * Copyright (c) 2010 University of Illinois. All rights reserved.
 * 
 * Developed by: 
 * National Center for Supercomputing Applications (NCSA)
 * University of Illinois
 * http://www.ncsa.illinois.edu/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimers.
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimers in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the names of National Center for Supercomputing Applications,
 *   University of Illinois, nor the names of its contributors may 
 *   be used to endorse or promote products derived from this Software 
 *   without specific prior written permission.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
 ******************************************************************************/
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
 * combinations of the results. Uses {@link java.util.concurrent.Callable} and
 * {@link java.util.concurrent.Future}.
 * 
 * @author Luigi Marini
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
