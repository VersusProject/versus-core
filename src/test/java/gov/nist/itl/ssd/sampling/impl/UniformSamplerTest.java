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

import org.apache.commons.collections.CollectionUtils;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.nist.itl.ssd.sampling.Individual;
import gov.nist.itl.ssd.sampling.SamplingException;

/**
 *
 * @author antoinev
 */
public class UniformSamplerTest {

    public UniformSamplerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class RandomSampler.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        UniformSampler instance = new UniformSampler();
        String expResult = "Uniform sampler";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSupportedIndividuals method, of class RandomSampler.
     */
    @Test
    public void testGetSupportedIndividuals() {
        System.out.println("getSupportedIndividuals");
        UniformSampler instance = new UniformSampler();
        List result = instance.getSupportedIndividuals();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Individual.class, result.get(0));
    }

    /**
     * Test of sample method, of class RandomSampler.
     */
    @Test
    public void testSample() throws Exception {
        System.out.println("sample");
        testSizeCombination(10, 1);
        testSizeCombination(10, 2);
        testSizeCombination(10, 3);
        testSizeCombination(10, 4);
        testSizeCombination(10, 5);
        testSizeCombination(10, 6);
        testSizeCombination(10, 7);
        testSizeCombination(10, 8);
        testSizeCombination(10, 9);
        testSizeCombination(10, 10);
        testSizeCombination(5, 3);
        testSizeCombination(1000, 30);
        testSizeCombination(1000, 1000);
        testSizeCombination(3, 2);
    }

    private void testSizeCombination(int collectionSize, int sampleSize)
            throws SamplingException {
        List<Individual> collection = getCollection(collectionSize);
        UniformSampler instance = new UniformSampler();
        List<Individual> result = instance.sample(collection, sampleSize);
        assertNotNull(result);
        assertEquals("Fail with " + collectionSize + "," + sampleSize,
                sampleSize, result.size());
        assertTrue(collection.containsAll(result));
        try {
            instance.sample(null, sampleSize);
            fail();
        } catch (SamplingException e) {
        }
        try {
            instance.sample(new ArrayList<Individual>(), sampleSize);
            fail();
        } catch (Exception e) {
        }
        try {
            instance.sample(collection, 0);
            fail();
        } catch (Exception e) {
        }

        List<Individual> sample = instance.sample(collection, collectionSize);
        boolean equalCollection = CollectionUtils.isEqualCollection(collection, sample);
        assertTrue(equalCollection);
    }

    private List<Individual> getCollection(int size) {
        ArrayList<Individual> collection = new ArrayList<Individual>(size);
        for (int i = 0; i < size; i++) {
            collection.add(new BasicIndividual((new Integer(i)).toString()));
        }
        return collection;
    }
}
