package nisbet.andrew.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nisbet.andrew.link.LinkDictionaryXML;
import nisbet.andrew.notecrawler.BestBeforeURL;

import org.junit.Test;

public class LinkDictionaryXMLTest {

	@Test
	public void testLinkDictionaryXML() {
		LinkDictionaryXML dictXml = new LinkDictionaryXML( "tempLink.xml" );
		assertNotNull( dictXml );
		dictXml.readDictionary();
		BestBeforeURL bbURL = new BestBeforeURL("name2", "www.google.ca");
		dictXml.addSymbol("one", bbURL);
		dictXml.addSymbol("two", bbURL);
		assertTrue( dictXml.writeToFile() );
	}

}
