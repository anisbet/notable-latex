/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.*;

import nisbet.andrew.notecrawler.DictionaryXML;

import org.junit.Test;

/**
 * @author anisbet
 *
 */
public class DictionaryXMLTest {

	/**
	 * Test method for {@link nisbet.andrew.notecrawler.DictionaryXML#DictionaryXML(java.lang.String)}.
	 */
	@Test
	public void testDictionaryXML() {
		DictionaryXML dictXml = new DictionaryXML( "temp.xml" );
		assertNotNull( dictXml );
		dictXml.readDictionary();
		dictXml.addSymbol("one", "one-value");
		dictXml.addSymbol("two", "two-value");
		assertTrue( dictXml.writeToFile() );
	}

}
