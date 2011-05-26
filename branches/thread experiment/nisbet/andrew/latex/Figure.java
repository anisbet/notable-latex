/**
 * 
 */
package nisbet.andrew.latex;

import nisbet.andrew.service.ImageFetcher;

/**
 * Figure is the encapsulation of a LaTeX figure. Given the information about the image this 
 * class formats the request into legal latex code for output.
 * @author anisbet
 *
 */
public class Figure
{
	private boolean isSuccessful; // True if download and save of the image successful
	private boolean hasCaption;   // true if there is a caption and false otherwise.
	private String caption;       // Caption for the image optional
	private String localName;     // name of the file on the local fs. Needed for the fig: tag.
	
	
	public Figure( ) 
	{
		this.isSuccessful = false;
		this.caption = new String();
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
		this.isSuccessful = ImageFetcher.testImageExists( this.localName );
		return this.isSuccessful;
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
		if ( this.isSuccessful == false )
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
