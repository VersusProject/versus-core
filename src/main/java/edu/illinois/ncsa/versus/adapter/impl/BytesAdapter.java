package edu.illinois.ncsa.versus.adapter.impl;

import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.adapter.HasBytes;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Get bytes from file.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class BytesAdapter implements HasBytes, FileLoader {

	private File file;
	private static Log log = LogFactory.getLog(BytesAdapter.class);

	@Override
	public String getName() {
		return "Bytes";
	}

	@Override
	public List<String> getSupportedMediaTypes() {
		List<String> mediaTypes = new ArrayList<String>();
		mediaTypes.add("*/*");
		return mediaTypes;
	}

	@Override
	public void load(File file) throws IOException {
		this.file = file;
	}

	@Override
	public byte[] getBytes() throws IOException {
		try {
			log.debug("Loading file " + file.getAbsolutePath());
			InputStream is = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			return bytes;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[0];
	}
}
