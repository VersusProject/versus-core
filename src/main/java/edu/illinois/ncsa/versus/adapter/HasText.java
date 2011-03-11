/**
 * 
 */
package edu.illinois.ncsa.versus.adapter;

import java.util.List;

/**
 * Anything that has text.
 * 
 * @author Luigi Marini
 *
 */
public interface HasText extends Adapter {

	/**
	 * Get the list of words in the document.
	 * 
	 * @return array of words
	 */
	List<String> getWords();
}
