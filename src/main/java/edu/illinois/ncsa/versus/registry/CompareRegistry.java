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
package edu.illinois.ncsa.versus.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

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

	/**
	 * Collection of known extractors.
	 */
	private final Collection<Extractor> extractors;

	private static ServiceLoader<Adapter> adapterLoader = ServiceLoader
			.load(Adapter.class);

	private static ServiceLoader<Extractor> extractorLoader = ServiceLoader
			.load(Extractor.class);

	private static ServiceLoader<Measure> measureLoader = ServiceLoader
			.load(Measure.class);

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
			if (adapter.getClass().getName().equals(adapterId))
				return adapter;
		}
		return null;
	}

	/**
	 * All extractor available on the classpath.
	 * 
	 * @param adapterId
	 * @return
	 */
	public static List<Extractor> getExtractor() {
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
			if (extractor.getClass().getName().equals(adapterId))
				return extractor;
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
			if (measure.getClass().getName().equals(measureId))
				return measure;
		}
		return null;
	}

	/**
	 * Create an instance and load known extractors.
	 * 
	 * @Deprecated Use static methods instead
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
	 * 
	 * @Deprecated Use static methods instead
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
	 * 
	 * @Deprecated Use static methods instead
	 */
	public Collection<String> getAvailableFeatures() {
		return featureToExtractorsMap.keySet();
	}

	/**
	 * Get known measures.
	 * 
	 * @return known measures
	 * 
	 * @Deprecated Use static methods instead
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
	 * 
	 * @Deprecated Use static methods instead
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
	 * 
	 * @Deprecated Use static methods instead
	 */
	@Deprecated
	public Collection<Extractor> getAvailableExtractors(String featureType) {
		return featureToExtractorsMap.get(featureType);
	}

	/**
	 * Get all available extractors.
	 * 
	 * @return a collection of extractors
	 * 
	 * @Deprecated Use static methods instead
	 */
	public Collection<Extractor> getAvailableExtractors() {
		return extractors;
	}

	/**
	 * Get all adapters known to the system.
	 * 
	 * @return collection of adapters
	 * 
	 * @Deprecated Use static methods instead
	 */
	public Collection<Adapter> getAvailableAdapters() {
		return adapters;
	}

	/**
	 * Get ids of all known adapters.
	 * 
	 * @return collection of adapters ids
	 * 
	 * @Deprecated Use static methods instead
	 */
	public Collection<String> getAvailableAdaptersIds() {
		Collection<String> adaptersIds = new HashSet<String>();
		for (Adapter adapter : adapters) {
			adaptersIds.add(adapter.getClass().getName());
		}
		return adaptersIds;
	}

	/**
	 * Get ids of all known extractors.
	 * 
	 * @return collection of extractors ids
	 * 
	 * @Deprecated Use static methods instead
	 */
	public Collection<String> getAvailableExtractorsIds() {
		Collection<String> extractorIds = new HashSet<String>();
		for (Extractor extractor : extractors) {
			extractorIds.add(extractor.getClass().getName());
		}
		return extractorIds;
	}

	/**
	 * Get ids of all known measures.
	 * 
	 * @return collection of measures ids
	 * 
	 * @Deprecated Use static methods instead
	 */
	public Collection<String> getAvailableMeasuresIds() {
		Collection<String> measuresIds = new HashSet<String>();
		for (Measure measure : measures) {
			measuresIds.add(measure.getClass().getName());
		}
		return measuresIds;
	}
}
