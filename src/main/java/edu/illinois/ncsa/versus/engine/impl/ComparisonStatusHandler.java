package edu.illinois.ncsa.versus.engine.impl;

public interface ComparisonStatusHandler {

	void onDone(double value);

	void onStarted();

	void onFailed(String msg, Exception e);

	void onAborted(String msg);
}
