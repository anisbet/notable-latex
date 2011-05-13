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
	
	public static NoteCrawler getNoteBook( Preprocessor openNoteBook )
	{
		return new Crawler( openNoteBook );
	}
}
