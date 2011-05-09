/**
 * 
 */
package nisbet.andrew.latex;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author anisbet
 *
 */
public class Figure
{
	private boolean isSuccessful; // True if download and save of the image successful
	private boolean hasCaption;   // true if there is a caption and false otherwise.
	private String caption;       // Caption for the image optional
	private String localName;
	
	
	public Figure( ) 
	{
		isSuccessful = false;
		this.caption = new String();
	}

	/**
	 * @param image
	 */
	public Figure( String image ) 
	{
		// find the image and download it.
		isSuccessful = downloadImage( image );
		this.caption = new String();
	}
	
	/**
	 * @param image the url to the image that is to be downloaded.
	 */
	public void getImageDownload( String image ) 
	{
		// find the image and download it.
		isSuccessful = downloadImage( image );
		this.caption = new String();
	}
	
	/**
	 * @param image
	 * @param caption
	 */
	public Figure( String image, String caption )
	{
		isSuccessful = downloadImage( image );
		this.caption = caption;
	}
	
	/**
	 * Takes the name name of an image that is expected to exist on the local file system and 
	 * tests if it is there.
	 * @param imageName name of the image you expect to find.
	 * @return True if the image could be found on the local file system and false otherwise.
	 */
	public boolean setImage( String imageName )
	{
		// take a name and if you can find it in the file system then we're good and if not return false.
		this.localName = imageName;
		this.isSuccessful = Figure.testImageExists( this.localName );
		return this.isSuccessful;
	}

	/**
	 * @param name of the file to test for existance.
	 * @return True if the file exists and is actually a file.
	 */
	public static boolean testImageExists( String name ) 
	{
		File file = new File( name );
		return file.exists() && file.isFile();
	}

	/**
	 * If you are looking to see if an already downloaded image is available for inclusion into the 
	 * notes use {@link #setImage(String)}.
	 * @return True if the download of the image was successful, and false otherwise.
	 */
	public boolean isSuccessful()
	{
		return isSuccessful;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		if ( isSuccessful == false )
		{
			return "";
		}
		
		StringBuffer outBuffer = new StringBuffer();
		outBuffer.append( "\n\\begin{figure}[h!]\n" );
		outBuffer.append( "\t\\centering\n" );
		//outBuffer.append( "\t\\includegraphics[scale=0.5]{" + localName + "}\n" );
		outBuffer.append( "\t\\includegraphics[width=210 px]{" + localName + "}\n" );
		//outBuffer.append( "\t\\includegraphics[width=\\textwidth]{" + localName + "}\n" );
		if ( this.hasCaption )
		{
			outBuffer.append( "\t\\caption{" + caption + "}\n" );
		}
		outBuffer.append( "\\end{figure}\n\n" );
		
		return outBuffer.toString();
	}

	/**
	 * @param caption the hasCaption to set the caption on an image.
	 */
	public void setCaption( String caption )
	{
		this.hasCaption = true;
		this.caption    = caption;
	}

	/**
	 * @param image url string to the image you wish to download. The string may also include a query string
	 * that will be safely ignored.
	 * @return true if the image was downloaded and false otherwise.
	 */
	private boolean downloadImage( String imagePath )
	{
		URL url;
		try 
		{
			url = new URL( imagePath );
			localName = getLocalName( url );
			if ( localName == null )
			{
				return false;
			}
			System.out.println( "file: " + localName + " retreived and written to local file system." );
			BufferedImage image = ImageIO.read( url );
			File outputfile = new File( localName );
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
	 * @return
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
	 * @return The local name of the file as saved in the local file system.
	 */
	public String getLocalFileName() 
	{
		return this.localName;
	}

	/**
	 * @return The caption of the image.
	 */
	public String getCaption()
	{
		return this.caption;
	}
	
}
