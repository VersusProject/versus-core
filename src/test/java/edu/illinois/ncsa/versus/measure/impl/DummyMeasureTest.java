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
package edu.illinois.ncsa.versus.measure.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.*;
import static org.junit.Assert.*;

import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature;
import edu.illinois.ncsa.versus.descriptor.impl.DummyFeature2;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;

/**
 *
 * @author antoinev
 */
public class DummyMeasureTest {

    public DummyMeasureTest() {
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
     * Test of compare method, of class DummyMeasure.
     */
    @Test
    public void testCompare() throws Exception {
        System.out.println("compare");
        DummyMeasure instance = new DummyMeasure();
        Similarity expResult = new SimilarityNumber(0);
        Similarity result = instance.compare(null, null);
        assertEquals(expResult.getValue(), result.getValue(), 0);
    }
    
    /**
     * Test of normalize method, of class DummyMeasure.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        DummyMeasure instance = new DummyMeasure();
        SimilarityPercentage expResult = null;
        SimilarityPercentage result = instance.normalize(null);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of supportedFeaturesTypes method, of class DummyMeasure.
     */
    @Test
    public void testSupportedFeaturesTypes() {
        System.out.println("supportedFeaturesTypes");
        DummyMeasure instance = new DummyMeasure();
        Class[] expResult = {DummyFeature.class, DummyFeature2.class};
        Set result = instance.supportedFeaturesTypes();
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(expResult)));
    }

    /**
     * Test of getName method, of class DummyMeasure.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        DummyMeasure instance = new DummyMeasure();
        String expResult = "Dummy Measure";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class DummyMeasure.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        DummyMeasure instance = new DummyMeasure();
        Class expResult = DummyMeasure.class;
        Class result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCategory method, of class DummyMeasure.
     */
    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        DummyMeasure instance = new DummyMeasure();
        String expResult = "Dummy";
        String result = instance.getCategory();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHelpUrl method, of class DummyMeasure.
     */
    @Test
    public void testGetHelpZipped() throws IOException {
        System.out.println("getHelpZipped");
        File dir = new File("target");
        
        DummyMeasure instance = new DummyMeasure();
        InputStream result = instance.getHelpZipped();
        assertNotNull(result);

        ZipInputStream zis = new ZipInputStream(result);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            assertNotNull(entry);
            int BUFFER = 2048;
            FileOutputStream fos = new FileOutputStream(new File(dir, entry.getName()));
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
        }
        zis.close();
        
        File helpHtml = new File(dir, "index.html");
        File helpImage = new File(dir, "dialog-information-3.png");
        assertTrue(helpHtml.exists());
        assertTrue(helpImage.exists());
    }
    
    @Test
    public void testGetHelpSHA1() {
        System.out.println("getHelpSHA1");
        DummyMeasure instance = new DummyMeasure();
        String result = instance.getHelpSHA1();
        assertEquals("1d4b322b2996993621401896cb61f0cb2c2ca656", result);
    }
}
