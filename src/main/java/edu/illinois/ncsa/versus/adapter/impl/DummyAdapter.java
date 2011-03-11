/**
 * 
 */
package edu.illinois.ncsa.versus.adapter.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.ncsa.versus.adapter.FileLoader;

/**
 * @author lmarini
 * 
 */
public class DummyAdapter implements FileLoader {

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

}