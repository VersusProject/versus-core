/**
 * 
 */
package edu.illinois.ncsa.versus.descriptor.impl;

import edu.illinois.ncsa.versus.descriptor.Descriptor;

/**
 * A feature represented by a 3 dimensional array of doubles.
 * 
 * @author Luigi Marini
 * 
 */
public class ThreeDimensionalDoubleArrayFeature implements Descriptor {

	public final String type = this.getClass().toString();

	private double[][][] values;

	public ThreeDimensionalDoubleArrayFeature() {
		// TODO Auto-generated constructor stub
	}

	public ThreeDimensionalDoubleArrayFeature(double[][][] values) {
		this.values = values;
	}

	@Override
	public String getType() {
		return type;
	}

	public double[][][] getValues() {
		return values;
	}

	public double getValue(int row, int col, int band) {
		return values[row][col][band];
	}

	public int getNumBands() {
		return values[0][0].length;
	}

	public int getHeight() {
		return values.length;
	}

	public int getWidth() {
		return values[0].length;
	}

	@Override
	public String getName() {
		return "3D Array";
	}
}