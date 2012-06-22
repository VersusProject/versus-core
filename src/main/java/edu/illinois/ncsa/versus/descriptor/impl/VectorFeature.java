/*******************************************************************************
 * Copyright (c) 2010 University of Illinois. All rights reserved.
 * 
 * Developed by: 
 * National Center for Supercomputing Applications (NCSA)
 * University of Illinois
 * http://www.ncsa.illinois.edu/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimers.
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimers in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the names of National Center for Supercomputing Applications,
 *   University of Illinois, nor the names of its contributors may 
 *   be used to endorse or promote products derived from this Software 
 *   without specific prior written permission.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
 ******************************************************************************/
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
