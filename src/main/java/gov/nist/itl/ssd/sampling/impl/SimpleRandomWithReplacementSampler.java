package gov.nist.itl.ssd.sampling.impl;

import gov.nist.itl.ssd.sampling.Individual;
import gov.nist.itl.ssd.sampling.Sampler;
import gov.nist.itl.ssd.sampling.SamplingException;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Nimorwicz
 *
 */
public class SimpleRandomWithReplacementSampler implements Sampler {

    @Override
    public String getName() {
        return "Simple random sampling with replacement";
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
        	
        ArrayList<Individual> result = new ArrayList<Individual>();
        for (int i = 0; i < sampleSize; i++) {
            int index = (int) (Math.random() * (collection.size() - 1) + 0.5);
            //System.out.println(collection.get(index).toString());
            result.add(collection.get(index));
        }
        
        return result;
    }
}