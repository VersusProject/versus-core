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
package edu.illinois.ncsa.versus.measure.impl;

import java.util.Arrays;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.MD5Digest;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;

/**
 * MD5 Digest distance. Distance is 1 if the digests are equal, 0 otherwise.
 * 
 * @author Luigi Marini
 * 
 */
public class MD5DistanceMeasure implements Measure {

	@Override
	public Similarity compare(Descriptor feature1, Descriptor feature2)
			throws Exception {
		if (feature1 instanceof MD5Digest && feature2 instanceof MD5Digest) {
			MD5Digest md5Feature1 = (MD5Digest) feature1;
			MD5Digest md5Feature2 = (MD5Digest) feature2;
			if (Arrays.equals(md5Feature1.getDigest(), md5Feature2.getDigest())) {
				return new SimilarityNumber(1.0);
			} else {
				return new SimilarityNumber(0);
			}
		} else {
			throw new UnsupportedTypeException(
					"Similarity measure expects feature of type MD5Digest");
		}
	}

	@Override
	public SimilarityPercentage normalize(Similarity similarity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFeatureType() {
		return MD5Digest.class.getName();
	}

	@Override
	public String getName() {
		return "MD5 Hash Distance";
	}

	@Override
	public Class<MD5DistanceMeasure> getType() {
		return MD5DistanceMeasure.class;
	}

}
