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
public class LabelHistogramEuclidianDistanceMeasure implements Measure {

    @Override
    public SimilarityPercentage normalize(Similarity similarity) {
        return new SimilarityPercentage(1 - similarity.getValue());
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
    public Class<LabelHistogramEuclidianDistanceMeasure> getType() {
        return LabelHistogramEuclidianDistanceMeasure.class;
    }

    // correlation

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
            
            // compute distance
            double sum = 0;
            for (String s : labels) {
                Double b1 = lhd1.getBin(s);
                Double b2 = lhd2.getBin(s);
                if (b1 == null) {
                    sum += b2 * b2;
                } else if (b2 == null) {
                    sum += b1 * b1;
                } else {
                    sum += (b1 - b2) * (b1 - b2);
                }
            }

            return new SimilarityNumber(Math.sqrt(sum), 0, 1, 0);
        } else {
            throw new UnsupportedTypeException();
        }
    }
}