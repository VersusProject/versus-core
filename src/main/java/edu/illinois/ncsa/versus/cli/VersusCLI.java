/*******************************************************************************
 * Copyright (c) 2010 University of Illinois. All rights reserved.
 * 
 * Developed by: 
 * National Center for Supercomputing Applications (NCSA)
 * University of Illinois
 * http://www.ncsa.illinois.edu/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimers.
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimers in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the names of National Center for Supercomputing Applications,
 *   University of Illinois, nor the names of its contributors may 
 *   be used to endorse or promote products derived from this Software 
 *   without specific prior written permission.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
 ******************************************************************************/
/**
 * 
 */
package edu.illinois.ncsa.versus.cli;

import java.io.File;
import java.util.UUID;

import edu.illinois.ncsa.versus.engine.impl.ComparisonStatusHandler;
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
 * @author Luigi Marini
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
			engine.submit(comparison, new ComparisonStatusHandler() {
				@Override
				public void onDone(double value) {
					System.out.println("Comparison's result: " + value);
					System.exit(0);
				}

				@Override
				public void onStarted() {
					System.out.println("Comparison started...");
				}

				@Override
				public void onFailed(String msg, Throwable e) {
					System.out.println("Comparison failed! " + msg + "\n" + e);
					System.exit(0);
				}

				@Override
				public void onAborted(String msg) {
					System.out.println("Comparison aborted! " + msg);
					System.exit(0);
				}
			});
		} else {
			System.out.println("Please provide the following arguments:");
			System.out
					.println("java VersusCLI dataset1 dataset2 adapter extractor measure");
			System.out.println("For example:");
			System.out
					.println("java VersusCLI file1.txt file2.txt edu.illinois.ncsa.versus.adapter.impl.BytesAdapter "
							+ "edu.illinois.ncsa.versus.extract.impl.MD5Extractor edu.illinois.ncsa.versus.measure.impl.MD5DistanceMeasure");
			System.exit(1);
		}
	}
}
