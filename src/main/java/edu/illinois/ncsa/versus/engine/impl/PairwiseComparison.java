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

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * A pairwise comparison between two files based on an (Adapter, Extractor,
 * Measure) triple.
 * 
 * @author Luigi Marini
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
