/**
 * 
 */
package edu.illinois.ncsa.versus.extract.impl;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.impl.DummyAdapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature2;
import edu.illinois.ncsa.versus.extract.Extractor;

/**
 * @author lmarini
 * 
 */
public class DummyExtractor2 implements Extractor {

	private static final long SLEEP = 10000;

	@Override
	public Descriptor extract(Adapter adapter) throws Exception {
		if (adapter instanceof DummyAdapter) {
			Thread.sleep(SLEEP);
			return new DummyFeature2();
		} else {
			throw new UnsupportedTypeException();
		}
	}

	@Override
	public Adapter newAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Dummy Extractor 2";
	}

	@Override
	public Set<Class<? extends Adapter>> supportedAdapters() {
		Set<Class<? extends Adapter>> adapters = new HashSet<Class<? extends Adapter>>();
		adapters.add(DummyAdapter.class);
		return adapters;
	}

	@Override
	public Class<? extends Descriptor> getFeatureType() {
		return DummyFeature2.class;
	}

	@Override
	public boolean hasPreview() {
		return false;
	}

	@Override
	public String previewName(){
		return null;
	}
}
