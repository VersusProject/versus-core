/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * A pairwise comparison between two files based on an adapter, extractor,
 * measure triple.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
@SuppressWarnings("serial")
public class PairwiseComparison implements Serializable {

	private String id;

	private InputStream firstDataset;

	private InputStream secondDataset;

	private String adapterId;

	private String extractorId;

	private String measureId;

	private Double similarity;

	public PairwiseComparison() {
	}

	/**
	 * @return the firstDataset
	 */
	public InputStream getFirstDataset() {
		return firstDataset;
	}

	/**
	 * @param firstDataset
	 *            the firstDataset to set
	 */
	public void setFirstDataset(InputStream firstDataset) {
		this.firstDataset = firstDataset;
	}

	/**
	 * @return the secondDataset
	 */
	public InputStream getSecondDataset() {
		return secondDataset;
	}

	/**
	 * @param secondDataset
	 *            the secondDataset to set
	 */
	public void setSecondDataset(InputStream secondDataset) {
		this.secondDataset = secondDataset;
	}

	/**
	 * @return the similarity
	 */
	public Double getSimilarity() {
		return similarity;
	}

	/**
	 * @param similarity
	 *            the similarity to set
	 */
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public void setAdapterId(String adapterId) {
		this.adapterId = adapterId;
	}

	public String getAdapterId() {
		return adapterId;
	}

	public void setExtractorId(String extractorId) {
		this.extractorId = extractorId;
	}

	public String getExtractorId() {
		return extractorId;
	}

	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}

	public String getMeasureId() {
		return measureId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
