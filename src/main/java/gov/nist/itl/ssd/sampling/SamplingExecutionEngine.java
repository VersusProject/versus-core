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
package gov.nist.itl.ssd.sampling;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import gov.nist.itl.ssd.sampling.SamplingRequest.SamplingStatus;

/**
 *
 * @author antoinev
 */
public class SamplingExecutionEngine {

    private static final int EXECUTION_THREADS = 1;

    private final ExecutorService executorService;

    private final List<SamplingRequest> requests;

    private static final Logger log = Logger.getLogger(SamplingExecutionEngine.class.getName());

    public SamplingExecutionEngine() {
        executorService = Executors.newFixedThreadPool(EXECUTION_THREADS);
        requests = new ArrayList<SamplingRequest>();
    }

    public void submit(final SamplingRequest sampling,
            final SamplingStatusHandler handler) {
        requests.add(sampling);
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    if (handler != null) {
                        handler.onStarted();
                    }
                    List<Individual> sample = sample(sampling);
                    sampling.setResult(sample);

                    if (handler != null) {
                        handler.onDone();
                    }
                } catch (Throwable e) {
                    if(handler != null) {
                        handler.onFailed(e.getMessage(), e);
                    }
                    log.log(Level.SEVERE, "Error computing sampling " + sampling.getId(), e);
                }
            }

            private List<Individual> sample(SamplingRequest sampling) throws UnsupportedTypeException, SamplingException {
                String individualId = sampling.getIndividualId();
                String samplerId = sampling.getSamplerId();
                List<String> datasetsNames = sampling.getDatasetsNames();
                List<InputStream> datasetsStreams = sampling.getDatasetsStreams();

                List<Individual> individuals = new ArrayList<Individual>(datasetsNames.size());
                Sampler sampler;
                try {
                    sampler = (Sampler) Class.forName(samplerId).newInstance();
                    Iterator<String> dni = datasetsNames.iterator();
                    Iterator<InputStream> dsi = datasetsStreams.iterator();
                    while (dni.hasNext() && dsi.hasNext()) {
                        String name = dni.next();
                        InputStream stream = dsi.next();
                        Individual individual =
                                (Individual) Class.forName(individualId).newInstance();
                        individual.setId(name);
                        individuals.add(individual);
                    }
                } catch (ClassNotFoundException e) {
                    log.log(Level.SEVERE, "Error setting up compute thread", e);
                    throw new UnsupportedTypeException("Instanciation error.", e);
                } catch (InstantiationException e) {
                    log.log(Level.SEVERE, "Error setting up compute thread", e);
                    throw new UnsupportedTypeException("Instanciation error.", e);
                } catch (IllegalAccessException e) {
                    log.log(Level.SEVERE, "Error setting up compute thread", e);
                    throw new UnsupportedTypeException("Instanciation error.", e);
                }
                return sampler.sample(individuals, sampling.getSampleSize());
            }
        };

        executorService.execute(run);
    }
}
