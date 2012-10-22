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
package edu.illinois.ncsa.versus.engine;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.VersusException;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.registry.CompareRegistry;

/**
 * Simple engine to compare a list of files with eachother.
 *
 * @author Luigi Marini
 * @deprecated
 */
@Deprecated
public class SimpleEngine {

    private File[] files;

    /**
     * Commons logging *
     */
    private static Log log = LogFactory.getLog(SimpleEngine.class);

    public SimpleEngine() {
    }

    public void add(File... files) {
        this.files = files;
    }

    public <S> Collection<S> compareAll(File[] files,
            Measure similarityMeasure) {

        Collection<S> similarityValues = new HashSet<S>();

        Extractor chooseExtractor = chooseExtractor(similarityMeasure);

        if (chooseExtractor != null) {
            Collection<Descriptor> features = extractFiles(files, chooseExtractor);

            for (Descriptor feature1 : features) {
                for (Descriptor feature2 : features) {
                    if (feature1 != feature2) {
                        // similarityMeasure.compare(feature1, feature2);
                    }
                }
            }

        }
        return similarityValues;
    }

    /**
     * Extract features from files.
     *
     * @param files
     * @param extractor
     */
    private Collection<Descriptor> extractFiles(File[] files, Extractor extractor) {

        Collection<Descriptor> features = new HashSet<Descriptor>();

        for (File file : files) {
            FileLoader newAdapter = (FileLoader) extractor.newAdapter();
            try {
                newAdapter.load(file);
            } catch (IOException e) {
                log.error("Error loading image" + e);
            } catch (VersusException ex) {
                Logger.getLogger(SimpleEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Descriptor feature = extractor.extract(newAdapter);
                features.add(feature);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return features;
    }

    /**
     * Return the first available extractor for a particular measure.
     *
     * @param similarityMeasure
     * @return
     */
    private Extractor chooseExtractor(Measure similarityMeasure) {

        CompareRegistry registry = new CompareRegistry();

        Collection<Extractor> availableExtractors = registry.getAvailableExtractors(similarityMeasure);

        Iterator<Extractor> iterator = availableExtractors.iterator();

        // for now only use the first extractor in the list
        if (iterator.hasNext()) {
            Extractor extractor = iterator.next();
            log.debug("Using extractor " + extractor.getName());
            return extractor;
        }

        return null;
    }
}
