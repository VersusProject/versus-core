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
import java.util.List;

/**
 *
 * @author antoinev
 */
public class SamplingRequest {

    public enum SamplingStatus {
        STARTED, DONE, FAILED, ABORTED
    }
    
    private final String id;

    private final List<String> datasetsNames;

    private final List<InputStream> datasetsStreams;

    private final String individualId;

    private final String samplerId;

    private final int sampleSize;

    private final List<Individual> result;
    
    private SamplingStatus status;

    public SamplingRequest(String id, String individualId, String samplerId,
            int sampleSize, List<String> datasetsNames,
            List<InputStream> datasetsStreams) {
        this.id = id;
        this.individualId = individualId;
        this.samplerId = samplerId;
        this.sampleSize = sampleSize;
        this.datasetsNames = datasetsNames;
        this.datasetsStreams = datasetsStreams;
        this.result = new ArrayList<Individual>(sampleSize);
    }
    
    public String getId() {
        return id;
    }

    public List<String> getDatasetsNames() {
        return datasetsNames;
    }

    public List<InputStream> getDatasetsStreams() {
        return datasetsStreams;
    }

    public String getIndividualId() {
        return individualId;
    }

    public String getSamplerId() {
        return samplerId;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public List<Individual> getResult() {
        return result;
    }

    public void setResult(List<Individual> result) {
        this.result.clear();
        this.result.addAll(result);
    }
    
    public SamplingStatus getStatus() {
        return status;
    }
    
    public void setStatus(SamplingStatus status) {
        this.status = status;
    }
}
