/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.io.File;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;

/**
 * @author Luigi Marini
 * 
 */
public class ComputeSimilarityThread extends Thread {

	private final Runnable			doneHandler;

	/** Commons logging **/
	private static Log				log	= LogFactory.getLog(ComputeSimilarityThread.class);

	private final File				file1;

	private final File				file2;

	private final String			colorSpace;

	private final ResultTableModel	resultTableModel;

	private final Adapter		adapter1;

	private final Extractor			extractor;

	private final Measure			measure;

	private final Adapter		adapter2;

	private final Runnable			errorHandler;

	/**
	 * 
	 * @param resultTableModel
	 * @param file1
	 * @param file2
	 * @param colorSpace
	 * @param doneHandler
	 * @param adapter1
	 * @param extractor
	 */
	public ComputeSimilarityThread(ResultTableModel resultTableModel, File file1, File file2, String colorSpace, 
			Adapter adapter1, Adapter adapter2, Extractor extractor,
			Measure measure, Runnable doneHandler, Runnable errorHandler) {
		this.resultTableModel = resultTableModel;
		this.file1 = file1;
		this.file2 = file2;
		this.adapter2 = adapter2;
		this.doneHandler = doneHandler;
		this.colorSpace = colorSpace;
		this.adapter1 = adapter1;
		this.extractor = extractor;
		this.measure = measure;
		this.errorHandler = errorHandler;
	}

	/**
	 * 
	 */
	@Override
	public void run() {

//		for (Adapter adapter : adapters1) {
//			log.trace("Applying adapter " + adapter.getClass().getName());
//		}
//
//		log.trace("Applying extractor " + extractor.getName());
//
//		log.trace("Calculating metric " + measure.getName());

		Similarity compare;
		try {
			compare = compare(file1, file2);
			log.debug("Compared " + file1.getName() + " with " + file2.getName() + " = " + compare.getValue());
			resultTableModel.setValueAt(compare.getValue(), file1, file2);
		} catch (Exception e1) {
			log.error("Error computing similarity between " + file1 + " and " + file2, e1);
			resultTableModel.setValueAt("INVALID", file1, file2);
			SwingUtilities.invokeLater(errorHandler);
		}

		SwingUtilities.invokeLater(doneHandler);
	}

	/**
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 * @throws Exception
	 */
	private Similarity compare(File file1, File file2) throws Exception {

		if ((adapter1 instanceof FileLoader) && (adapter2 instanceof FileLoader)) {
			FileLoader fileLoaderAdapter = (FileLoader) adapter1;
			fileLoaderAdapter.load(file1);
			Descriptor feature1 = extractor.extract(fileLoaderAdapter);
			FileLoader fileLoaderAdapter2 = (FileLoader) adapter2;
			fileLoaderAdapter2.load(file2);
			Descriptor feature2 = extractor.extract(fileLoaderAdapter2);
			Similarity value = measure.compare(feature1, feature2);
			return value;
		} else {
			throw new UnsupportedTypeException();
		}
	}
}
