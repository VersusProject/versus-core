package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;

/**
 * Execute extraction on a dedicated thread. Returns the descriptor when done.
 * 
 * @author Luigi Marini
 * 
 */
public class ExtractionCallable implements Callable<Descriptor> {
	private static Log log = LogFactory.getLog(ExtractionCallable.class);
	private final File file;
	private final Adapter adapter;
	private final Extractor extractor;

	public ExtractionCallable(File file, Adapter adapter, Extractor extractor) {
		this.file = file;
		this.adapter = adapter;
		this.extractor = extractor;
	}

	@Override
	public Descriptor call() throws Exception {
		if (adapter instanceof FileLoader) {
			FileLoader fileLoaderAdapter = (FileLoader) adapter;
			fileLoaderAdapter.load(file);
			Descriptor descriptor = extractor.extract(fileLoaderAdapter);
			log.debug("Descriptor created for file " + file.getAbsolutePath());
			return descriptor;
		} else {
			throw new UnsupportedTypeException(
					"This adapter cannot load files, " + adapter.getName());
		}
	}
}
