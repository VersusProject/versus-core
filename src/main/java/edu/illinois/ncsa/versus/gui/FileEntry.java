/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.io.File;

/**
 * @author lmarini
 *
 */
public class FileEntry {

	private final File file;
	
	public FileEntry(File file) {
		this.file = file;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
}
