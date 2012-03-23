/**
 * 
 */
package edu.illinois.ncsa.versus.adapter.impl;

import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.adapter.HasBytes;
import edu.illinois.ncsa.versus.category.HasCategory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmarini
 * 
 */
public class DummyAdapter implements HasBytes, FileLoader, HasCategory {

	private static final long SLEEP = 5000;

	@Override
	public String getName() {
		return "Dummy Adapter";
	}

	@Override
	public List<String> getSupportedMediaTypes() {
		List<String> mediaTypes = new ArrayList<String>();
		mediaTypes.add("*/*");
		return mediaTypes;
	}

	@Override
	public void load(File file) throws IOException {
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
