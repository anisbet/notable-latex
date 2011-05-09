package nisbet.andrew.test;

import nisbet.andrew.notecrawler.LetterOpener;
import nisbet.andrew.notecrawler.NoteCrawler;
import nisbet.andrew.notecrawler.NoteCrawlerFactory;

import org.junit.Test;

public class LatexCrawlerTest {

	@Test
	public void testRunAll() 
	{
		
		LetterOpener openNoteBook = null;
		try
		{
			openNoteBook = new LetterOpener( "report.tex" );
		}
		catch ( Exception e )
		{
			System.err.println( "Unable to continue because of unrecoverable errors." );
		}
		
		NoteCrawler classNotes = NoteCrawlerFactory.getNoteBook( openNoteBook );
		classNotes.runAll();
	}

}
