package nisbet.andrew.test;

import nisbet.andrew.notecrawler.Preprocessor;
import nisbet.andrew.notecrawler.NoteCrawler;
import nisbet.andrew.notecrawler.NoteCrawlerFactory;

import org.junit.Test;

public class LatexCrawlerTest {

	@Test
	public void testRunAll() 
	{
		
		Preprocessor openNoteBook = null;
		try
		{
			openNoteBook = new Preprocessor( "report.tex" );
		}
		catch ( Exception e )
		{
			System.err.println( "Unable to continue because of unrecoverable errors." );
		}
		
		NoteCrawler classNotes = NoteCrawlerFactory.getNoteBook( openNoteBook );
		classNotes.runAll();
	}

}
