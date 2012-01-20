/**
 * 
 */
package edu.illinois.ncsa.versus.engine;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.registry.CompareRegistry;
import java.util.Set;

/**
 * Simple engine to compare a list of files with eachother.
 * 
 * @author Luigi Marini
 * @deprecated
 */
@Deprecated
public class SimpleEngine {

    private File[] files;

    /** Commons logging **/
    private static Log log = LogFactory.getLog(SimpleEngine.class);

    public SimpleEngine() {
    }

    public void add(File... files) {
        this.files = files;
    }

    public <S> Collection<S> compareAll(File[] files,
            Measure similarityMeasure) {

        Collection<S> similarityValues = new HashSet<S>();

        Extractor chooseExtractor = chooseExtractor(similarityMeasure);

        if (chooseExtractor != null) {
            Collection<Descriptor> features = extractFiles(files, chooseExtractor);

            for (Descriptor feature1 : features) {
                for (Descriptor feature2 : features) {
                    if (feature1 != feature2) {
                        // similarityMeasure.compare(feature1, feature2);
                    }
                }
            }

        }
        return similarityValues;
    }

    /**
     * Extract features from files.
     * 
     * @param files
     * @param extractor
     */
    private Collection<Descriptor> extractFiles(File[] files, Extractor extractor) {

        Collection<Descriptor> features = new HashSet<Descriptor>();

        for (File file : files) {
            FileLoader newAdapter = (FileLoader) extractor.newAdapter();
            try {
                newAdapter.load(file);
            } catch (IOException e) {
                log.error("Error loading image" + e);
            }
            try {
                Descriptor feature = extractor.extract(newAdapter);
                features.add(feature);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return features;
    }

    /**
     * Return the first available extractor for a particular measure.
     * 
     * @param similarityMeasure
     * @return
     */
    private Extractor chooseExtractor(Measure similarityMeasure) {

        CompareRegistry registry = new CompareRegistry();

        Set<Class<? extends Descriptor>> features = similarityMeasure.supportedFeaturesTypes();
        Descriptor feature = null;

        Collection<Descriptor> availablesFeatures = registry.getAvailableFeatures();
        Iterator<Descriptor> featureIterator = availablesFeatures.iterator();
        while (featureIterator.hasNext()) {
            feature = featureIterator.next();
            if (features.contains(feature.getClass())) {
                log.debug("Using feature " + feature.getName());
                break;
            }
            feature = null;
        }
        if (feature == null) {
            return null;
        }

        Collection<Extractor> availableExtractors = registry.getAvailableExtractors();
        Iterator<Extractor> iterator = availableExtractors.iterator();

        while (iterator.hasNext()) {
            Extractor extractor = iterator.next();
            if (feature.getClass().equals(extractor.getFeatureType())) {
                log.debug("Using extractor " + extractor.getName());
                return extractor;
            }
        }

        return null;
    }
}
