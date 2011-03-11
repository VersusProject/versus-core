package edu.illinois.ncsa.versus.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.engine.impl.Job.ComparisonStatus;

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
		jobs.add(job);
		Set<PairwiseComparison> comparison = job.getComparison();
		for (PairwiseComparison pairwiseComparison : comparison) {
			ComputeThread computeThread = new ComputeThread(pairwiseComparison,
					job);
			newFixedThreadPool.execute(computeThread);
			job.setStatus(pairwiseComparison.getId(), ComparisonStatus.STARTED);
		}
		log.debug("Job submitted");
	}

	/**
	 * Execute each comparison in its own thread.
	 * 
	 * @param job
	 */
	public void submit(Job job, ComparisonDoneHandler handler) {
		jobs.add(job);
		Set<PairwiseComparison> comparison = job.getComparison();
		for (PairwiseComparison pairwiseComparison : comparison) {
			ComputeThread computeThread = new ComputeThread(pairwiseComparison,
					job, handler);
			newFixedThreadPool.execute(computeThread);
			job.setStatus(pairwiseComparison.getId(), ComparisonStatus.STARTED);
		}
		log.debug("Job submitted");
	}

	public Job getJob(String jobId) {
		for (Job job : jobs) {
			if (job.getId().equals(jobId)) {
				return job;
			}
		}
		log.error("Job not found. Id = " + jobId);
		return null;
	}

	public Job submit(PairwiseComparison comparison) {
		Job job = new Job();
		job.setId(UUID.randomUUID().toString());
		job.addComparison(comparison);
		submit(job);
		return job;
	}

	public Job submit(PairwiseComparison comparison,
			ComparisonDoneHandler handler) {
		Job job = new Job();
		job.setId(UUID.randomUUID().toString());
		job.addComparison(comparison);
		submit(job, handler);
		return job;
	}
}
