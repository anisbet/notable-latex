/**
 * 
 */
package nisbet.andrew.notecrawler;

import nisbet.andrew.latex.Crawler;


/**
 * @author anisbet
 *
 */
public class NoteCrawlerFactory
{
	
	public static NoteCrawler getNoteBook( LetterOpener openNoteBook )
	{
		return new Crawler( openNoteBook );
	}
}
