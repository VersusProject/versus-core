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

import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.registry.CompareRegistry;

/**
 * Panel to configure adapters to use.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class PickAdapterPanel extends JPanel {

	private final JComboBox		adaptersComboBox;
	private final List<Adapter>	adapters;

	public PickAdapterPanel() {
		super();
		adapters = new ArrayList<Adapter>();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Border createEmptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(createEmptyBorder);

		add(new JLabel("Data Representation: "));

		CompareRegistry registry = new CompareRegistry();
		Collection<Adapter> availableAdapters = registry.getAvailableAdapters();

		adaptersComboBox = new JComboBox();

		for (Adapter adapter : availableAdapters) {
			adapters.add(adapter);
			adaptersComboBox.addItem(adapter.getName());
		}

		add(adaptersComboBox);
	}

	public String getSelectedClassName() {
		return adapters.get(adaptersComboBox.getSelectedIndex()).getClass().getName();
	}
}
