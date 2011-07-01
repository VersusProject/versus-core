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
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
@SuppressWarnings("serial")
public class Job implements Serializable {

	private String id;

	private Set<PairwiseComparison> comparisons = new HashSet<PairwiseComparison>();

	private Date started;

	private Date ended;

	public enum ComparisonStatus {
		STARTED, DONE, FAILED, ABORTED
	}

	private Map<String, ComparisonStatus> comparisonStatus = new HashMap<String, Job.ComparisonStatus>();

	public Job() {
		comparisonStatus = new HashMap<String, ComparisonStatus>();
	}

	public void setComparison(Set<PairwiseComparison> comparison) {
		this.comparisons = comparison;
	}

	public void addComparison(PairwiseComparison comparison) {
		comparisons.add(comparison);
	}

	public Set<PairwiseComparison> getComparisons() {
		return comparisons;
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

	public Map<String, ComparisonStatus> getComparisonStatus() {
		return comparisonStatus;
	}

	public synchronized void setStatus(String comparisonId,
			ComparisonStatus status) {
		if (getComparisonStatus().containsKey(comparisonId)) {
			getComparisonStatus().remove(comparisonId);
		}
		getComparisonStatus().put(comparisonId, status);
	}

	public ComparisonStatus getStatus(PairwiseComparison comparison) {
		return getComparisonStatus().get(comparison);
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
