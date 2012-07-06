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
package edu.illinois.ncsa.versus.measure.impl;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.LabelHistogramDescriptor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;

/**
 * Euclidean distance between two label histograms. Any labels not found in a
 * descriptor are assumed to have value 0.
 * 
 * @author Luigi Marini
 * @author Rob Kooper
 * 
 */
public class LabelHistogramIntersectionDistanceMeasure implements Measure {

    @Override
    public SimilarityPercentage normalize(Similarity similarity) {
        throw (new RuntimeException("Not implemented."));
    }

    @Override
	public Set<Class<? extends Descriptor>> supportedFeaturesTypes() {
        Set<Class<? extends Descriptor>> featuresTypes = new HashSet<Class<? extends Descriptor>>(1);
        featuresTypes.add(LabelHistogramDescriptor.class);
        return featuresTypes;
    }

    @Override
    public String getName() {
        return "Histogram Distance";
    }

    @Override
    public Class<LabelHistogramIntersectionDistanceMeasure> getType() {
        return LabelHistogramIntersectionDistanceMeasure.class;
    }

    @Override
    public Similarity compare(Descriptor desc1, Descriptor desc2) throws Exception {
        if ((desc1 instanceof LabelHistogramDescriptor) && (desc2 instanceof LabelHistogramDescriptor)) {
            LabelHistogramDescriptor lhd1 = (LabelHistogramDescriptor) desc1;
            LabelHistogramDescriptor lhd2 = (LabelHistogramDescriptor) desc2;

            // get all possible labels
            Set<String> labels = new HashSet<String>();
            labels.addAll(lhd1.getLabels());
            labels.addAll(lhd2.getLabels());

            // normalize
            lhd1.normalize();
            lhd2.normalize();

            // compute intersection
            double minsum = 0;
            for (String s : labels) {
                Double b1 = lhd1.getBin(s);
                Double b2 = lhd2.getBin(s);
                if ((b1 != null) && (b2 != null)) {
                    minsum += Math.min(b1, b2);
                }
            }

            return new SimilarityNumber(1 - minsum, 0, 1, 0);
        } else {
            throw new UnsupportedTypeException("Similarity measure expects feature of type HistogramFeature");
        }
    }
}
