/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Only show jpegs.
 * 
 * @author Luigi Marini
 * 
 */
public class JpgFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File arg0, String arg1) {

		if (arg1.toLowerCase().endsWith(".jpeg")
				|| arg1.toLowerCase().endsWith(".jpg")) {
			return true;
		}
		return false;
	}

}
