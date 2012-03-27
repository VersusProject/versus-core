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

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antoinev
 */
public class HelpProvider {

    private HelpProvider() {
    }

    public static InputStream getHelpZipped(Class helpClass) {
        return helpClass.getResourceAsStream("help/" + helpClass.getSimpleName() + ".zip");
    }

    public static String getHelpSHA1(Class helpClass) {
        InputStream helpStream = getHelpZipped(helpClass);
        if (helpStream == null) {
            return null;
        }
        try {
            return Hasher.getHash(helpStream, "SHA1");
        } catch (IOException ex) {
            Logger.getLogger(HelpProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HelpProvider.class.getName()).log(Level.SEVERE, "SHA1 algorithm not found.", ex);
        }
        return null;
    }
}
