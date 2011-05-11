/**
 * 
 */
package nisbet.andrew.notecrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This is a standard name value lookup dictionary implemented as a Hashmap.
 * @author anisbet
 *
 */
public class Dictionary implements NotableDictionary 
{
	
	private static final String KEY_VALUE_DELIMITER = "<=>";
	protected String dictionaryName = null;
	protected Hashtable<String, String> dictionary = null;
	
	
	/**
	 * Reads the dictionary from the file with the name serializedDictionary
	 * @param dictionary
	 */
	public Dictionary( String dictionary )
	{
		dictionaryName = dictionary;
		this.dictionary = new Hashtable<String, String>();
	}
	
	
	/**
	 * @param line
	 */
	private void parseLine( String line ) 
	{
		String[] value = line.split( KEY_VALUE_DELIMITER );
		this.addSymbol( value[0], value[1] );
	}
	

	/**
	 */
	@Override
	public void readDictionary( )
	{
		dictionary = new Hashtable<String, String>();
		File dictFile = new File( dictionaryName );
		if ( dictFile.exists() == false )
		{
			return; // don't try to read an non-existent dictionary.
		}
		BufferedReader dictionaryReader = null;
		try
		{
			dictionaryReader = new BufferedReader( new FileReader( dictFile ));
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
	
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.NotableDictionary#addSymbol(java.lang.String, java.lang.String)
	 */
	
	@Override
	public void addSymbol( String key, Object value )
	{
		dictionary.put( key, (String)value );
	}
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.NotableDictionary#writeToFile()
	 */
	@Override
	public boolean writeToFile()
	{
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
				writer.write( dictionary.get( key ).toString() );
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

	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.NotableDictionary#keys()
	 */
	@Override
	public Enumeration<String> keys() 
	{
		return dictionary.keys();
	}

	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.NotableDictionary#getValue(java.lang.String)
	 */
	@Override
	public String getValue(String searchValue) 
	{
		return dictionary.get( searchValue );
	}


	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.NotableDictionary#containsEntry(java.lang.String)
	 */
	@Override
	public boolean containsEntry( String word ) 
	{
		return dictionary.contains( word );
	}
}
