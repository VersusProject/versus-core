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
 * ****************************************************************************
 */
package edu.illinois.ncsa.versus.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.engine.impl.Job.ComparisonStatus;

/**
 * Multithreaded comparison engine.
 *
 * Might want to move to using {@link java.util.concurrent.Callable} and
 * {@link java.util.concurrent.Future}. See {@link ComprehensiveEngine}
 *
 * @author Luigi Marini
 *
 */
public class ExecutionEngine {

    private static final int EXECUTION_THREADS = 1;

    private final ThreadPoolExecutor newFixedThreadPool;

    private final List<Job> jobs;

    private static Log log = LogFactory.getLog(ExecutionEngine.class);

    public ExecutionEngine() {
        newFixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(EXECUTION_THREADS);
        jobs = new ArrayList<Job>();
    }

    /**
     * Execute each comparison in its own thread.
     *
     * @param job
     */
    public void submit(Job job) {
        submit(job, null);
    }

    /**
     * Execute each comparison in its own thread.
     *
     * FIXME each PairwiseComparison in a job needs an individual handler or
     * there needs to be a JobStatusHandler
     *
     * @param job
     */
    public void submit(Job job, ComparisonStatusHandler handler) {
        jobs.add(job);
        Set<PairwiseComparison> comparison = job.getComparisons();
        for (PairwiseComparison pairwiseComparison : comparison) {
            ComputeThread computeThread;
            try {
                computeThread = new ComputeThread(pairwiseComparison, job, handler);
            } catch (UnsupportedTypeException ex) {
                Logger.getLogger(ExecutionEngine.class.getName()).log(Level.SEVERE, null, ex);
                job.setStatus(pairwiseComparison.getId(), ComparisonStatus.FAILED);
                if (handler != null) {
                    handler.onFailed("Cannot start comparison.", ex);
                }
                continue;
            }
            newFixedThreadPool.execute(computeThread);
            job.setStatus(pairwiseComparison.getId(), ComparisonStatus.STARTED);
        }
        log.debug("Job submitted");
    }

    /**
     * Retrieve job by jobId from internal cache.
     *
     * @param jobId
     * @return
     */
    public Job getJob(String jobId) {
        for (Job job : jobs) {
            if (job.getId().equals(jobId)) {
                return job;
            }
        }
        log.error("Job not found. Id = " + jobId);
        return null;
    }

    /**
     * Submit a single pairwise comparison.
     *
     * @param comparison
     * @return
     */
    public Job submit(PairwiseComparison comparison) {
        return submit(comparison, null);
    }

    /**
     * Submit a single pairwise comparison and a comparison status handler.
     *
     * @param comparison
     * @param handler
     * @return
     */
    public Job submit(PairwiseComparison comparison,
            ComparisonStatusHandler handler) {
        Job job = new Job();
        job.setId(UUID.randomUUID().toString());
        if (comparison.getId() == null) {
            comparison.setId(UUID.randomUUID().toString());
        }
        job.addComparison(comparison);
        submit(job, handler);
        return job;
    }

    /**
     * Get the approximate number of jobs in the queue
     * @return 
     */
    public long getWaitingJobsNumber() {
        return newFixedThreadPool.getTaskCount()
                - newFixedThreadPool.getCompletedTaskCount();
    }
}
