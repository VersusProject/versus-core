/*
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of
 * their official duties. Pursuant to title 17 Section 105 of the United
 * States Code this software is not subject to copyright protection and is
 * in the public domain. This software is an experimental system. NIST assumes
 * no responsibility whatsoever for its use by other parties, and makes no
 * guarantees, expressed or implied, about its quality, reliability, or
 * any other characteristic. We would appreciate acknowledgement if the
 * software is used.
 */
package edu.illinois.ncsa.versus.adapter;

import java.io.IOException;
import java.io.InputStream;

import edu.illinois.ncsa.versus.VersusException;

/**
 *
 * @author antoinev
 */
public interface StreamLoader extends Adapter {
    
    /**
     * Load a stream in the data structure specific to the adapter
     * @param stream 
     */
    public void load(InputStream stream) throws IOException, VersusException;
    
}
