/**
 * 
 */
package edu.illinois.ncsa.versus.search;

import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.measure.Measure;

/**
 * Create and query an index for information retrieval.
 * 
 * @author Luigi Marini
 * 
 */
public interface Indexer {

	/**
	 * Add a document to the index. This might or might not update the index.
	 * Always use {@link commit} to be sure.
	 * 
	 * @param document
	 *            The descriptor representing the document.
	 */
	void addDescriptor(Descriptor document);

	/**
	 * Build index.
	 */
	void commit();

	/**
	 * Query index using a query and a proximity measure.
	 * 
	 * @param query
	 *            Descriptor representing the query.
	 * @param measure
	 *            Measure used to find the distance between query and documents.
	 */
	void query(Descriptor query, Measure measure);
}
