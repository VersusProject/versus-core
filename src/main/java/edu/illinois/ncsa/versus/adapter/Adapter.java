package edu.illinois.ncsa.versus.adapter;

import java.util.List;


/**
 * Marker interface for adapters. Adapters wrap and existing data structure for
 * a particular document and provide ways to retrieve the documents contents. If
 * possible, an adapter should be reversible, there should not be loss of
 * information.
 * 
 * @author Luigi Marini
 * 
 */
public interface Adapter {

	/**
	 * Pretty name of adapter.
	 * 
	 * @return pretty name
	 */
	String getName();
	
	/**
	 * List of supported media types.
	 * 
	 * @return list of MIME types
	 */
	List<String> getSupportedMediaTypes();
}
