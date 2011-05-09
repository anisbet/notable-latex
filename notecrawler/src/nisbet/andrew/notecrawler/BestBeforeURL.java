/**
 * 
 */
package nisbet.andrew.notecrawler;

import java.util.Calendar;
import java.util.Date;

import nisbet.andrew.latex.Figure;
import nisbet.andrew.latex.LaTeXLink;
import nisbet.andrew.link.Link;

/**
 * This class contains a URL and a best before date that can be queried for staleness.
 * Don't forget to set the setMonthsFreshness() to a date in the future. To ensure links
 * are not always going out of date in a month.
 * @author anisbet
 */
public class BestBeforeURL 
{
	public static final int MONTHS_FRESHNESS = 6; // the number of months until the URL needs to be updated.
	public static final String DELIMITER = "==";  // change this if it conflicts on output.
	public static final int YEAR = 5;
	public static final int MONTH = 1;
	public static final int DAY = 2;
	private Calendar bestBeforeDate = null;
	private Figure latexFigure = null;
	private Link url;
	
	// extensions for keeping images too.
	//private String image;
	
	/**
	 * Creates a best before link with a future expirey of +6 months from now.
	 * @param link the search term, URL and expirey date.
	 */
	public BestBeforeURL( Link link )
	{
		initBestBeforeURL();
		url = link;
	}
	

	
	/**
	 * Creates a {@link BestBeforeURL} plain link with just the name of the link.
	 * Used if the word does not have a link for some reason like an error on lookup.
	 * @param linkName
	 */
	public BestBeforeURL( String linkName )
	{
		initBestBeforeURL();
		url = new Link( linkName );
	}
	
	/**
	 * Helper method that is run from each constructor.
	 */
	private void initBestBeforeURL()
	{
		bestBeforeDate = Calendar.getInstance();
		bestBeforeDate.setTime( new Date() );
		bestBeforeDate.add( Calendar.MONTH, MONTHS_FRESHNESS );
		latexFigure = new Figure();
	}

	/**
	 * Used when reading from a file the serialized values from the text file.
	 * @param serializedDate
	 * @param link
	 */
	public BestBeforeURL( String serializedDate, Link link )
	{
		setBestBeforeDate( serializedDate );
		latexFigure = new Figure();
		url = link;
	}
	
	/**
	 * Creates a {@link BestBeforeURL} object automatically creating a {@link LaTeXLink} object
	 * from target and name.
	 * @param target
	 * @param name
	 */
	public BestBeforeURL( String target, String name )
	{
		initBestBeforeURL();
		url = new LaTeXLink( target, name );
	}

	/**
	 * Convert Name to an integer.
	 * @param string
	 * @return
	 */
	private int getMonth( String string ) 
	{
		if (string.equalsIgnoreCase("Jan"))
		{
			return 0;
		} 
		else if (string.equalsIgnoreCase("Feb"))
		{
			return 1;
		} 
		else if (string.equalsIgnoreCase("Mar"))
		{
			return 2;
		} 
		else if (string.equalsIgnoreCase("Apr"))
		{
			return 3;
		} 
		else if (string.equalsIgnoreCase("May"))
		{
			return 4;
		} 
		else if (string.equalsIgnoreCase("Jun"))
		{
			return 5;
		} 
		else if (string.equalsIgnoreCase("Jul"))
		{
			return 6;
		} 
		else if (string.equalsIgnoreCase("Aug"))
		{
			return 7;
		} 
		else if (string.equalsIgnoreCase("Sep"))
		{
			return 8;
		} 
		else if (string.equalsIgnoreCase("Oct"))
		{
			return 9;
		} 
		else if (string.equalsIgnoreCase("Nov"))
		{
			return 10;
		}
		return 11;
	}

	/**
	 * Setting a positive month value will cause the date to be set to a future month,
	 * setting to a negative value will make it expire immediately. Setting this value
	 * will set the date to the current date and time and then forward or backward in
	 * time based on the value passed. The stored date value is touched before the 
	 * new months of freshness are added.
	 * @param monthsFreshness
	 */
	public void setMonthsFreshness( int monthsFreshness ) 
	{
		touch();
		bestBeforeDate.add( Calendar.MONTH, monthsFreshness );
	}
	
	/**
	 * Resets the date for the link to now.
	 */
	public void touch()
	{
		bestBeforeDate.setTime( new Date() );
	}

	/**
	 * @param link the stored link.
	 */
	public void setLink( Link link )
	{
		this.url = link;
	}
	
	/**
	 * @param link - the stored link.
	 */
	public void setLink( String link )
	{
		Link latexLink = new LaTeXLink( link, this.url.getName() );
		this.url = latexLink;
	}
	
	/**
	 * @return true if the link is within the best before date.
	 */
	public boolean isFresh()
	{
		Calendar now = Calendar.getInstance();
		now.setTime( new Date() );
		return bestBeforeDate.compareTo( now ) >= 0;
	}
	
	/**
	 * @return true if a image file as read from the link dictionary exists and false otherwise. It could be 
	 * false if the dictionary has no entry - that is, there was no image to get from wikipedia.
	 */
	public boolean imageExists()
	{
		return this.latexFigure.isSuccessful();
	}
	
	/**
	 * @return name of the image file associated with this URL if any. See {@link #imageExists()}.
	 */
	public String getLaTeXImageCode()
	{
		return this.latexFigure.toString();
	}
	
	/**
	 * @return The stored link
	 */
	public Link getLink()
	{
		return url;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer out = new StringBuffer();
		out.append( bestBeforeDate.getTime().toString() );
		out.append( DELIMITER );
		out.append( url.getRawLink() );
		// this is where you append the image information to be serialized to file.
		if ( imageExists() )
		{
			out.append( DELIMITER );
			out.append( this.latexFigure.getLocalFileName() );
			out.append( DELIMITER );
			out.append( this.latexFigure.getCaption() );
		}
		return out.toString();
	}

	/**
	 * @return True if the link object actually has a link and false otherwise.
	 */
	public boolean hasLink() 
	{
		return url.length() > 1;
	}

	/**
	 * @return url of the page with no other LaTeX adornments.
	 */
	public String getRawLink() 
	{
		return this.url.getRawLink();
	}

	/**
	 * @param string message to appear in the caption below the image.
	 */
	public void setImageCaption(String string) 
	{
		this.latexFigure.setCaption( string );
	}



	/**
	 * Allows others to create their own latex figures and associate them with this URL.
	 * @see nisbet.andrew.link.LinkCrawler
	 * @param currentFigure
	 */
	public void setImage( Figure currentFigure ) 
	{
		this.latexFigure = currentFigure;
	}
	
	/**
	 * Used by a dictionary to load images stored in association with a BestBeforeURL.
	 * @param image the name of the file in the local file system associated with this link.
	 */
	public void setImage( String image ) // add another string param for the caption then make a LatexFigure in here.
	{
		this.latexFigure.setImage( image );
	}

	/**
	 * @return The local file name of the image stored as a Figure.
	 */
	public String getImage()
	{
		return this.latexFigure.getLocalFileName();
	}


	/**
	 * @return Figure stored in the BBURL
	 */
	public Figure getLaTeXFigure()
	{
		return this.latexFigure;
	}



	/**
	 * @return the bestBeforeDate
	 */
	public String getBestBeforeDate() 
	{
		return bestBeforeDate.getTime().toString();
	}



	/**
	 * @param bestBeforeDate the bestBeforeDate to set
	 */
	public void setBestBeforeDate( String date ) 
	{
		this.bestBeforeDate = Calendar.getInstance();
		String[] dateString = date.split( " " );
		this.bestBeforeDate.set( Integer.parseInt( dateString[YEAR] ), getMonth( dateString[MONTH] ),  Integer.parseInt( dateString[DAY] ) );
	}


	/**
	 * @return the url
	 */
	public Link getUrl() {
		return url;
	}



	/**
	 * @return Caption of the Figure.
	 */
	public String getImageCaption() 
	{
		return this.latexFigure.getCaption();
	}



	/**
	 * @return String of the url associated with this BestBeforeURL object.
	 */
	public String getURL() 
	{
		return this.url.getRawLink();
	}

}
