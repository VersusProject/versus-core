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

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A job is a collection of {@link PairwiseComparison} that are submitted to an
 * engine. It includes a start/end timestamp and a status for each pairwise
 * comparison.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class Job implements Serializable {

	private volatile String id;

	private Set<PairwiseComparison> comparisons = new HashSet<PairwiseComparison>();

	private volatile Date started;

	private volatile Date ended;

	public enum ComparisonStatus {
		STARTED, DONE, FAILED, ABORTED
	}

	private Map<String, ComparisonStatus> comparisonStatus = new HashMap<String, Job.ComparisonStatus>();

	public Job() {
		comparisonStatus = new HashMap<String, ComparisonStatus>();
	}

	public synchronized void setComparisons(Set<PairwiseComparison> comparison) {
		this.comparisons = comparison;
	}

	public synchronized void addComparison(PairwiseComparison comparison) {
		comparisons.add(comparison);
	}

	public synchronized Set<PairwiseComparison> getComparisons() {
		return new HashSet<PairwiseComparison>(comparisons);
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getStarted() {
		return started;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}

	public Date getEnded() {
		return ended;
	}

	public synchronized void setStatus(String comparisonId,
			ComparisonStatus status) {
		comparisonStatus.put(comparisonId, status);
	}

	public synchronized ComparisonStatus getStatus(PairwiseComparison comparison) {
		return comparisonStatus.get(comparison.getId());
	}

	public synchronized void updateSimilarityValue(String comparisonId,
			Double similarity) {
		Iterator<PairwiseComparison> iterator = comparisons.iterator();
		while (iterator.hasNext()) {
			PairwiseComparison comparison = iterator.next();
			if (comparison.getId().equals(comparisonId)) {
				comparison.setSimilarity(similarity);
				setStatus(comparisonId, ComparisonStatus.DONE);
			}
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
