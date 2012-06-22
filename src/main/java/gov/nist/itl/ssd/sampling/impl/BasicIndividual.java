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

/**
 *
 * @author antoinev
 */
public class BasicIndividual implements Individual {

    private final String id;

    // To be called only by reflection by the SamplingRegistry
    public BasicIndividual() {
        this(null);
    }

    public BasicIndividual(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Basic individual";
    }

    @Override
    public List<String> getSupportedMediaTypes() {
        ArrayList<String> supported = new ArrayList<String>(1);
        supported.add("*/*");
        return supported;
    }
}
