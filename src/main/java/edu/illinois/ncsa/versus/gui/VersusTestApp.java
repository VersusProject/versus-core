/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.media.jai.codec.MemoryCacheSeekableStream;
import com.sun.media.jai.widget.DisplayJAI;

import edu.illinois.ncsa.versus.registry.CompareRegistry;

/**
 * A simple application to test the library.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class VersusTestApp extends JFrame implements ListSelectionListener {

	/** Commons logging **/
	private static Log						log					= LogFactory.getLog(VersusTestApp.class);

	private static final int				THUMBNAIL_SIZE		= 100;

	private final Container					container;

	private final List<File>				files;

	private final JPanel					filesPanel;

	private final JList						filesList;

	private final DefaultListModel			filesModel;

	private final JSplitPane				mainSplitPane;

	private Menu							menuBar;

	private final Map<File, RenderedImage>	thumbnails;

	private final DisplayJAI				thumbnailPreview;

	private final JSplitPane				listSplitPanel;

	private final ExecutionPanel			executionPanel;

	public final CompareRegistry			registry;

	private final String					currentColorSpace	= "RGB";

	/**
	 * 
	 * @throws Exception
	 */
	public VersusTestApp() throws Exception {

		super("Versus");

		registry = new CompareRegistry();

		thumbnails = new HashMap<File, RenderedImage>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setMinimumSize(new Dimension(500, 500));

		container = getContentPane();

		container.setLayout(new BorderLayout());

		// menu bar
		setupMenu();

		// file list
		filesPanel = new JPanel();
		filesModel = new DefaultListModel();
		filesList = new JList(filesModel);
		filesList.setCellRenderer(new FileCellRenderer());
		JScrollPane listScrollPane = new JScrollPane(filesList);
		filesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filesList.addListSelectionListener(this);
		filesPanel.add(listScrollPane);

		// list split pane with preview at the bottom
		thumbnailPreview = new DisplayJAI();
		listSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScrollPane, thumbnailPreview);
		listSplitPanel.setOneTouchExpandable(true);
		listSplitPanel.setDividerLocation(300);
		Dimension minimumSize = new Dimension(100, 50);
		// listScrollPane.setMinimumSize(minimumSize);
		// thumbnailPreview.setMinimumSize(minimumSize);
		// listSplitPanel.setPreferredSize(new Dimension(400, 200));
		thumbnailPreview.setMinimumSize(new Dimension(THUMBNAIL_SIZE, THUMBNAIL_SIZE));
		thumbnailPreview.setPreferredSize(new Dimension(THUMBNAIL_SIZE, THUMBNAIL_SIZE));

		// right main container
		executionPanel = new ExecutionPanel(this, registry);

		JScrollPane pictureScrollPane = new JScrollPane(executionPanel);

		// Create a split pane with the two scroll panes in it
		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listSplitPanel, pictureScrollPane);
		mainSplitPane.setOneTouchExpandable(true);
		mainSplitPane.setDividerLocation(150);

		// Provide minimum sizes for the two components in the split pane
		listScrollPane.setMinimumSize(minimumSize);
		pictureScrollPane.setMinimumSize(minimumSize);

		// Provide a preferred size for the split pane
		mainSplitPane.setPreferredSize(new Dimension(400, 200));

		container.add(mainSplitPane);

		files = new ArrayList<File>();

		pack();
		setVisible(true);
	}

	/**
	 * Creates and instance and loads a series of files.
	 * 
	 * @throws Exception
	 * 
	 */
	public VersusTestApp(File... files) throws Exception {
		this();
		for (File file : files) {
			loadFile(file);
		}
	}

	/**
	 * Setup the menu bar.
	 */
	private void setupMenu() {

		menuBar = new Menu();

		menuBar.getOpenFileItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});

		menuBar.getExitItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});

		getRootPane().setJMenuBar(menuBar);
	}

	/**
	 * Create a thumbnail from a file on disk.
	 * 
	 * @param file
	 * @param size
	 * @return
	 * @throws FileNotFoundException
	 */
	private RenderedImage scale(File file, double size) throws FileNotFoundException {
		log.debug("Creating thumbnail for " + file.getName());
		RenderedOp op1 = JAI.create("stream", new MemoryCacheSeekableStream(new FileInputStream(file)));
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(op1);
		pb.add(size / op1.getWidth());
		pb.add(size / op1.getHeight());
		RenderedOp op2 = JAI.create("SubsampleAverage", pb, qualityHints);
		return op2.getAsBufferedImage();
	}

	/**
	 * File chooser.
	 */
	public void chooseFile() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setMultiSelectionEnabled(true);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = fileChooser.getSelectedFiles();
			for (File file : selectedFiles) {
				loadFile(file);

			}
		}
	}

	/**
	 * Add a file to the list of know files.
	 * 
	 * @param file
	 */
	private void loadFile(File file) {
		filesModel.addElement(new FileEntry(file));
		files.add(file);
	}

	/**
	 * Launch an instance of the application. Load two test files.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File[] files = new File[0];
		//files[0] = new File("data/test_1.jpg");
		//files[1] = new File("data/test_2.jpg");
		new VersusTestApp(files);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		FileEntry selectedValue = (FileEntry) list.getSelectedValue();
		File file = selectedValue.getFile();

		RenderedImage thumbnail = thumbnails.get(file);

		try {
			thumbnail = scale(file, THUMBNAIL_SIZE);
			thumbnails.put(file, thumbnail);
		} catch (FileNotFoundException e1) {
			log.error("Unable to create thumbnail", e1);
		}

		thumbnailPreview.set(thumbnails.get(file));
	}

	/**
	 * Files model representing the files currently known to the app.
	 * 
	 * @return default list model
	 */
	public DefaultListModel getFilesModel() {
		return filesModel;
	}

}
