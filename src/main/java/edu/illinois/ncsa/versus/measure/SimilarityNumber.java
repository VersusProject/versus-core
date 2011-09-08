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
	private final double equality;
	private final double min;
	private final double max;

	public SimilarityNumber(double distance) {
		this(distance, -Double.MAX_VALUE, Double.MAX_VALUE, 0);
	}

	public SimilarityNumber(double distance, double min, double max,
			double equality) {
		this.distance = distance;
		this.min = min;
		this.max = max;
		this.equality = equality;

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
		return equality;
	}

	@Override
	public double getSimilarityMin() {
		return min;
	}

	@Override
	public double getSimilarityMax() {
		return max;
	}
}
