/**
 * 
 */
package edu.illinois.ncsa.versus.cli;

import java.io.File;
import java.util.UUID;

import edu.illinois.ncsa.versus.engine.impl.ComparisonDoneHandler;
import edu.illinois.ncsa.versus.engine.impl.ExecutionEngine;
import edu.illinois.ncsa.versus.engine.impl.PairwiseComparison;

/**
 * Simple command line interface to versus engine.
 * 
 * Example use:
 * 
 * "VersusCLI /path/to/file/1 /path/to/file/2 
 * edu.illinois.ncsa.versus.adapter.impl.BytesAdapter 
 * edu.illinois.ncsa.versus.extract.impl.MD5Extractor 
 * edu.illinois.ncsa.versus.measure.impl.MD5DistanceMeasure"
 * 
 * @author Luigi Marini <lmarini@ncsa.illinois.edu>
 *
 */
public class VersusCLI {

	public static void main(String[] args) {
		if (args.length == 5) {
			// create new comparison
			PairwiseComparison comparison = new PairwiseComparison();
			comparison.setId(UUID.randomUUID().toString());
			comparison.setFirstDataset(new File(args[0]));
			comparison.setSecondDataset(new File(args[1]));
			comparison.setAdapterId(args[2]);
			comparison.setExtractorId(args[3]);
			comparison.setMeasureId(args[4]);

			// create an engine
			ExecutionEngine engine = new ExecutionEngine();

			// submit to engine
			engine.submit(comparison, new ComparisonDoneHandler() {
			   @Override
			   public void onDone(double value) {
			      System.out.println("Comparison's result: " + value);
			      System.exit(0);
			   }
			});
		} else {
			System.out.println("Please provide the following arguments:");
			System.out.println("java VersusCLI dataset1 dataset2 adapter extractor measure");
			System.out.println("For example:");
			System.out.println("java VersusCLI file1.txt file2.txt edu.illinois.ncsa.versus.adapter.impl.BytesAdapter " +
					"edu.illinois.ncsa.versus.extract.impl.MD5Extractor edu.illinois.ncsa.versus.measure.impl.MD5DistanceMeasure");
			System.exit(1);
		}
	}
}
