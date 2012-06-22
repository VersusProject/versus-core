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

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.DoubleArrayFeature;
import edu.illinois.ncsa.versus.descriptor.impl.VectorFeature;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;

/**
 * Dynamic Time Warping between two collections of features. 
 * 
 * @author Devin Bonnie
 * 
 */
public class DynamicTimeWarpingMeasure implements Measure {


	private SimilarityNumber compare(VectorFeature<?>  feature1, VectorFeature<?> feature2) throws Exception {


		if( feature1.size() != feature2.size() ){
			throw new Exception("Vector Features must have the same length");
		}
		if( (feature1.get(0).getClass().toString() != "DoubleArrayFeature") || 
				(feature2.get(0).getClass().toString() != "DoubleArrayFeature") ) {

			throw new Exception("Vector Features must contain the same type");
		}		
		
		double[][] d = new double[feature1.size()][feature2.size()];
		d[0][0]      = 0;
		double d0, d1, d2, distance;		
		
		for(int i=1; i<=feature1.size(); i++){
			d[i][0] = Double.MAX_VALUE;
		}		
		for(int j=1; j<=feature2.size(); j++){
			d[0][j] = Double.MAX_VALUE;
		}
		
		//Fill in table
		for(int i=1; i<=feature1.size(); i++){
			for(int j=1; j<=feature2.size(); j++){
				
				d0                    = d[i-1][j];			//Deletion
				d1                    = d[i][j-1];			//Insertion
				d2                    = d[i-1][j-1];		//Match/Substitution
				distance              = 0;
				DoubleArrayFeature f1 = (DoubleArrayFeature)feature1.get(i);
				DoubleArrayFeature f2 = (DoubleArrayFeature)feature2.get(j);

				if( f2.getLength() != f2.getLength() ){
					throw new Exception("DoubleArrayFeature's must have the same length");
				}
				
				//ssd between the two features
				for( int k=0; k<f1.getLength(); k++){
					distance += Math.pow( f1.getValue(k)-f2.getValue(k), 2);					
				}				
				d[i][j] = Math.sqrt(distance) + Math.min(Math.min(d0, d1), d2); //distance is now euclidean (sqrt)
			}
		}		
		return new SimilarityNumber( d[feature1.size()][feature2.size()] );
	}

	@Override
	public SimilarityPercentage normalize(Similarity similarity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFeatureType() {
		return VectorFeature.class.getName();
	}

	@Override
	public String getName() {
		return "Dynamic Time Warping";
	}

	@Override
	public Similarity compare(Descriptor feature1, Descriptor feature2)	throws Exception {
 
		if (feature1 instanceof VectorFeature<?> && feature2 instanceof VectorFeature<?>) {
			return compare((VectorFeature<?>)feature1, (VectorFeature<?>)feature2);
		}
		else {
			throw new UnsupportedTypeException();
		}
	}

	@Override
	public Class<DynamicTimeWarpingMeasure> getType() {
		return DynamicTimeWarpingMeasure.class;
	}
}
