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
    public String getFeatureType() {
        return LabelHistogramDescriptor.class.getName();
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