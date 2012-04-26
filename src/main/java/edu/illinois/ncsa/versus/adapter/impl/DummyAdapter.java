/**
 * 
 */
package edu.illinois.ncsa.versus.adapter.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.adapter.HasBytes;
import edu.illinois.ncsa.versus.utility.HasCategory;
import edu.illinois.ncsa.versus.utility.HasHelp;
import edu.illinois.ncsa.versus.utility.HelpProvider;

/**
 * @author lmarini
 * 
 */
public class DummyAdapter implements HasBytes, FileLoader, HasCategory, HasHelp {

	private static final long SLEEP = 5000;

	@Override
	public String getName() {
		return "Dummy Adapter";
	}

	@Override
	public List<String> getSupportedMediaTypes() {
		List<String> mediaTypes = new ArrayList<String>();
		mediaTypes.add("*/*");
        mediaTypes.add("text/plain");
		return mediaTypes;
	}

	@Override
	public void load(File file) throws IOException {
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public byte[] getBytes() throws IOException {
		return new byte[0];
	}

    @Override
    public String getCategory() {
        return "Dummy";
    }

    @Override
    public InputStream getHelpZipped() {
        return HelpProvider.getHelpZipped(DummyAdapter.class);
    }

    @Override
    public String getHelpSHA1() {
        return HelpProvider.getHelpSHA1(DummyAdapter.class);
    }

}
