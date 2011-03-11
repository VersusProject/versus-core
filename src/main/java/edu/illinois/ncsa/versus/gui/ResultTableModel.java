/**
 * 
 */
package edu.illinois.ncsa.versus.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class ResultTableModel extends AbstractTableModel {

	private final List<String>	columnHeaders;

	private final List<String>	rowHeaders;

	private final String[][]	data;

	public ResultTableModel(File... files) {
		columnHeaders = new ArrayList<String>();
		rowHeaders = new ArrayList<String>();
		for (File file : files) {
			columnHeaders.add(file.getName());
		}
		data = new String[files.length][files.length];
	}

	public void addFile(File file) {
		columnHeaders.add(file.getName());
		rowHeaders.add(file.getName());
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return columnHeaders.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnHeaders.get(column);
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// if (rowIndex == 0) {
		// return columnHeaders.get(columnIndex - 1);
		// } else if (columnIndex == 0) {
		// return rowHeaders.get(rowIndex - 1);
		// } else {
		// return null;
		// }
		return data[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = (String) aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public void setValueAt(double value, File file1, File file2) {
		int indexOfFile1 = columnHeaders.indexOf(file1.getName());
		int indexOfFile2 = columnHeaders.indexOf(file2.getName());
		setValueAt(String.valueOf(value), indexOfFile2, indexOfFile1);
//		setValueAt(file1.toString() + " - " + file2.toString(), indexOfFile2, indexOfFile1);
	}

	public void setValueAt(Object value, File file1, File file2) {
		int indexOfFile1 = columnHeaders.indexOf(file1.getName());
		int indexOfFile2 = columnHeaders.indexOf(file2.getName());
		setValueAt(String.valueOf(value), indexOfFile2, indexOfFile1);
//		setValueAt(file1.toString() + " - " + file2.toString(), indexOfFile2, indexOfFile1);
	}

}
