/**
 * 
 */
package nisbet.andrew.latex;

import java.util.Vector;

/**
 * @author anisbet
 *
 */
public class Cleaner 
{
	public static String clean( String input )
	{
		
//		System.out.println("\n\n\n=====\n" + input + "\n\n\n=====\n");
		if ( input == null || input.length() == 0 )
		{
			return "";
		}
		String output = new String();
		output = input.replaceAll("\\\\", "\\\\\\\\");
		output = input.replaceAll("_", "\\_");
		return output;
	}
	
	public static String cleanURLEncoded( String string )
	{
		if ( string == null || string.length() == 0 )
		{
			return "";
		}
		String output = new String();
		output = cleanSpecialCharacters( output );
		return output;
	}

	/**
	 * Removes periods from a file name but not the last and file extension
	 * LaTeX balks at periods, expecting a file extension.
	 * @param output
	 */
	public static String cleanSpecialCharacters(String output) 
	{
		Vector<Character> buffer = new Vector<Character>();
		boolean isFirstPeriod = true;
		for ( int i = output.length() -1; i >= 0; i-- )
		{
			char c = output.charAt(i);
			if ( c == '.' ) // any dot in the name is thought to start the extension which is stupid.
			{
				if ( isFirstPeriod )
				{
					isFirstPeriod = false;
				}
				else
				{
					continue;
				}
			}
			else if (c == '_' || c == '-' || c == '%') // % is the worst because it comments the rest of the line.
			{
				continue;
			}
			buffer.insertElementAt(c, 0);
		}
		StringBuffer buffOut = new StringBuffer();
		for ( Character c : buffer )
		{
			buffOut.append(c);
		}
		
		return buffOut.toString();
	}
}
