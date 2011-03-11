package edu.illinois.ncsa.versus.adapter;
import java.util.*;

/**
 * Anything that has vertices.
 * @author Kenton McHenry 
 */
public interface HasVertices extends Adapter
{
	/**
	 * Get vertices.
	 * @return a collection of vertices
	 */
	Vector<double[]> getVertices();

	/**
	 * Get one vertex at a time.
	 * @param index the index of the vertex
	 * @return the vertex at the given index
	 */
	double[] getVertex(int index);
}