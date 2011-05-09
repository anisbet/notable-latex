/**
 * 
 */
package nisbet.andrew.link;

import nisbet.andrew.latex.Figure;
import nisbet.andrew.latex.LaTeXLink;
import nisbet.andrew.latex.Crawler;
import nisbet.andrew.notecrawler.BestBeforeURL;
import nisbet.andrew.notecrawler.NotableDictionary;
import nisbet.andrew.service.ServiceRequest;
import nisbet.andrew.service.WikipediaServiceRequest;


/**
 * This class manages the request for a link from the users notes. The user requests a link to a pertinate 
 * topic in Wikipedia by surrounding the term or phrase in question with {@link Crawler#LINK_DELIMITER}.
 * @author anisbet
 *
 */
public class LinkCrawler 
{
	private NotableDictionary dictionary = null;
	private BestBeforeURL bbURL = null;
	
	/**
	 * Constructor.
	 * @param linkDictionary (opened).
	 */
	public LinkCrawler( NotableDictionary linkDictionary )
	{
		this.dictionary = linkDictionary;
	}

	
	/**
	 * To get here we need to find a good link and put it in the dictionary like word<=>http://link.
	 * @param string
	 * @return the link or the original search term if the search of Wikipedia failed.
	 */
	public String createLink( String string ) 
	{
		ServiceRequest serviceRequest = new WikipediaServiceRequest( string, "1" );
		Link link = serviceRequest.getLink();
		return link.getLink();
	}
	


	/**
	 * @param searchTerm
	 * @return true if the search term doesn't exist or the link is out-of-date and false otherwise.
	 */
	public boolean needsLink( String searchTerm )
	{
		if ( this.dictionary.containsEntry( searchTerm ) )
		{
			this.bbURL = (BestBeforeURL)this.dictionary.getValue( searchTerm );
			if ( bbURL.isFresh() )
			{
				return false;
			}
		}
		return true;
	}


	/**
	 * Returns a LaTeX formatted hyperlink reference.
	 * @param searchTerm
	 * @return New link from Wikipedia, fresh link from database or original term if neither can be gotten.
	 */
	public Link getLink( String searchTerm ) {
		if ( this.dictionary.containsEntry( searchTerm ) )
		{
			BestBeforeURL bbURL = (BestBeforeURL)this.dictionary.getValue( searchTerm );
			if ( bbURL.isFresh() )
			{
				System.out.println( "the existing link is up-to-date" );
				LaTeXLink latexLink = new LaTeXLink( bbURL.getRawLink(), searchTerm );
				latexLink.setLaTeXFigure( bbURL.getLaTeXFigure() );
				return latexLink;
			}
			System.out.println( "the existing link is out-of-date" );
		}
		// else create a new one
		System.out.println( "creating new query for Wikipedia" );
		ServiceRequest serviceRequest = new WikipediaServiceRequest( searchTerm, "1" );
		if ( serviceRequest.serviceSucceeded() )
		{
			BestBeforeURL bbURL = new BestBeforeURL( new Link( searchTerm ));
			bbURL.setLink( serviceRequest.getLink() );
			if ( serviceRequest.isImageAvailable() )
			{
				Figure currentFigure = new Figure();
				currentFigure.getImageDownload( serviceRequest.getImage() );
				currentFigure.setCaption( serviceRequest.getDescription() );
				if ( currentFigure.isSuccessful() )
				{
					bbURL.setImage( currentFigure );
				}
			}
			
			dictionary.addSymbol( searchTerm, bbURL );
			LaTeXLink latexLink = new LaTeXLink( bbURL.getRawLink(), searchTerm );
			latexLink.setLaTeXFigure( bbURL.getLaTeXFigure() );
			return latexLink;
		}
		else
		{
			System.out.println( "the term '" + serviceRequest.getLink() + 
				"' failed. either the term could not be found or you may have lost connection to the Internet. Rerun." );
		}
		return serviceRequest.getLink(); // a failed request will return just the search term.
	}


	/**
	 * @return True if the URL has an image and false otherwise.
	 */
	public boolean hasImage()
	{
		if ( bbURL == null )
		{
			return false;
		}
		return bbURL.imageExists();
	}


	public String getImage() 
	{
		return bbURL.getLaTeXImageCode();
	}
}
