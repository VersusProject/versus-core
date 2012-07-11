/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.search.Indexer;

/**
 * A very simple multithreaded engine to build an index.
 * 
 * @author Luigi Marini
 * 
 */
public class IndexingEngine {

	private final ExecutorService threadPool;
	private static Log log = LogFactory.getLog(IndexingEngine.class);
	private final Adapter adapter;
	private final Extractor extractor;
	private final Indexer indexer;
	private int numDocs = 0;

	public IndexingEngine(Adapter adapter, Extractor extractor,
			Indexer indexer, int numThreads) {
		this.adapter = adapter;
		this.extractor = extractor;
		this.indexer = indexer;
		threadPool = Executors.newFixedThreadPool(numThreads);
	}

	/**
	 * Execute each comparison in its own thread.
	 * 
	 * @param job
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void addDocument(File file) throws InterruptedException,
			ExecutionException {
		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				getAdapter(), getExtractor());
		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		getIndexer().addDescriptor(descriptor.get());
		numDocs++;
		log.debug("Document added to index, " + file.getAbsolutePath());
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public Adapter getAdapter() {
		return adapter;
	}

	public Extractor getExtractor() {
		return extractor;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public int getNumDocs() {
		return numDocs;
	}
}
