/**
 * 
 */
package edu.illinois.ncsa.versus.registry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

	/**
	 * Collection of known measures.
	 */
	private final Collection<Measure> measures;

	/**
	 * Collection of known features.
	 */
	private final Collection<Descriptor> features;

	/**
	 * Collection of known adapters.
	 */
	private final Collection<Adapter> adapters;

	private final Collection<Extractor> extractors;

	/**
	 * Create an instance and load known extractors.
	 */
	public CompareRegistry() {
		measures = new HashSet<Measure>();
		features = new HashSet<Descriptor>();
		extractors = new HashSet<Extractor>();
		adapters = new HashSet<Adapter>();
		populateRegistry();
	}

	/**
	 * Populate registry.
	 * 
	 * TODO switch to dynamic loading
	 */
	private void populateRegistry() {
		Iterator<Adapter> iter = ServiceLoader.load(Adapter.class).iterator();
		while (iter.hasNext()) {
			Adapter adapter = iter.next();
			adapters.add(adapter);
		}

		// measures
		Iterator<Measure> iterMeasure = ServiceLoader.load(Measure.class).iterator();
		while (iterMeasure.hasNext()) {
			Measure measure = iterMeasure.next();
			measures.add(measure);
		}

		// array feature extractors
		Iterator<Extractor> iterExtractor = ServiceLoader.load(Extractor.class).iterator();
		while (iterExtractor.hasNext()) {
			Extractor extractor = iterExtractor.next();
			extractors.add(extractor);
		}
	}

	/**
	 * Get list of keys for features available on the classpath.
	 * 
	 * @return keys of available features
	 */
	public Collection<Descriptor> getAvailableFeatures() {
		return features;
	}

	/**
	 * Get known measures.
	 * 
	 * @return known measures
	 */
	public Collection<Measure> getAvailableMeasures() {
		return measures;
	}

	/**
	 * Get all available extractors.
	 * 
	 * @return a collection of extractors
	 */
	public Collection<Extractor> getAvailableExtractors() {
		return extractors;
	}
    
    /**
     * Get all available extractors which support the given adapter
     * @param adapter
     * @return 
     */
    public Collection<Extractor> getAvailableExtractors(Adapter adapter) {
        Class[] interfaces = adapter.getClass().getInterfaces();
        Collection<Extractor> ex = new HashSet<Extractor>();
        for(Extractor extractor : extractors) {
            Set<Class<? extends Adapter>> supportedAdaptersTypes = extractor.supportedAdapters();
            if(supportedAdaptersTypes.contains(adapter.getClass())) {
                ex.add(extractor);
            } else {
                for(Class inter : interfaces) {
                    if(supportedAdaptersTypes.contains(inter)) {
                        ex.add(extractor);
                        break;
                    }
                } 
            }
        }
        return ex;
    }

	/**
	 * Get all adapters known to the system.
	 * 
	 * @return collection of adapters
	 */
	public Collection<Adapter> getAvailableAdapters() {
		return adapters;
	}
    
    /**
     * Get all available adapters supported by the specified extractor
     * @param extractor
     * @return 
     */
    public Collection<Adapter> getAvailableAdapters(Extractor extractor) {
        Collection<Adapter> ad = new HashSet<Adapter>();
        Set<Class<? extends Adapter>> supportedAdaptersTypes = extractor.supportedAdapters();
        for(Adapter adapter : adapters) {
            if(supportedAdaptersTypes.contains(adapter.getClass())) {
                ad.add(adapter);
            } else {
                Class[] interfaces = adapter.getClass().getInterfaces();
                for(Class inter : interfaces) {
                    if(supportedAdaptersTypes.contains(inter)) {
                        ad.add(adapter);
                        break;
                    }
                }
            }
        }
        return ad;
    }

	/**
	 * Get all adapters ids known to the system.
	 * 
	 * @return collection of adapters ids
	 */
	public Collection<String> getAvailableAdaptersIds() {
		Collection<String> adaptersIds = new HashSet<String>();
		for (Adapter adapter : adapters) {
			adaptersIds.add(adapter.getClass().getName());
		}
		return adaptersIds;
	}
    
}
