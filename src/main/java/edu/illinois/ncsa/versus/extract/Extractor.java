/*******************************************************************************
 * Copyright (c) 2010 University of Illinois. All rights reserved.
 * 
 * Developed by: 
 * National Center for Supercomputing Applications (NCSA)
 * University of Illinois
 * http://www.ncsa.illinois.edu/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimers.
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimers in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the names of National Center for Supercomputing Applications,
 *   University of Illinois, nor the names of its contributors may 
 *   be used to endorse or promote products derived from this Software 
 *   without specific prior written permission.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
 ******************************************************************************/
/**
 * 
 */
package edu.illinois.ncsa.versus.extract;

import java.util.Set;

import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;

/**
 * An extractor extracts a particular feature from an adapter.
 * 
 * @author Luigi Marini
 * 
 */
public interface Extractor {

	/**
	 * Extract a feature from adapter.
	 * 
	 * @param adapter
	 * @return feature
	 */
	public Descriptor extract(Adapter adapter) throws Exception;

	/**
	 * Create an instance of the adapter required for this extractor.
	 * 
	 * @return an adapter instance used by this extractor
	 * 
	 * @deprecated Now extractors can support multiple adapter types
	 */
	@Deprecated
	public Adapter newAdapter();

	/**
	 * The pretty name for the extractor to use in applications.
	 * 
	 * @return the pretty name of the extractor
	 */
	String getName();

	/**
	 * The set of adapters supported by the extractor.
	 * 
	 * @return adapters supported by the extractor
	 */
	Set<Class<? extends Adapter>> supportedAdapters();

	/**
	 * The class of the feature that will be created by this extractor.
	 * 
	 * @return the class type of the output feature
	 */
	Class<? extends Descriptor> getFeatureType();

	/**
	 * Previews are used by clients who want to show a preview of the object to
	 * the user.
	 * 
	 * @return if a preview can be calculated or not
	 */
	public boolean hasPreview();

	/**
	 * 
	 * NULL if no preview for the extractor, if there is a visualizer class for
	 * the specified preview, then return the name of visualuzer class
	 * 
	 * e.g. the MD5Extractor visualizer class is named MD5Visualizer, which is
	 * what this function returns.
	 * 
	 * @return the name of the visualizer class (with the preview) for the
	 *         specified extractor
	 */
	public String previewName();

}
