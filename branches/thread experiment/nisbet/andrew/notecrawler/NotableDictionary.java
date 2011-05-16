package nisbet.andrew.notecrawler;

import java.util.Enumeration;

public interface NotableDictionary
{

	/**
	 * Adds a key and value to the dictionary.
	 * @param key
	 * @param value
	 */

	public abstract void addSymbol(String key, Object value);

	/**
	 * @return true if the dictionary was written to file successfully and false otherwise.
	 */
	public abstract boolean writeToFile();

	/**
	 * @return All the keys of the dictionary.
	 */
	public abstract Enumeration<String> keys();

	/**
	 * @param searchValue
	 * @return The value that matches the keyword of null if not found.
	 */
	public abstract Object getValue(String searchValue);

	/**
	 * @param word
	 * @return true if the dictionary contains an entry for the search word and false otherwise.
	 */
	public abstract boolean containsEntry(String word);

	public abstract void readDictionary();

}