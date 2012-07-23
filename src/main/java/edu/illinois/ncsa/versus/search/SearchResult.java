/**
 * 
 */
package edu.illinois.ncsa.versus.search;

import edu.illinois.ncsa.versus.measure.Proximity;

/**
 * Result of a query against an index.
 * 
 * @author Luigi Marini
 * 
 */
public class SearchResult {

	private Proximity proximity;
	private String docId;

	public Proximity getProximity() {
		return proximity;
	}

	public void setProximity(Proximity proximity) {
		this.proximity = proximity;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

}
