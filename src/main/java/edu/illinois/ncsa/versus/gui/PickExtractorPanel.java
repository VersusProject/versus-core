/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.registry.CompareRegistry;

/**
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class PickExtractorPanel extends javax.swing.JPanel {

	private final JComboBox			extractorsComboBox;
	private final List<Extractor>	extractors;

	public PickExtractorPanel() {
		super();
		extractors = new ArrayList<Extractor>();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Border createEmptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(createEmptyBorder);

		add(new JLabel("Feature Extractor: "));

		CompareRegistry registry = new CompareRegistry();
		Collection<Extractor> availableExtractors = registry.getAvailableExtractors();

		extractorsComboBox = new JComboBox();

		for (Extractor extractor : availableExtractors) {
			extractors.add(extractor);
			extractorsComboBox.addItem(extractor.getName());
		}

		add(extractorsComboBox);
	}

	public String getSelectedClassName() {
		return extractors.get(extractorsComboBox.getSelectedIndex()).getClass().getName();
	}
}
