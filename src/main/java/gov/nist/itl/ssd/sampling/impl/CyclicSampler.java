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
import java.util.List;

import gov.nist.itl.ssd.sampling.Individual;
import gov.nist.itl.ssd.sampling.Sampler;
import gov.nist.itl.ssd.sampling.SamplingException;

/**
 * Sampler which will uniformally select the individuals through the input
 * collection.
 * 
 * @author antoinev
 */
public class CyclicSampler implements Sampler {

    @Override
    public String getName() {
        return "Cyclic sampling";
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

        int size = collection.size();
        if (size <= sampleSize) {
            return collection;
        }

        ArrayList<Individual> result = new ArrayList<Individual>(sampleSize);
        double ratio = (size + 1) / (double) (sampleSize + 1);
        int i = 1;
        int next = (int)Math.round(ratio);
        for(Individual ind : collection) {
            if (i == next) {
                result.add(ind);
                next = (int)(ratio * (result.size() + 1));
            }
            i++;
        }
        
        return result;
    }
}
