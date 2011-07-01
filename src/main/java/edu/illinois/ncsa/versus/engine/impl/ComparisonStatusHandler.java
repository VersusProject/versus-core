package edu.illinois.ncsa.versus.engine.impl;

/**
 * Handler signaling when a comparison is done, started, failed, aborted.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public interface ComparisonStatusHandler {

	void onDone(double value);

	void onStarted();

	void onFailed(String msg, Exception e);

	void onAborted(String msg);
}
