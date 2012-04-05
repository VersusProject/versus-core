/**
 *
 */
package edu.illinois.ncsa.versus.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import edu.illinois.ncsa.versus.adapter.Adapter;
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

    /**
     * Create an instance and load known extractors.
     */
    public CompareRegistry() {
        adapters = new HashMap<String, Adapter>();
        extractors = new HashMap<String, Extractor>();
        measures = new HashMap<String, Measure>();
        populateRegistry();
    }

    /**
     * Populate registry.
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

    public Adapter getAdapter(String adapterId) {
        return adapters.get(adapterId);
    }

    /**
     * Get all adapters ids known to the system.
     *
     * @return collection of adapters ids
     */
    public Collection<String> getAvailableAdaptersIds() {
        return adapters.keySet();
    }

    /**
     * Get all adapters known to the system.
     *
     * @return collection of adapters
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
     */
    public Collection<Adapter> getAvailableAdapters(Extractor extractor) {
        Collection<Adapter> ad = new HashSet<Adapter>();
        Set<Class<? extends Adapter>> supportedAdaptersTypes = extractor.supportedAdapters();
        for (Adapter adapter : adapters.values()) {
            if (supportedAdaptersTypes.contains(adapter.getClass())) {
                ad.add(adapter);
            } else {
                Class[] interfaces = adapter.getClass().getInterfaces();
                for (Class inter : interfaces) {
                    if (supportedAdaptersTypes.contains(inter)) {
                        ad.add(adapter);
                        break;
                    }
                }
            }
        }
        return ad;
    }

    public Extractor getExtractor(String extractorId) {
        return extractors.get(extractorId);
    }

    public Collection<String> getAvailableExtractorsId() {
        return extractors.keySet();
    }

    /**
     * Get all available extractors.
     *
     * @return a collection of extractors
     */
    public Collection<Extractor> getAvailableExtractors() {
        return extractors.values();
    }

    /**
     * Get all available extractors which support the given adapter
     *
     * @param adapter
     * @return
     */
    public Collection<Extractor> getAvailableExtractors(Adapter adapter) {
        Class[] interfaces = adapter.getClass().getInterfaces();
        Collection<Extractor> ex = new HashSet<Extractor>();
        for (Extractor extractor : extractors.values()) {
            Set<Class<? extends Adapter>> supportedAdaptersTypes = extractor.supportedAdapters();
            if (supportedAdaptersTypes.contains(adapter.getClass())) {
                ex.add(extractor);
            } else {
                for (Class inter : interfaces) {
                    if (supportedAdaptersTypes.contains(inter)) {
                        ex.add(extractor);
                        break;
                    }
                }
            }
        }
        return ex;
    }

    public Measure getMeasure(String measureId) {
        return measures.get(measureId);
    }

    public Collection<String> getAvailableMeasureId() {
        return measures.keySet();
    }

    /**
     * Get known measures.
     *
     * @return known measures
     */
    public Collection<Measure> getAvailableMeasures() {
        return measures.values();
    }

    /**
     * Determines if the registry support the comparison
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
