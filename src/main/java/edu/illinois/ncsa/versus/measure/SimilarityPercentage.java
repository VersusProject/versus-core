/**
 * 
 */
package edu.illinois.ncsa.versus.measure;

/**
 * A similarity value represented by a number between 0 and 1, where 0 is not
 * similar at all and 1 is the same.
 * 
 * @author Luigi Marini
 * 
 */
public class SimilarityPercentage implements Similarity {

	private double similarity = 0.0;

	public SimilarityPercentage() {
		this(0);
	}

	public SimilarityPercentage(double similarity) {
		this.similarity = similarity;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	@Override
	public double getValue() {
		return similarity;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEqualityValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSimilarityMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSimilarityMax() {
		// TODO Auto-generated method stub
		return 0;
	}
}
