/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menu bar.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class Menu extends JMenuBar {

	private JMenu fileMenu;
	private JMenuItem openFileItem;
	private JMenuItem exitItem;

	/**
	 * Construct a menu with the proper entries.
	 */
	public Menu() {
		super();
		populate();
	}

	/**
	 * Create entries.
	 */
	private void populate() {

		// File
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		fileMenu.getAccessibleContext().setAccessibleDescription(
				"Menu for operations on files");
		add(fileMenu);

		// open
		openFileItem = new JMenuItem("Open");
		fileMenu.add(openFileItem);

		// exit
		fileMenu.addSeparator();
		exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
	}

	public void setOpenFileItem(JMenuItem openFileItem) {
		this.openFileItem = openFileItem;
	}

	public JMenuItem getOpenFileItem() {
		return openFileItem;
	}

	public void setExitItem(JMenuItem exitItem) {
		this.exitItem = exitItem;
	}

	public JMenuItem getExitItem() {
		return exitItem;
	}
}
