/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.*;

import java.io.File;

import nisbet.andrew.link.Link;
import nisbet.andrew.link.LinkDictionary;
import nisbet.andrew.notecrawler.BestBeforeURL;

import org.junit.Test;

/**
 * @author anisbet
 *
 */
public class DictionaryTest {


	/**
	 * Test method for {@link nisbet.andrew.notecrawler.Dictionary#Dictionary(java.lang.String)}.
	 */
	@Test
	public void testDictionaryString() {
		LinkDictionary dict = new LinkDictionary( "test.txt" );
		dict.readDictionary();
		dict.addSymbol("name", new BestBeforeURL( new Link("#") ));
		dict.writeToFile();
		assertNotNull( new File( "test.txt" ) );
	}

	/**
	 * Test method for nisbet.andrew.notecrawler.Dictionary.
	 */
	@Test
	public void testAddSymbol() {
		LinkDictionary dict = new LinkDictionary( "test.txt" );
		dict.readDictionary();
		dict.addSymbol("name2", new BestBeforeURL( "www.google.ca", "name2" ) );
		dict.writeToFile();
		assertNotNull( new File( "test.txt" ) ); 
	}

}
