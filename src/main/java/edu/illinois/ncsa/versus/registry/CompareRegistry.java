/**
 * 
 */
package edu.illinois.ncsa.versus.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import sun.misc.Service;
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
	 * A map between feature ids and extractors capable of producing that
	 * feature type
	 **/
	private final Map<String, Collection<Extractor>> featureToExtractorsMap;

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
		featureToExtractorsMap = new HashMap<String, Collection<Extractor>>();
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
		Iterator<Adapter> iter = Service.providers(Adapter.class);
		while (iter.hasNext()) {
			Adapter adapter = iter.next();
			adapters.add(adapter);
		}

		// measures
		Iterator<Measure> iterMeasure = Service.providers(Measure.class);
		while (iterMeasure.hasNext()) {
			Measure measure = iterMeasure.next();
			measures.add(measure);
		}

		// array feature extractors
		Iterator<Extractor> iterExtractor = Service.providers(Extractor.class);
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
	public Collection<String> getAvailableFeatures() {
		return featureToExtractorsMap.keySet();
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
	 * Given the key of a feature, return an instance of that feature.
	 * 
	 * @param key
	 *            of feature
	 * @return an instance of a feature
	 */
	public Descriptor getFeature(String key) {
		return null;
	}

	/**
	 * Given a feature type return all extractors know to extract this type.
	 * 
	 * @param string
	 *            a unique string identifying the feature type
	 * @return a collection of extractors
	 * @deprecated
	 */
	@Deprecated
	public Collection<Extractor> getAvailableExtractors(String featureType) {
		return featureToExtractorsMap.get(featureType);
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
	 * Get all adapters known to the system.
	 * 
	 * @return collection of adapters
	 */
	public Collection<Adapter> getAvailableAdapters() {
		return adapters;
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
