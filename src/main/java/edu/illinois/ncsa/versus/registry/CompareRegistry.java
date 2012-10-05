/**
 * *****************************************************************************
 * Copyright (c) 2010 University of Illinois. All rights reserved.
 *
 * Developed by: National Center for Supercomputing Applications (NCSA)
 * University of Illinois http://www.ncsa.illinois.edu/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * with the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimers. - Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimers in the documentation and/or other materials
 * provided with the distribution. - Neither the names of National Center for
 * Supercomputing Applications, University of Illinois, nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * Software without specific prior written permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS WITH
 * THE SOFTWARE.
 * ****************************************************************************
 */
/**
 *
 */
package edu.illinois.ncsa.versus.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;

/**
 * Registry of available extractors, features, measures.
 *
 * @author Luigi Marini
 *
 */
public class CompareRegistry {

    private final Map<String, Adapter> adapters;

    private final Map<String, Extractor> extractors;

    private final Map<String, Measure> measures;

    private static ServiceLoader<Adapter> adapterLoader = ServiceLoader.load(Adapter.class);

    private static ServiceLoader<Extractor> extractorLoader = ServiceLoader.load(Extractor.class);

    private static ServiceLoader<Measure> measureLoader = ServiceLoader.load(Measure.class);

    /**
     * All adapters available on the classpath.
     *
     * @param adapterId
     * @return
     */
    public static List<Adapter> getAdapters() {
        List<Adapter> adapters = new ArrayList<Adapter>();
        Iterator<Adapter> iterator = adapterLoader.iterator();
        while (iterator.hasNext()) {
            adapters.add(iterator.next());
        }
        return adapters;
    }

    /**
     * Retrieve an instance of a particular adapter.
     *
     * @param adapterId
     * @return null if instance not found
     */
    public static Adapter getAdapter(String adapterId) {
        for (Adapter adapter : adapterLoader) {
            if (adapter.getClass().getName().equals(adapterId)) {
                return adapter;
            }
        }
        return null;
    }

    /**
     * All extractor available on the classpath.
     *
     * @param adapterId
     * @return
     */
    public static List<Extractor> getExtractors() {
        List<Extractor> extractors = new ArrayList<Extractor>();
        Iterator<Extractor> iterator = extractorLoader.iterator();
        while (iterator.hasNext()) {
            extractors.add(iterator.next());
        }
        return extractors;
    }

    /**
     * Retrieve an instance of a particular extractor.
     *
     * @param extractorId
     * @return null if instance not found
     */
    public static Extractor getExtractor(String adapterId) {
        for (Extractor extractor : extractorLoader) {
            if (extractor.getClass().getName().equals(adapterId)) {
                return extractor;
            }
        }
        return null;
    }

    /**
     * All measures available on the classpath.
     *
     * @param measureId
     * @return
     */
    public static List<Measure> getMeasures() {
        List<Measure> measures = new ArrayList<Measure>();
        Iterator<Measure> iterator = measureLoader.iterator();
        while (iterator.hasNext()) {
            measures.add(iterator.next());
        }
        return measures;
    }

    /**
     * Retrieve an instance of a particular measure.
     *
     * @param measureId
     * @return null if instance not found
     */
    public static Measure getMeasure(String measureId) {
        for (Measure measure : measureLoader) {
            if (measure.getClass().getName().equals(measureId)) {
                return measure;
            }
        }
        return null;
    }

    /**
     * Create an instance and load known extractors.
     *
     * @Deprecated Use static methods instead
     */
    public CompareRegistry() {
        adapters = new HashMap<String, Adapter>();
        extractors = new HashMap<String, Extractor>();
        measures = new HashMap<String, Measure>();
        populateRegistry();
    }

    /**
     * Populate registry.
     *
     * @Deprecated Use static methods instead
     */
    private void populateRegistry() {
        Iterator<Adapter> iter = ServiceLoader.load(Adapter.class).iterator();
        while (iter.hasNext()) {
            Adapter adapter = iter.next();
            adapters.put(adapter.getClass().getName(), adapter);
        }

        // measures
        Iterator<Measure> iterMeasure = ServiceLoader.load(Measure.class).iterator();
        while (iterMeasure.hasNext()) {
            Measure measure = iterMeasure.next();
            measures.put(measure.getClass().getName(), measure);
        }

        // array feature extractors
        Iterator<Extractor> iterExtractor = ServiceLoader.load(Extractor.class).iterator();
        while (iterExtractor.hasNext()) {
            Extractor extractor = iterExtractor.next();
            extractors.put(extractor.getClass().getName(), extractor);
        }
    }

    /**
     * Get all adapters ids known to the system.
     *
     * @return collection of adapters ids
     *
     * @Deprecated Use static methods instead
     */
    public Collection<String> getAvailableAdaptersIds() {
        return adapters.keySet();
    }

    /**
     * Get all adapters known to the system.
     *
     * @return collection of adapters
     *
     * @Deprecated Use static methods instead
     */
    public Collection<Adapter> getAvailableAdapters() {
        return adapters.values();
    }

    public Collection<String> getAvailableAdaptersIds(Extractor extractor) {
        Collection<Adapter> availableAdapters = getAvailableAdapters(extractor);
        Collection<String> result = new HashSet<String>(availableAdapters.size());
        for (Adapter adapter : availableAdapters) {
            result.add(adapter.getClass().getName());
        }
        return result;
    }

    /**
     * Get all available adapters supported by the specified extractor
     *
     * @param extractor
     * @return
     *
     * @Deprecated Use static methods instead
     */
    public Collection<Adapter> getAvailableAdapters(Extractor extractor) {
        Collection<Adapter> ad = new HashSet<Adapter>();
        Set<Class<? extends Adapter>> supportedAdaptersTypes =
                extractor.supportedAdapters();
        for (Adapter adapter : adapters.values()) {
            Class<? extends Adapter> adapterClass = adapter.getClass();
            for (Class<? extends Adapter> supportedAdapter : supportedAdaptersTypes) {
                if (supportedAdapter.isAssignableFrom(adapterClass)) {
                    ad.add(adapter);
                    break;
                }
            }
        }
        return ad;
    }

    public Collection<String> getAvailableAdaptersId(Extractor extractor) {
        Collection<Adapter> availableAdapters = getAvailableAdapters(extractor);
        HashSet<String> result = new HashSet<String>(availableAdapters.size());
        for (Adapter ad : availableAdapters) {
            result.add(ad.getClass().getName());
        }
        return result;
    }

    public Collection<String> getAvailableExtractorsId() {
        return extractors.keySet();
    }

    /**
     * Get all available extractors.
     *
     * @return a collection of extractors
     *
     * @Deprecated Use static methods instead
     */
    public Collection<Extractor> getAvailableExtractors() {
        return extractors.values();
    }

    /**
     * Get all available extractors which support the given adapter
     *
     * @param adapter
     * @return
     * @Deprecated Use static methods instead
     */
    public Collection<Extractor> getAvailableExtractors(Adapter adapter) {
        Class<? extends Adapter> adapterClass = adapter.getClass();
        Collection<Extractor> ex = new HashSet<Extractor>();
        for (Extractor extractor : extractors.values()) {
            Set<Class<? extends Adapter>> supportedAdaptersTypes =
                    extractor.supportedAdapters();
            for (Class<? extends Adapter> supportedAdapter : supportedAdaptersTypes) {
                if (supportedAdapter.isAssignableFrom(adapterClass)) {
                    ex.add(extractor);
                    break;
                }
            }
        }
        return ex;
    }

    public Collection<String> getAvailableExtractorsId(Adapter adapter) {
        Collection<Extractor> availableExtractors = getAvailableExtractors(adapter);
        Collection<String> result = new HashSet<String>(availableExtractors.size());
        for (Extractor ex : availableExtractors) {
            result.add(ex.getClass().getName());
        }
        return result;
    }

    public Collection<Extractor> getAvailableExtractors(Measure measure) {
        Set<Class<? extends Descriptor>> supportedFeaturesTypes = measure.supportedFeaturesTypes();
        Collection<Extractor> ex = new HashSet<Extractor>();
        for (Extractor extractor : extractors.values()) {
            if (supportedFeaturesTypes.contains(extractor.getFeatureType())) {
                ex.add(extractor);
            }
        }
        return ex;
    }

    public Collection<String> getAvailableMeasuresId() {
        return measures.keySet();
    }

    /**
     * Get known measures.
     *
     * @return known measures
     *
     * @Deprecated Use static methods instead
     */
    public Collection<Measure> getAvailableMeasures() {
        return measures.values();
    }

    /**
     * Determines if the registry support the comparison
     *
     * @param adapterId
     * @param extractorId
     * @param measureId
     * @return
     */
    public boolean supportComparison(String adapterId, String extractorId,
            String measureId) {
        return adapters.containsKey(adapterId)
                && extractors.containsKey(extractorId)
                && measures.containsKey(measureId);
    }
}
