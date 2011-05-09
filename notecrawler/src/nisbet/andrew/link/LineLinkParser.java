/**
 * 
 */
package nisbet.andrew.link;

import java.util.Vector;

/**
 * This class will parse out the links in a string ignoring the links tokens within math expressions.
 * @author anisbet
 *
 */
public class LineLinkParser 
{
	public final static String DELIMITER = "+";
	private char delimiter;
	private Vector<String> linkTargets;
	private boolean isMathOperation;
	
	/**
	 * @param line
	 * @param delimiter
	 */
	public LineLinkParser( String line, char delimiter )
	{
		linkTargets = new Vector<String>();
		this.delimiter = delimiter;
		this.isMathOperation = false;
		parseLine( line );
	}
	
	/**
	 * @param line read from the notes.
	 */
	protected void parseLine( String line ) {
				
		int index = 0;
		while ( index < line.length() )
		{
			if ( line.charAt(index) == '$' )
			{
				if ( isMathOperation == true )
					isMathOperation = false;
				else
					isMathOperation = true;
			}
			
			if ( isMathOperation == false && line.charAt(index) == this.delimiter )
			{
				StringBuffer searchWord = new StringBuffer();
				index++; // skip the found demimiter
				while ( index < line.length() && line.charAt(index) != this.delimiter )
				{
					searchWord.append( line.charAt( index ) );
					index++;
				}
				//System.out.println( "'" + searchWord.toString() + "'" );
				linkTargets.add( searchWord.toString() );
			}
			index++; // if not in link advance count if in link skips last delimiter.
		}
	}


	/**
	 * @return true if the line has more links in it and false otherwise.
	 */
	public boolean hasMoreLinks() 
	{
		return linkTargets.size() > 0;
	}

	/**
	 * @return the next link on the line. Links are consumed as this method is called.
	 */
	public String nextLink() 
	{
		return linkTargets.remove(0);
	}
}
