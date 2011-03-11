/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.registry.CompareRegistry;

/**
 * Top allows user to pick what to run. Bottom shows a table of results.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class ExecutionPanel extends JPanel implements ActionListener {

	private static final int EXECUTION_THREADS = 1;
	private final JPanel topPanel;
	private final JPanel bottomPanel;
	private JComboBox colorComboBox;
	private JPanel resultsPanel;
	private JTable table;

	/** Commons logging **/
	private static Log log = LogFactory.getLog(ExecutionPanel.class);

	private final CompareRegistry registry;
	private JPanel pickPanel;
	private final VersusTestApp app;
	private JButton submitButton;
	private ResultTableModel resultTableModel;
	private String selectedColorSpace;
	private PickAdapterPanel adapterPanel;
	private PickExtractorPanel extractorPanel;
	private PickMeasurePanel measurePanel;

	public ExecutionPanel(VersusTestApp app, CompareRegistry registry) {

		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.app = app;

		this.registry = registry;

		topPanel = new JPanel();

		add(topPanel);

		add(new JSeparator(JSeparator.HORIZONTAL));

		bottomPanel = new JPanel(new BorderLayout());

		add(bottomPanel);

		createPickPanel();

		createBottomPanel();

	}

	/**
	 * Panel to pick algorithm and launch execution.
	 */
	private void createPickPanel() {

		pickPanel = new JPanel();

		pickPanel.setLayout(new BoxLayout(pickPanel, BoxLayout.Y_AXIS));

		pickPanel.add(new JLabel(
				"Select Data Representation, Feature and Similarity Measure"),
				BorderLayout.NORTH);

		// createComboBoxColor();

		adapterPanel = new PickAdapterPanel();

		pickPanel.add(adapterPanel);

		extractorPanel = new PickExtractorPanel();

		pickPanel.add(extractorPanel);

		measurePanel = new PickMeasurePanel();

		pickPanel.add(measurePanel);

		submitButton = new JButton("Compute");

		submitButton.addActionListener(this);

		pickPanel.add(submitButton);

		topPanel.add(pickPanel);
	}

	/**
	 * 
	 */
	private void createBottomPanel() {
		resultsPanel = new JPanel(new BorderLayout());

		bottomPanel.add(resultsPanel, BorderLayout.NORTH);

		resultsPanel.add(new JLabel("Results"), BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		log.debug("Execution panel action performed");

		this.selectedColorSpace = "RGB";

		String selectedMeasure = measurePanel.getSelectedPrettyName();

		submitButton.setEnabled(false);

		DefaultListModel filesModel = app.getFilesModel();

		// setup list of known files
		File[] files = new File[filesModel.size()];

		for (int i = 0; i < filesModel.size(); i++) {
			files[i] = ((FileEntry) filesModel.getElementAt(i)).getFile();
		}

		// create table for results
		resultsPanel.removeAll();
		resultsPanel.add(new JLabel(selectedMeasure), BorderLayout.NORTH);
		resultsPanel.add(createResultTable(files), BorderLayout.CENTER);
		resultsPanel.updateUI();

		execute(filesModel);
	}

	/**
	 * 
	 * @param filesModel
	 */
	private void execute(DefaultListModel filesModel) {

		try {
			// selected adapter
			String selectedAdapter = adapterPanel.getSelectedClassName();
			// log.debug("Selected adapter is " + selectedAdapter);
			Adapter adapter1 = (Adapter) Class.forName(selectedAdapter)
					.newInstance();
			Adapter adapter2 = (Adapter) Class.forName(selectedAdapter)
					.newInstance();

			// selected extractor
			String selectedExtractor = extractorPanel.getSelectedClassName();
			log.debug("Selected extractor is " + selectedExtractor);
			Extractor extractor = (Extractor) Class.forName(selectedExtractor)
					.newInstance();

			// selected measure
			String selectedMeasure = measurePanel.getSelectedClassName();
			log.debug("Selected measure is " + selectedMeasure);
			Measure measure = (Measure) Class.forName(selectedMeasure)
					.newInstance();

			// Extractor extractor = new HistogramFeatureExtractor();
			//
			// SimilarityMeasure measure = new HistogramDistanceMeasure();

			ExecutorService newFixedThreadPool = Executors
					.newFixedThreadPool(EXECUTION_THREADS);

			for (int i = 0; i < filesModel.size(); i++) {
				for (int j = 0; j < filesModel.size(); j++) {
					FileEntry fileEntry = (FileEntry) filesModel
							.getElementAt(i);
					FileEntry fileEntry2 = (FileEntry) filesModel
							.getElementAt(j);
					File file1 = fileEntry.getFile();
					File file2 = fileEntry2.getFile();

					// if (file1 != file2 && i < j) {
					if (i <= j) {

						ComputeSimilarityThread taskThread = new ComputeSimilarityThread(
								resultTableModel,
								file1,
								file2,
								this.selectedColorSpace,
								adapter1,
								adapter2,
								extractor,
								measure,
								new DoneHandler(),
								new ErrorHandler(this,
										"Error computing similarity. See logs."));

						// log.debug("Launching comparison of " + file1 +
						// " and " + file2);

						newFixedThreadPool.execute(taskThread);
					}
				}
			}

		} catch (InstantiationException e) {
			log.error("Error instantiating class from pulldown menu", e);
		} catch (IllegalAccessException e) {
			log.error("Error instantiating class from pulldown menu", e);
		} catch (ClassNotFoundException e) {
			log.error("Error instantiating class from pulldown menu", e);
		}
	}

	/**
	 * 
	 * @param files
	 * @return
	 */
	private JScrollPane createResultTable(final File... files) {

		resultTableModel = new ResultTableModel(files);
		table = new JTable(resultTableModel);

		// row headers
		final String headers[] = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			headers[i] = files[i].getName();
		}
		ListModel lm = new AbstractListModel() {

			@Override
			public int getSize() {
				return headers.length;
			}

			@Override
			public Object getElementAt(int index) {
				return headers[index];
			}
		};
		JList rowHeader = new JList(lm);
		rowHeader.setFixedCellWidth(200);

		rowHeader.setFixedCellHeight(table.getRowHeight()
				+ table.getRowMargin());
		rowHeader.setCellRenderer(new RowHeaderRenderer(table));

		// scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setRowHeaderView(rowHeader);
		table.setFillsViewportHeight(true);
		return scrollPane;
	}

	class DoneHandler implements Runnable {

		@Override
		public void run() {
			submitButton.setEnabled(true);
			resultsPanel.updateUI();
		}
	}

	class ErrorHandler implements Runnable {

		private final Component parent;
		private final String message;

		public ErrorHandler(Component parent, String message) {
			this.parent = parent;
			this.message = message;
		}

		@Override
		public void run() {
			log.debug("Showing error popup");
			JOptionPane.showMessageDialog(parent, message);
		}
	}
}
