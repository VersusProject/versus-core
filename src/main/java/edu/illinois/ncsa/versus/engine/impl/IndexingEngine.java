/**
 * 
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

//import edu.illinois.ncsa.versus.census.CensusIndexer1;
/**
 * A very simple multithreaded engine to build an index.
 * 
 * @author Luigi Marini
 * 
 */
public class IndexingEngine {

	private final ExecutorService threadPool;
	private static Log log = LogFactory.getLog(IndexingEngine.class);

	private Adapter adapter;
	private Extractor extractor;
	private Measure measure;

	private Indexer indexer;
	private int numDocs = 0;

	public IndexingEngine(Indexer indexer, int numThreads) {
		this.indexer = indexer;
		threadPool = Executors.newFixedThreadPool(numThreads);
	}

	public IndexingEngine(Adapter adapter, Extractor extractor,
			Indexer indexer, int numThreads) {
		this.indexer = indexer;
		this.adapter = adapter;
		this.extractor = extractor;
		threadPool = Executors.newFixedThreadPool(numThreads);
	}

	public IndexingEngine(int numThreads) {
		threadPool = Executors.newFixedThreadPool(numThreads);

	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	public void setExtractor(Extractor extractor) {

		this.extractor = extractor;

	}

	public void setMeasure(Measure measure) {
		this.measure = measure;

	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
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
		getIndexer().addDescriptor(descriptor.get(), file.getName());
		numDocs++;
		// log.debug("Document added to index, " + file.getAbsolutePath());
	}

	public void addDocument(File file, Adapter adapter, Extractor extractor,
			Indexer indexer) throws InterruptedException, ExecutionException {
		log.debug("Inside addDocument");
		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				adapter, extractor);
		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		indexer.addDescriptor(descriptor.get(), file.getName());
		numDocs++;
		// log.debug("Document added to index, " + file.getAbsolutePath());
	}

	public void addDocument(File file, String adapterId, String extractorId,
			Indexer indexer) throws InterruptedException, ExecutionException {
		log.debug("Inside addDocument");

		Adapter adapter = createAdapterInstance(adapterId);
		Extractor extractor = createExtractorInstance(extractorId);
		// Indexer indexer=createIndexerInstance(indexerId);
		// indexer.setId(id);

		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				adapter, extractor);
		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		indexer.addDescriptor(descriptor.get(), file.getName());
		numDocs++;
		// log.debug("Document added to index, " + file.getAbsolutePath());
	}

	public List<SearchResult> queryIndex(File file)
			throws InterruptedException, ExecutionException {

		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				getAdapter(), getExtractor());

		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		// log.debug("Signature getIndex="+descriptor.get().toString());

		FileInputStream in;
		try {
			in = new FileInputStream(
					new File(
							"/Users/smruti/NCSAResearch/workspace-versus/censusindex.txt"));
			ObjectInputStream s = new ObjectInputStream(in);
			Indexer indexerRead = (Indexer) s.readObject();
			// indexerRead.getCluster().identifiers;

			/*
			 * for(String id: indexerRead.getCluster().identifiers ){
			 * System.out.println("Identifiers "+id); }
			 */
			return indexerRead.query(descriptor.get(), getMeasure());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return indexerRead.query(descriptor.get(), getMeasure());
		return null;
		// return getIndexer().query(descriptor.get(), getMeasure());
	}

	public List<SearchResult> queryIndex(File file, Adapter adapter,
			Extractor extractor, Measure measure, Indexer indexer)
			throws InterruptedException, ExecutionException {

		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				adapter, extractor);

		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		log.debug("Indexing Engine: queryIndex");
		indexer.setMeasure(measure);
		return indexer.query(descriptor.get(), measure);
	}

	public List<SearchResult> queryIndex(File file, String adapterId,
			String extractorId, String measureId, Indexer indexer)
			throws InterruptedException, ExecutionException {

		Adapter adapter = createAdapterInstance(adapterId);
		Extractor extractor = createExtractorInstance(extractorId);
		Measure measure = createMeasureInstance(measureId);
		ExtractionCallable extractionCallable = new ExtractionCallable(file,
				adapter, extractor);

		Future<Descriptor> descriptor = getThreadPool().submit(
				extractionCallable);
		log.debug("Indexing Engine: queryIndex");
		indexer.setMeasure(measure);
		return indexer.query(descriptor.get(), measure);
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

	public Measure getMeasure() {
		return measure;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public int getNumDocs() {
		return numDocs;
	}

	public Adapter createAdapterInstance(String adapterID) {

		try {
			Adapter adapter = (Adapter) Class.forName(adapterID).newInstance();
			return adapter;
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return null;

	}

	public Extractor createExtractorInstance(String extractorID) {
		try {
			Extractor extractor = (Extractor) Class.forName(extractorID)
					.newInstance();
			return extractor;
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return null;

	}

	public Measure createMeasureInstance(String measureID) {

		try {
			Measure measure = (Measure) Class.forName(measureID).newInstance();
			return measure;
		} catch (InstantiationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IllegalAccessException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		return null;

	}

	public Indexer createIndexerInstance(String indexerID) {
		/*
		 * try {
		 * 
		 * //Indexer indexer = (Indexer) Class.forName(indexerID).newInstance();
		 * return indexer;
		 * 
		 * } catch (InstantiationException e3) {
		 * 
		 * e3.printStackTrace(); } catch (IllegalAccessException e3) {
		 * 
		 * e3.printStackTrace(); } catch (ClassNotFoundException e3) {
		 * 
		 * e3.printStackTrace(); }
		 */
		return null;
	}

}
