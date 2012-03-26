/**
 * 
 */
package edu.illinois.ncsa.versus.extract.impl;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.HasBytes;
import edu.illinois.ncsa.versus.adapter.impl.DummyAdapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.utility.HasCategory;
import edu.illinois.ncsa.versus.utility.HasHelp;
import edu.illinois.ncsa.versus.utility.HelpProvider;

/**
 * @author lmarini
 * 
 */
public class DummyExtractor implements Extractor, HasCategory, HasHelp {

	private static final long SLEEP = 10000;

	@Override
	public Descriptor extract(Adapter adapter) throws Exception {
		if (adapter instanceof DummyAdapter) {
			Thread.sleep(SLEEP);
			return new DummyFeature();
		} else {
			throw new UnsupportedTypeException();
		}
	}

	@Override
	public Adapter newAdapter() {
		return null;
	}

	@Override
	public String getName() {
		return "Dummy Extractor";
	}

	@Override
	public Set<Class<? extends Adapter>> supportedAdapters() {
		Set<Class<? extends Adapter>> adapters = new HashSet<Class<? extends Adapter>>();
		adapters.add(HasBytes.class);
		return adapters;
	}

	@Override
	public Class<? extends Descriptor> getFeatureType() {
		return DummyFeature.class;
	}

	@Override
	public boolean hasPreview() {
		return false;
	}

	@Override
	public String previewName(){
		return null;
	}

    @Override
    public String getCategory() {
        return "Dummy";
    }

    @Override
    public InputStream getHelpZipped() {
        return HelpProvider.getHelpZipped(DummyExtractor.class);
    }

    @Override
    public String getHelpSHA1() {
        return HelpProvider.getHelpSHA1(DummyExtractor.class);
    }
}
