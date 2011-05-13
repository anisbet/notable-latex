/**
 * 
 */
package nisbet.andrew.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This class takes a url and name of an image and {@link #getImageURL()} will
 * return the href of the image. This is required because the Wikipedia doesn't
 * want people to actually download their images, so this class will scrape the 
 * page for the requested image and providing its URL so someone else can 
 * download it. There is a terms of use that should be reviewed before using 
 * this class or {@link nisbet.andrew.latex.Figure}.
 * @author anisbet
 *
 */
public class ImageFetcher 
{
	private String imageUrl = new String();
	
	public ImageFetcher( String linkTarget, String myImageName )
	{
		URL yahoo = null;
		try {
			yahoo = new URL( linkTarget );
		
			BufferedReader in;
			in = new BufferedReader( new InputStreamReader( yahoo.openStream() ) );
			String inputLine;
	
			while ( ( inputLine = in.readLine() ) != null )
			{
			    if ( inputLine.contains( myImageName ) )
			    {
			    	imageUrl = stripURL( inputLine );
			    }
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is silly and clumsy but it works.
	 * @param inputLine
	 * @return String contents of the href src taken directly from the HTML page of the article in question.
	 */
	private String stripURL( String inputLine )
	{
		int fromIndex  = inputLine.indexOf( "<img " ) + "<img ".length();
		int beginIndex = inputLine.indexOf( "src=\"", fromIndex ) + "src=\"".length();
		int endIndex   = inputLine.indexOf( "\"", beginIndex );
		return inputLine.substring( beginIndex, endIndex );
	}

	/**
	 * @return the url of the image, as taken from the HTML img tag and src attribute.
	 */
	public String getImageURL() 
	{
		return imageUrl;
	}

}
