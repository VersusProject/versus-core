package edu.illinois.ncsa.versus.descriptor.impl;

import edu.illinois.ncsa.versus.descriptor.Feature;

/**
 * MD5 Digest feature descriptor.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 *
 */
public class MD5Digest implements Feature {

	private final byte[] digest;

	public MD5Digest(byte[] digest) {
		this.digest = digest;
	}

	@Override
	public String getName() {
		return "MD5 Digest";
	}

	@Override
	public String getType() {
		return this.getClass().toString();
	}

	public byte[] getDigest() {
		return digest;
	}

}
