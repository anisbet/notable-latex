/**
 * 
 */
package nisbet.andrew;

import nisbet.andrew.notecrawler.LetterOpener;
import nisbet.andrew.notecrawler.NoteCrawler;
import nisbet.andrew.notecrawler.NoteCrawlerFactory;

/**
 * @author anisbet
 *
 */
public class Notary
{
	private static final int EXIT_FAILURE = -1;
	/**
	 * Notary is an application that will crawl through Note books and make annotations for the student.
	 * @param args
	 */
	public static void main(String[] args) {
		if ( Notary.checkArgs(args) == false )
		{
			Notary.usage();
			System.exit( EXIT_FAILURE );
		}
		
		LetterOpener openNoteBook = null;
		
		try
		{
			openNoteBook = new LetterOpener( args[0] );
		}
		catch ( Exception e )
		{
			System.err.println( "Unable to continue because of unrecoverable errors." );
			System.exit( EXIT_FAILURE );
		}
		
		NoteCrawler classNotes = NoteCrawlerFactory.getNoteBook( openNoteBook );
		classNotes.runAll();
	}
	
	private static void usage() {
		System.err.println( "Usage: " + Notary.class.getName() + " [options] notes.file");
		System.err.println( "List of commands:" );
		System.err.println( "\t===[Chapter Title]" );
		System.err.println( "\tcode:\n\t\t[code text verbatim]\n\tcode:" );
		System.err.println( "\tdef:[term] -- [definition]" );
		System.err.println( "\tfig:[figure.ps]\n\t[Figure caption]" );
		System.err.println( "\t+[Phrase to look up]+" );
		System.err.println( "\t# [item of the numbered list.]" );
		System.err.println( "\t- [item of the itemized list.]" );
		System.err.println( "\t\\t[Section Name]" );
		System.err.println( "\t\\t\\t[Sub Section Name]" );
		System.err.println( "\t\\t\\t\\t[Sub Sub Section Name]" );
		System.err.println( "\ttable:\n\t-\n\ttitle | next title\n\t-\n\tone | next one\n\ttwo | next two\n\t-\n\ttable:" );
		System.err.println( "\tall commands must appear as the first text on a line." );
		System.err.println( "\tAlso note that there need not be a space between the command and its arguments;" +
				"in fact fig: requires that there be no space." );

	}
	
	
	/**
	 * Checks for correct note files and arguments. 
	 * @param arguments
	 * @return
	 */
	private static boolean checkArgs( String[] arguments )
	{
		if ( arguments.length == 1 )
		{
			return true;
		}
		return false;
	}

}
