/**
 * 
 */
package nisbet.andrew.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

/**
 * This class takes a url and name of an image and will
 * find the src of the image. This is required because the Wikipedia doesn't
 * want people to actually download their images, so this class will scrape the 
 * page for the requested image and providing its URL so someone else can 
 * download it. There is a terms of use that should be reviewed before using 
 * this class or {@link nisbet.andrew.latex.Figure}.
 * 
 * Pass in an HTML page and ImageFetcher will search for a reference to it and 
 * download the image from the URL specified in the src attribute of the associated img tag.
 * @author anisbet
 *
 */
public class ImageFetcher 
{
	public final static int MINIMUM_IMAGE_SIZE = 100;
	private int size;
	private URL imageURL;       // the page that the image source can be found on.
	private String searchName;  // the most basic name this image has to be found in an HTML page source.
	private String imageName;   // the name of the file to find on the page.
	
	/**
	 * Reads the HTML found at imageSrc and looks for the reference to imageName stores it as
	 * the image URL.
	 * @param imageSrc
	 * @param basicImageSearchName
	 */
	public ImageFetcher( String imageSrc, String basicImageSearchName ) throws UnknownHostException
	{
		this.imageURL = null;
		try {
			this.imageURL = new URL( imageSrc );
			this.searchName = basicImageSearchName;
			BufferedReader in;
			in = new BufferedReader( new InputStreamReader( this.imageURL.openStream() ) );
			String inputLine;
			// read the content of the URL until you get an image mention.
			while ( ( inputLine = in.readLine() ) != null )
			{
			    if ( inputLine.contains( this.searchName ) )
			    {
			    	this.imageURL = new URL( getImageSrc( inputLine ) );
			    	// this is the true name from the html SRC attribute of IMG tag - but not the complete url.
			    	this.imageName= getLocalName(this.imageURL);
			    }
			}
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return True if the image was down loaded correctly and false otherwise.
	 */
	public boolean download( )
	{
		try 
		{
			if ( this.imageName == null )
			{
				return false;
			}
			System.out.println( "file: " + this.imageName + " retreived and written to local file system." );
			BufferedImage image = ImageIO.read( this.imageURL );
			File outputfile = new File( this.imageName );
		    ImageIO.write( image, "jpg", outputfile );
		} 
		catch (MalformedURLException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Given a URL it will strip off the file name and even remove the query if any.
	 * @param url
	 * @return the name of the file that will be stored on the local filesystem.
	 */
	private String getLocalName( URL url ) 
	{
		String temp = url.getFile();
		String[] dirs = temp.split("/");
		if ( dirs.length > 0 )
		{
			String name =  dirs[ dirs.length -1 ];
			// just to make sure we don't include a query...
			if ( name.contains("?") )
			{
				String[] tname = name.split("\\?");
				name = tname[0];
			}
			return name;
		}
		return null;
	}

	/**
	 * This is silly and clumsy but it works.
	 * @param inputLine
	 * @return String contents of the href src taken directly from the HTML page of the article in question.
	 */
	private String getImageSrc( String inputLine )
	{
		int fromIndex  = inputLine.indexOf( "<img " ) + "<img ".length();
		int beginIndex = inputLine.indexOf( "src=\"", fromIndex ) + "src=\"".length();
		int endIndex   = inputLine.indexOf( "\"", beginIndex );
		this.size = getImageSize( inputLine );
		return inputLine.substring( beginIndex, endIndex );
	}

	/**
	 * @param inputLine
	 * @return the size from the width attribute of the IMG tag, or 0 if it isn't set or 
	 * the size could not be converted to an integer.
	 */
	private int getImageSize(String inputLine) {
		int fromIndex  = inputLine.indexOf( "<img " ) + "<img ".length();
		int beginIndex = inputLine.indexOf( "width=\"", fromIndex ) + "width=\"".length();
		int endIndex   = inputLine.indexOf( "\"", beginIndex );
		String size = inputLine.substring(beginIndex, endIndex);
		try
		{
			return Integer.parseInt(size);
		}
		catch ( NumberFormatException e ) // incase the value couldn't be extracted from the image tag.
		{
			return 0;
		}
	}
	
	/**
	 * @param image name with expected path so a File can be tested.
	 * @return
	 */
	public static boolean testImageExists( String image )
	{
		File file = new File( image );
		if ( file.exists() && file.isFile() )
		{
			return true;
		}
		
		return false;
	}

	/**
	 * @return the url of the image, as taken from the HTML img tag and src attribute.
	 */
	public String getImageURL() 
	{
		return this.imageURL.toString();
	}

	/**
	 * @return true if the image is bigger than MINIMUM_IMAGE_SIZE and false otherwise.
	 */
	public boolean isBigEnough() 
	{
		return this.size >= ImageFetcher.MINIMUM_IMAGE_SIZE;
	}
	
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}

}
