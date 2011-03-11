/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Luigi Marini
 *
 */
@SuppressWarnings("serial")
public class FileCellRenderer extends JLabel implements ListCellRenderer {

	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	
	public FileCellRenderer() {
        setOpaque(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList arg0, Object value,
			int arg2, boolean isSelected, boolean arg4) {
		
		FileEntry fileEntry = (FileEntry) value;
		
		setText(fileEntry.getFile().getName());
		
		if(isSelected) {
            setBackground(HIGHLIGHT_COLOR);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
		
		return this;
	}

}
