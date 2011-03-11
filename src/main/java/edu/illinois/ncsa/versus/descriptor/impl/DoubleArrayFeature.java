package edu.illinois.ncsa.versus.descriptor.impl;

import edu.illinois.ncsa.versus.descriptor.Descriptor;

/**
 * A feature represented by an array of doubles.
 * @author Kenton McHenry
 */
public class DoubleArrayFeature implements Descriptor
{
	public final String type = this.getClass().toString();
	private double[] values;

	public DoubleArrayFeature() {}

	public DoubleArrayFeature(double[] values)
	{
		this.values = values;
	}

	@Override
	public String getType()
	{
		return type;
	}

	public double[] getValues()
	{
		return values;
	}

	public double getValue(int index)
	{
		return values[index];
	}

	public int getLength()
	{
		return values.length;
	}

	@Override
	public String getName()
	{
		return "Array";
	}
}