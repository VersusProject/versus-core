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
package edu.illinois.ncsa.versus.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.illinois.ncsa.versus.measure.impl.DummyMeasure;

/**
 *
 * @author antoinev
 */
public class HelpProvider {

    private HelpProvider() {
    }

    public static InputStream getHelpZipped(Class helpClass) {
        URL helpPath = getHelpPath(helpClass);
        if (helpPath == null) {
            return null;
        }
        try {
            return new FileInputStream(new File(helpPath.toURI()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(DummyMeasure.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DummyMeasure.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getHelpSHA1(Class helpClass) {
        URL helpPath = getHelpPath(helpClass);
        if (helpPath == null) {
            return null;
        }
        try {
            return Hasher.getHash(new File(helpPath.toURI()), "SHA1");
        } catch (IOException ex) {
            Logger.getLogger(HelpProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DummyMeasure.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HelpProvider.class.getName()).log(Level.SEVERE, "SHA1 algorithm not found.", ex);
        }
        return null;
    }

    private static URL getHelpPath(Class helpClass) {
        return helpClass.getResource("help/" + helpClass.getSimpleName() + ".zip");
    }
}
