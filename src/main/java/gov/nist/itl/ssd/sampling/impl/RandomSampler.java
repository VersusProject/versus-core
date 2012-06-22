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
package gov.nist.itl.ssd.sampling.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gov.nist.itl.ssd.sampling.Individual;
import gov.nist.itl.ssd.sampling.Sampler;
import gov.nist.itl.ssd.sampling.SamplingException;

/**
 *
 * @author antoinev
 */
public class RandomSampler implements Sampler {

    @Override
    public String getName() {
        return "Random sampler";
    }

    @Override
    public List<Class<? extends Individual>> getSupportedIndividuals() {
        ArrayList<Class<? extends Individual>> supported =
                new ArrayList<Class<? extends Individual>>(1);
        supported.add(Individual.class); // all individuals supported
        return supported;
    }

    @Override
    public List<Individual> sample(List<Individual> collection, int sampleSize)
            throws SamplingException {
        if (collection == null || collection.isEmpty()) {
            throw new SamplingException("Specified collection to sample is empty.");
        }

        if (sampleSize <= 0) {
            throw new SamplingException("Specified sample size is less or equal to 0.");
        }

        LinkedList<Individual> backup = new LinkedList<Individual>(collection);
        if (collection.size() <= sampleSize) {
            return backup;
        }

        ArrayList<Individual> result = new ArrayList<Individual>(sampleSize);
        for (int i = 0; i < sampleSize; i++) {
            int index = (int) (Math.random() * (collection.size() - i - 1) + 0.5);
            result.add(backup.remove(index));
        }

        return result;
    }
}
