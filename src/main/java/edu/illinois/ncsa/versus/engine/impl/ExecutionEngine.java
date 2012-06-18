package edu.illinois.ncsa.versus.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.engine.impl.Job.ComparisonStatus;

/**
 * Multithreaded comparison engine.
 *
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 *
 */
public class ExecutionEngine {

    private static final int EXECUTION_THREADS = 1;

    private final ExecutorService newFixedThreadPool;

    private final List<Job> jobs;

    private static Log log = LogFactory.getLog(ExecutionEngine.class);

    public ExecutionEngine() {
        newFixedThreadPool = Executors.newFixedThreadPool(EXECUTION_THREADS);
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
}
