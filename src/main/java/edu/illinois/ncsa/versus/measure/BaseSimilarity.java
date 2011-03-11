/**
 * 
 */
package edu.illinois.ncsa.versus.measure;

/**
 * Test to see if it makes sense to have similarities enforce order by default.
 * 
 * @author Luigi Marini
 * 
 */
public abstract class BaseSimilarity implements Similarity,
		Comparable<BaseSimilarity> {

	@Override
	public int compareTo(BaseSimilarity o) {
		double d1 = this.getValue();
		double d2 = o.getValue();
		if (d1 < d2) {
			return -1;
		} else if (d1 == d2) {
			return 0;
		} else {
			return 1;
		}
	}

}
