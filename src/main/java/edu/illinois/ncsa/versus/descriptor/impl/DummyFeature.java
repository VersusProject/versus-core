/**
 * 
 */
package edu.illinois.ncsa.versus.descriptor.impl;

import edu.illinois.ncsa.versus.descriptor.Feature;

/**
 * @author lmarini
 * 
 */
public class DummyFeature implements Feature {

	private final String type = this.getClass().toString();

	@Override
	public String getName() {
		return "Dummy Feature";
	}

	@Override
	public String getType() {
		return type;
	}

}
