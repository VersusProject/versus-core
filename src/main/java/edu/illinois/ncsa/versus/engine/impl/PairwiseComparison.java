/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.io.Serializable;

/**
 * @author lmarini
 *
 */
@SuppressWarnings("serial")
public class PairwiseComparison implements Serializable {
	
	private String id;
	
	private File firstDataset;

	private File secondDataset;
	
	private String adapterId;
	
	private String extractorId;
	
	private String measureId;
	
	private Double similarity;
	
	public PairwiseComparison() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the firstDataset
	 */
	public File getFirstDataset() {
		return firstDataset;
	}

	/**
	 * @param firstDataset the firstDataset to set
	 */
	public void setFirstDataset(File firstDataset) {
		this.firstDataset = firstDataset;
	}

	/**
	 * @return the secondDataset
	 */
	public File getSecondDataset() {
		return secondDataset;
	}

	/**
	 * @param secondDataset the secondDataset to set
	 */
	public void setSecondDataset(File secondDataset) {
		this.secondDataset = secondDataset;
	}

	/**
	 * @return the similarity
	 */
	public Double getSimilarity() {
		return similarity;
	}

	/**
	 * @param similarity the similarity to set
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
