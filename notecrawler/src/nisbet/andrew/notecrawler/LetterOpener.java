/**
 * 
 */
package nisbet.andrew.notecrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A convenience class for opening the note file.
 * You may place a AUTHOR:author name on a single line and or a TITLE: on a single line.
 * You may retrieve these values with their associated methods {@link #getAuthor()}, or {@link #getTitle()};
 * @author anisbet
 *
 */
public class LetterOpener
{
	
	private BufferedReader bReader = null;
	private String fileName = null;
	private String author;
	private String title;
	public static final String AUTHOR_DELIMITER = "AUTHOR:";
	public static final String TITLE_DELIMITER = "TITLE:";
	
	/**
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public LetterOpener( String fileName ) throws FileNotFoundException
	{
		this.fileName = fileName;
		File noteFile = new File( fileName );
		bReader = new BufferedReader( new FileReader( noteFile ) );
		try 
		{
			String line = null;
			int found = 0;
			while ( (line = this.bReader.readLine()) != null ) // this consumes lines with author or title and does not pass them onto the document.
				// document calls the getAuthor method or the getTitle method when it requires these values.
			{
				if ( line.contains(AUTHOR_DELIMITER) )
				{
					// don't pass this onto the document but set the author for a document to call at it's leisure.
					// take all the text from the end of the delimiter to the end of the line.
					this.author = line.substring( line.indexOf(AUTHOR_DELIMITER) + AUTHOR_DELIMITER.length() );
					found++; // consume this line.
				}
				else if ( line.contains(TITLE_DELIMITER) ) 
				{
					this.title = line.substring( line.indexOf(TITLE_DELIMITER) + TITLE_DELIMITER.length() );
					found++;
				}
				if ( found == 2 ) break;
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * Closes the notes file. It is important you do this.
	 */
	public void close()
	{
		try
		{
			bReader.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Name of original file.
	 */
	public String getFileName() 
	{
		return this.fileName;
	}

	/**
	 * Reads a line from the file and returns it to the caller.
	 * @return String that contains the contents of a line read from file.
	 */
	public String readLine() 
	{
		try 
		{
			String line = this.bReader.readLine();
			if ( line != null && line.contains(AUTHOR_DELIMITER) )
			{
				return ""; // consume this line.
			}
			if ( line != null && line.contains(TITLE_DELIMITER) ) 
			{
				return "";
			}
			return line;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return True if the underlying stream can be marked and false otherwise.
	 */
	public boolean markSupported()
	{
		return this.bReader.markSupported();
	}

	/**
	 * Place in the stream to return to if reset is called (and we haven't already read another 1024 bytes of the 
	 * stream.
	 */
	public void mark() 
	{
		try 
		{
			this.bReader.mark( 1024 );
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Resets the data stream in the case that you have to rewind the file pointer to a previously {@link #mark()}.
	 */
	public void reset()
	{
		try 
		{
			this.bReader.reset();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return author of the publication if any.
	 */
	public String getAuthor() 
	{
		return this.author;
	}

	/**
	 * @return Name of the class or title of the document you would like.
	 */
	public String getTitle() 
	{
		return this.title;
	}
	
}
