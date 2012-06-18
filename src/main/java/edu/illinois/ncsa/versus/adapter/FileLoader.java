/**
 * 
 */
package edu.illinois.ncsa.versus.adapter;

import java.io.File;
import java.io.IOException;

import edu.illinois.ncsa.versus.VersusException;

/**
 * Adapter is capable of loading a file.
 * 
 * @author Luigi Marini
 * 
 */
public interface FileLoader extends Adapter {

	/**
	 * Load a file from disk in the data structure specific to the adapter.
	 * 
	 * @param file
	 *            file to load
	 * @throws IOException
	 */
	public void load(File file) throws IOException, VersusException;
}
