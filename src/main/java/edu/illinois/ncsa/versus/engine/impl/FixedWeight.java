package edu.illinois.ncsa.versus.engine.impl;

/**
 * Fixed weight for linear combinations.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class FixedWeight implements WeightCalculator {

	private final Double weight;

	public FixedWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public Double calculateWeight() {
		return weight;
	}

}
