/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.util.List;

/**
 * @author lmarini
 * 
 */
public class LinearCombination {

	public Double combine(List<Double> vector, List<Double> weights)
			throws Exception {
		if (vector.size() != weights.size()) {
			throw new Exception("List size for proximities [" + vector.size()
					+ "] does not match list size of weights ["
					+ weights.size() + "]");
		}
		Double sum = new Double(0);
		for (int i = 0; i < vector.size(); i++) {
			sum += vector.get(i) * weights.get(i);
		}
		return sum;
	}
}
