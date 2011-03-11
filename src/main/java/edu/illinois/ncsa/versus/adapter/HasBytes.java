package edu.illinois.ncsa.versus.adapter;

import java.io.IOException;

/**
 * Anything that has bytes.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 *
 */
public interface HasBytes extends Adapter {

	byte[] getBytes() throws IOException;
}
