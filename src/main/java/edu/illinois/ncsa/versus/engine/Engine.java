/**
 * 
 */
package edu.illinois.ncsa.versus.engine;

import java.io.File;
import java.util.Collection;

import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.measure.Measure;

/**
 * @author Luigi Marini
 * @deprecated
 */
@Deprecated
public interface Engine {

	void add(File... files);

	Collection<Descriptor> compareAll(File[] files,
			Measure similarityMeasure);
}
