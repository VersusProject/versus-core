/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.search.Indexer;
import edu.illinois.ncsa.versus.search.SearchResult;

/**
 * A very simple multithreaded engine to build an index.
 * 
 * @author Luigi Marini
 * 
 */
public class IndexingEngine {

	private final ExecutorService threadPool;
	private static Log log = LogFactory.getLog(IndexingEngine.class);
	
	private  Adapter adapter;
	private  Extractor extractor;
	private Measure measure;

	private Indexer indexer;
	private int numDocs = 0;
	
	public IndexingEngine(Indexer indexer, int numThreads) { 
		this.indexer = indexer;
		threadPool = Executors.newFixedThreadPool(numThreads);
	}
	
	public IndexingEngine(int numThreads){
		threadPool = Executors.newFixedThreadPool(numThreads);
		
	}
	public void setAdapter(Adapter adapter){
		this.adapter=adapter;
	  }
	
	public void setExtractor(Extractor extractor){
		
	   this.extractor=extractor;
		
	}
	
	public void setMeasure(Measure measure){
	    this.measure=measure;	
		
	}
	
	public void setIndexer(Indexer indexer){
		this.indexer=indexer;
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
			log.debug("Inside addDocument");
			ExtractionCallable extractionCallable = new ExtractionCallable(file,
				getAdapter(), getExtractor());
			Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
			getIndexer().addDescriptor(descriptor.get(),file.getName());
			numDocs++;
			//log.debug("Document added to index, " + file.getAbsolutePath());
	}

	public void addDocument(File file, Adapter adapter,Extractor extractor,Indexer indexer) throws InterruptedException,
	ExecutionException {
		log.debug("Inside addDocument");
		ExtractionCallable extractionCallable = new ExtractionCallable(file,
		adapter, extractor);
		Future<Descriptor> descriptor = getThreadPool().submit(
		extractionCallable);
		indexer.addDescriptor(descriptor.get(),file.getName());
		numDocs++;
		//log.debug("Document added to index, " + file.getAbsolutePath());
	}
	
	public List<SearchResult> queryIndex(File file) throws InterruptedException,ExecutionException{
		
		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				getAdapter(), getExtractor());
		
		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		//log.debug("Signature getIndex="+descriptor.get().toString());
         return getIndexer().query(descriptor.get(), getMeasure());
	}
	

public List<SearchResult> queryIndex(File file,Adapter adapter,Extractor extractor,Measure measure,Indexer indexer) throws InterruptedException,ExecutionException{
	
	ExtractionCallable extractionCallable = new ExtractionCallable(file,
			adapter, extractor);
	
	Future<Descriptor> descriptor = getThreadPool().submit(
			extractionCallable);
	log.debug("Indexing Engine: queryIndex");
	indexer.setMeasure(measure);
	return indexer.query(descriptor.get());
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
	public Measure getMeasure(){
		return measure;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public int getNumDocs() {
		return numDocs;
	}
}
