package edu.illinois.ncsa.versus.adapter.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.adapter.HasBytes;
import edu.illinois.ncsa.versus.adapter.StreamLoader;

/**
 * Get bytes from file.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class BytesAdapter implements HasBytes, FileLoader, StreamLoader {

	private File file;
    private InputStream stream;
	private static final Log log = LogFactory.getLog(BytesAdapter.class);

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
    public void load(InputStream stream) {
        this.stream = stream;
    }
    
	@Override
	public byte[] getBytes() throws IOException {
        if(stream == null) {
            if(file == null) {
                throw new RuntimeException("No adapter input specified.");
            }
			log.debug("Loading file " + file.getAbsolutePath());
            stream = new FileInputStream(file);
        }
        try {
            return IOUtils.toByteArray(stream);
        } finally {
            stream.close();
        }
	}
}
