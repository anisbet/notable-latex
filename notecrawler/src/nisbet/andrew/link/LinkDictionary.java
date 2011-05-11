/**
 * 
 */
package nisbet.andrew.link;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import nisbet.andrew.latex.LaTeXLink;
import nisbet.andrew.notecrawler.BestBeforeURL;
import nisbet.andrew.notecrawler.NotableDictionary;

/**
 * Link Dictionary is a serialized form of the links in a document or documents. If you have more than one latex file
 * in the directory it's links will also be added to this dictionary.
 * A link Dictionary is meant as a way to reduce the amount of effort required of sourcing all information from the 
 * web everytime you want to rebuild a document.
 * 
 * This class does maintain links to images related to a link but does not maintain any other image.
 * @author anisbet
 *
 */
public class LinkDictionary implements NotableDictionary
{
	// TODO add XML dictionary as serialized format.
	private static final String KEY_VALUE_DELIMITER = "<=>";
	protected String dictionaryName = null;
	protected Hashtable<String, BestBeforeURL> dictionary = null;
	private boolean hasChanged = false;
	
	
	/**
	 * Reads the dictionary from the file with the name commandDictionary
	 * @param dictionaryName
	 */
	public LinkDictionary( String dictionaryName )
	{
		this.dictionaryName = dictionaryName;
		this.dictionary = new Hashtable<String, BestBeforeURL>();
	}
	
	
	/**
	 * This method takes a line from the link dictionary and parses it back into
	 * a hashtable in memory, ready for reference.
	 * @param line
	 */
	private void parseLine( String line ) 
	{
		String[] value = line.split( KEY_VALUE_DELIMITER );
		if ( value.length > 1 )
		{
			String[] dateLink = value[1].split( BestBeforeURL.DELIMITER );
			// if there was a link found load it.
			if ( dateLink.length > 1 ) // there was a link -- no link no image or caption
			{
				// dates
				String key = value[0];
				Link link = new LaTeXLink(dateLink[1], key);
				BestBeforeURL bbURL = new BestBeforeURL( dateLink[0], link );
				if ( dateLink.length > 2 ) // image is stored too.
				{
					// this is throwing a null pointer exception when you rerun a latex document.
					bbURL.setImage( dateLink[2] );
					if ( dateLink.length > 3 ) // this would fire if there is a caption
					{
						bbURL.setImageCaption( dateLink[3] );
					}
				}
				dictionary.put( key, bbURL );
				return;
			}
		}
		else
		{
			//this.addSymbol( value[0], new BestBeforeURL( "#" ) ); // an empty link that doesn't go anywhere in HTML
			dictionary.put( value[0], new BestBeforeURL( "#" ) );
		}
	}
	

	/**
	 */
	public void readDictionary( )
	{
		dictionary = new Hashtable<String, BestBeforeURL>();
		File dictFile = new File( dictionaryName );
		if ( dictFile.exists() == false )
		{
			return; // don't try to read an non-existent dictionary.
		}
		BufferedReader dictionaryReader = null;
		try
		{
			dictionaryReader = new BufferedReader( new FileReader( dictFile ) );
			String line = new String();
			while ( (line = dictionaryReader.readLine() ) != null)
			{
				parseLine( line );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				dictionaryReader.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Adds a key and value to the dictionary.
	 * @param key
	 * @param bestBeforeURL
	 */
	public void addSymbol( String key, Object bestBeforeURL )
	{
		this.hasChanged = true;
		dictionary.put(key, (BestBeforeURL)bestBeforeURL);
	}
	
	/**
	 * @return true if the dictionary was written to file and false otherwise.
	 * which could be false if the dictionary didn't need to be re-written.
	 */
	public boolean writeToFile()
	{
		if ( this.hasChanged == false )
		{
			return false;
		}
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter( new FileWriter( new File( this.dictionaryName ) ) );
			// get keys from has table.
			Enumeration<String> e = dictionary.keys();
			//iterate through Hashtable keys Enumeration
			String key = new String();
			while(e.hasMoreElements())
			{
				key = e.nextElement();
				writer.write( key );
				writer.write( KEY_VALUE_DELIMITER );
				BestBeforeURL bbu = dictionary.get( key );
				writer.write( bbu.toString() );
				writer.newLine();
			}
		} 
		catch ( Exception e )
		{
			return false;
		}
		finally 
		{
            //Close the BufferedWriter
            try {
                if (writer != null) 
                {
                	writer.flush();
                	writer.close();
                }
            } catch (IOException ex) {
                return false;
            }
        }
		return true;
	}

	/**
	 * @return All the keys of the dictionary.
	 */
	public Enumeration<String> keys() 
	{
		return dictionary.keys();
	}

	/**
	 * @param searchValue
	 * @return The value that matches the keyword of null if not found.
	 */
	public Object getValue( String searchValue ) 
	{
		return dictionary.get( searchValue );
	}


	/**
	 * @param word
	 * @return true if the dictionary contains a link and false otherwise.
	 */
	public boolean containsEntry( String word ) 
	{
		System.out.print( "searching '" );
		System.err.print( word );
		System.out.println( "," );
		return dictionary.containsKey( word );
	}
}
