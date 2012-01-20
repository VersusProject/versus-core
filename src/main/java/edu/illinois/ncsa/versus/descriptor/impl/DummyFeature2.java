/**
 * 
 */
package edu.illinois.ncsa.versus.descriptor.impl;

import edu.illinois.ncsa.versus.descriptor.Feature;

/**
 * @author lmarini
 * 
 */
public class DummyFeature2 implements Feature {

	private final String type = this.getClass().toString();

	@Override
	public String getName() {
		return "Dummy Feature2";
	}

	@Override
	public String getType() {
		return type;
	}

}
