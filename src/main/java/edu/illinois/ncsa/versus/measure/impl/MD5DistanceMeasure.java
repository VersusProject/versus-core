package edu.illinois.ncsa.versus.measure.impl;

import java.util.Arrays;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.descriptor.impl.MD5Digest;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;
import java.util.HashSet;
import java.util.Set;

/**
 * MD5 Digest distance. Distance is 1 if the digests are equal, 0 otherwise.
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 * 
 */
public class MD5DistanceMeasure implements Measure {

	@Override
	public Similarity compare(Descriptor feature1, Descriptor feature2)
			throws Exception {
		if (feature1 instanceof MD5Digest && feature2 instanceof MD5Digest) {
			MD5Digest md5Feature1 = (MD5Digest) feature1;
			MD5Digest md5Feature2 = (MD5Digest) feature2;
			if (Arrays.equals(md5Feature1.getDigest(), md5Feature2.getDigest())) {
				return new SimilarityNumber(1.0);
			} else {
				return new SimilarityNumber(0);
			}
		} else {
			throw new UnsupportedTypeException(
					"Similarity measure expects feature of type MD5Digest");
		}
	}

	@Override
	public SimilarityPercentage normalize(Similarity similarity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Class<? extends Descriptor>> supportedFeaturesTypes() {
        Set<Class<? extends Descriptor>> featuresTypes = new HashSet<Class<? extends Descriptor>>(1);
        featuresTypes.add(MD5Digest.class);
        return featuresTypes;
	}

	@Override
	public String getName() {
		return "MD5 Hash Distance";
	}

	@Override
	public Class<MD5DistanceMeasure> getType() {
		return MD5DistanceMeasure.class;
	}

}
