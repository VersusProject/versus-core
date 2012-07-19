/**
 * *****************************************************************************
 * Copyright (c) 2010 University of Illinois. All rights reserved.
 *
 * Developed by: National Center for Supercomputing Applications (NCSA)
 * University of Illinois http://www.ncsa.illinois.edu/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * with the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimers. - Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimers in the documentation and/or other materials
 * provided with the distribution. - Neither the names of National Center for
 * Supercomputing Applications, University of Illinois, nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * Software without specific prior written permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS WITH
 * THE SOFTWARE.
 *****************************************************************************
 */
/**
 *
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.StreamLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.engine.impl.Job.ComparisonStatus;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;

/**
 * A thread dedicated to running a specific comparison between two files.
 *
 * @author Luigi Marini
 *
 */
public class ComputeThread extends Thread {

    private static Log log = LogFactory.getLog(ComputeThread.class);

    private final InputStream file1;

    private final InputStream file2;

    private final Adapter adapter1;

    private final Extractor extractor;

    private final Measure measure;

    private final Adapter adapter2;

    private final Job job;

    private final PairwiseComparison pairwiseComparison;

    private final String adapterID;

    private final String extractionID;

    private final String measureID;

    private final ComparisonStatusHandler handler;

    public ComputeThread(PairwiseComparison pairwiseComparison, Job job)
            throws UnsupportedTypeException {
        this(pairwiseComparison, job, null);
    }

    public ComputeThread(PairwiseComparison pairwiseComparison, Job job,
            ComparisonStatusHandler handler) throws UnsupportedTypeException {
        this.pairwiseComparison = pairwiseComparison;
        this.job = job;
        adapterID = pairwiseComparison.getAdapterId();
        extractionID = pairwiseComparison.getExtractorId();
        measureID = pairwiseComparison.getMeasureId();
        try {
            log.debug("Selected adapter is " + adapterID);
            adapter1 = (Adapter) Class.forName(adapterID).newInstance();
            adapter2 = (Adapter) Class.forName(adapterID).newInstance();
            log.debug("Selected extractor is " + extractionID);
            extractor = (Extractor) Class.forName(extractionID).newInstance();
            log.debug("Selected measure is " + measureID);
            measure = (Measure) Class.forName(measureID).newInstance();
        } catch (ClassNotFoundException e) {
            log.error("Error setting up compute thread", e);
            throw new UnsupportedTypeException("Instanciation error.", e);
        } catch (InstantiationException e) {
            log.error("Error setting up compute thread", e);
            throw new UnsupportedTypeException("Instanciation error.", e);
        } catch (IllegalAccessException e) {
            log.error("Error setting up compute thread", e);
            throw new UnsupportedTypeException("Instanciation error.", e);
        }
        file1 = pairwiseComparison.getFirstDataset();
        file2 = pairwiseComparison.getSecondDataset();
        this.handler = handler;
    }

    /**
     * Compare two files and update the result.
     */
    @Override
    public void run() {
        try {
            if (handler != null) {
                handler.onStarted();
            }
            Similarity compare = compare(file1, file2);
            job.updateSimilarityValue(pairwiseComparison.getId(),
                    compare.getValue());
            if (handler != null) {
                handler.onDone(compare.getValue());
            }
            log.debug("Done computing similarity between " + file1 + " and "
                    + file2 + " = " + compare.getValue());
        } catch (Throwable e1) {
            job.updateSimilarityValue(pairwiseComparison.getId(), Double.NaN);
            if (handler != null) {
                handler.onFailed(e1.getMessage(), e1);
            }
            job.setStatus(pairwiseComparison.getId(), ComparisonStatus.FAILED);
            log.error("Error computing similarity between " + file1 + " and "
                    + file2 + " [" + pairwiseComparison.getId() + "]", e1);
        }
    }

    private Similarity compare(InputStream stream1, InputStream stream2) throws Exception {

        if ((adapter1 instanceof StreamLoader)
                && (adapter2 instanceof StreamLoader)) {
            StreamLoader streamLoaderAdapter = (StreamLoader) adapter1;
            streamLoaderAdapter.load(stream1);
            Descriptor feature1 = extractor.extract(streamLoaderAdapter);
            StreamLoader streamLoaderAdapter2 = (StreamLoader) adapter2;
            streamLoaderAdapter2.load(stream2);
            Descriptor feature2 = extractor.extract(streamLoaderAdapter2);
            Similarity value = measure.compare(feature1, feature2);
            return value;
        } else {
            if (handler != null) {
                handler.onAborted("Unsupported types");
            }
            throw new UnsupportedTypeException("Unsupported adapters.");
        }
    }
}
