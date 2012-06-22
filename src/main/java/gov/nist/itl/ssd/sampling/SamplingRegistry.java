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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

/**
 *
 * @author antoinev
 */
public class SamplingRegistry {

    private final Map<String, Individual> individuals;

    private final Map<String, Sampler> samplers;

    public SamplingRegistry() {
        individuals = new HashMap<String, Individual>();
        Iterator<Individual> indIter = ServiceLoader.load(Individual.class).iterator();
        while (indIter.hasNext()) {
            Individual individual = indIter.next();
            individuals.put(individual.getClass().getName(), individual);
        }

        samplers = new HashMap<String, Sampler>();
        Iterator<Sampler> samplerIter = ServiceLoader.load(Sampler.class).iterator();
        while (samplerIter.hasNext()) {
            Sampler sampler = samplerIter.next();
            samplers.put(sampler.getClass().getName(), sampler);
        }
    }

    public Individual getIndividual(String individualId) {
        return individuals.get(individualId);
    }

    public Set<String> getAvailableIndividualsIds() {
        return individuals.keySet();
    }

    public Collection<Individual> getAvailableIndividuals() {
        return individuals.values();
    }

    public Sampler getSampler(String samplerId) {
        return samplers.get(samplerId);
    }

    public Set<String> getAvailableSamplersIds() {
        return samplers.keySet();
    }

    public Collection<Sampler> getAvailableSamplers() {
        return samplers.values();
    }
    
    public Collection<Individual> getAvailableIndividuals(Sampler sampler) {
        Collection<Individual> ind = new HashSet<Individual>();
        List<Class<? extends Individual>> supportedIndiviudalsTypes = 
                sampler.getSupportedIndividuals();
        for(Individual individual : individuals.values()) {
            Class<? extends Individual> individualClass = individual.getClass();
            for(Class<? extends Individual> supportedIndividual : supportedIndiviudalsTypes) {
                if(supportedIndividual.isAssignableFrom(individualClass)) {
                    ind.add(individual);
                    break;
                }
            }
        }
        return ind;
    }
    
    public Collection<String> getAvailableIndividualsIds(Sampler sampler) {
        Collection<Individual> availableIndividuals = getAvailableIndividuals(sampler);
        HashSet<String> result = new HashSet<String>(availableIndividuals.size());
        for(Individual ind : availableIndividuals) {
            result.add(ind.getClass().getName());
        }
        return result;
    }
}
