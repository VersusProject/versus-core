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
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.registry.CompareRegistry;

/**
 * Panel to configure adapters to use.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class PickMeasurePanel extends JPanel {

	private final JComboBox		measureComboBox;
	private final List<Measure>	measures;

	public PickMeasurePanel() {
		super();
		measures = new ArrayList<Measure>();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Border createEmptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(createEmptyBorder);

		add(new JLabel("Similarity Measure: "));

		CompareRegistry registry = new CompareRegistry();
		Collection<Measure> availableMeasures = registry.getAvailableMeasures();

		measureComboBox = new JComboBox();

		for (Measure measure : availableMeasures) {
			measures.add(measure);
			measureComboBox.addItem(measure.getName());
		}

		add(measureComboBox);
	}

	public String getSelectedClassName() {
		return measures.get(measureComboBox.getSelectedIndex()).getClass().getName();
	}

	public String getSelectedPrettyName() {
		return measures.get(measureComboBox.getSelectedIndex()).getName();
	}

}
