/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

/**
 * @author lmarini
 * 
 */
@SuppressWarnings("serial")
public class RowHeaderRenderer extends JLabel implements ListCellRenderer {

	public RowHeaderRenderer(JTable table) {
		JTableHeader header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
		setSize(200, 30);
	}

	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
		setText((arg1 == null) ? "" : arg1.toString());
		return this;
	}

}
