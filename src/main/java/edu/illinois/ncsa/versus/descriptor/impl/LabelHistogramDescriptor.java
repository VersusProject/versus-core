package edu.illinois.ncsa.versus.descriptor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.illinois.ncsa.versus.descriptor.Descriptor;

public class LabelHistogramDescriptor implements Descriptor {
	private Map<String, Double> histogram;
	
	public LabelHistogramDescriptor() {
		histogram = new HashMap<String, Double>();
	}
	
	// ----------------------------------------------------------------------
	// Descriptor
	// ----------------------------------------------------------------------

	@Override
	public String getName() {
		return "Histogram of Labels";
	}

	@Override
	public String getType() {
		return this.getClass().toString();
	}
	
	// ----------------------------------------------------------------------
	// Histogram
	// ----------------------------------------------------------------------	
	public int getNumBins() {
		return histogram.size();
	}
	
	public Set<String> getLabels() {
		return histogram.keySet();
	}

	public Double getBin(String label) {
		return histogram.get(label);
	}
	
	public void put(String label, double value) {
		histogram.put(label, value);
	}
	
	public void putAll(Map<String, Double> map) {
		histogram.putAll(map);
	}

	public void increaseBin(String label) {
	    Double count = histogram.get(label);
		if (count == null) {
			count = new Double(1);
		} else {
			count = count + 1;
		}
		histogram.put(label, count);
	}

	public void normalize() {
	    Map<String, Double> orig = histogram;
	    histogram = new HashMap<String, Double>();
	    double total = 0;
	    for(Double d : orig.values()) {
	        total += d;
	    }
	    for(Entry<String, Double> entry : orig.entrySet()) {
	        histogram.put(entry.getKey(), entry.getValue() / total);
	    }
	}
}
