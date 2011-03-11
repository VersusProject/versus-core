/**
 * 
 */
package edu.illinois.ncsa.versus.measure;

/**
 * @author lmarini
 * 
 */
public class SimilarityNumber implements Similarity {

	private final double distance;

	public SimilarityNumber(double distance) {
		this.distance = distance;
	}

	@Override
	public double getValue() {
		return distance;
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
