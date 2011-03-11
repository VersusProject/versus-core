/**
 * 
 */
package edu.illinois.ncsa.versus.descriptor.impl;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.ncsa.versus.descriptor.Signature;

/**
 * A feature represented by a vector of objects E.
 * 
 * @author Luigi Marini
 * 
 */
public class VectorFeature<E> implements Signature {

	private final List<E> list = new ArrayList<E>();

	/**
	 * Add an element to the vector at a particular position.
	 * 
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) {
		list.add(index, element);
	}

	@Override
	public String getType() {
		return this.getClass().toString();
	}

	/**
	 * The size of the vector.
	 * 
	 * @return size
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Get an element at a specific index.
	 * 
	 * @param i
	 *            index
	 * @return element
	 */
	public E get(int i) {
		return list.get(i);
	}

	@Override
	public String getName() {
		return "Vector";
	}
}
