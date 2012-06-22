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
package edu.illinois.ncsa.versus.measure;

import edu.illinois.ncsa.versus.descriptor.Descriptor;

/**
 * A similarity measure requires the ability to compare two features.
 * 
 * @author Luigi Marini
 * 
 */
public interface Measure {

	/**
	 * Compare two features and output a similarity value representing how
	 * similar the two features are.
	 * 
	 * @param feature
	 *            1
	 * @param feature
	 *            2
	 * @return similarity value
	 * @throws Exception
	 */
	Similarity compare(Descriptor feature1, Descriptor feature2)
			throws Exception;

	/**
	 * Normalize a similarity value to a SimilarityPercentage.
	 * 
	 * @param similarity
	 *            value
	 * @return normalized similarity value
	 * 
	 * @deprecated confusing
	 */
	@Deprecated
	SimilarityPercentage normalize(Similarity similarity);

	/**
	 * The type of the feature used by this measure.
	 * 
	 * @return a unique string identifying the type
	 */
	String getFeatureType();

	/**
	 * The pretty name for the metric to use in applications.
	 * 
	 * @return the pretty name of the measure
	 */
	String getName();

	/**
	 * Get the class type of this measure.
	 * 
	 * @return the class for this measure
	 */
	Class<?> getType();
}
