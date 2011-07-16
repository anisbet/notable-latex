/**
 * 
 */
package nisbet.andrew.latex;

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
		output = string.replaceAll("%", "");
		output = removePeriods( output );
		return output;
	}

	/**
	 * Removes periods from a file name but not the last and file extension
	 * LaTeX balks at periods, expecting a file extension.
	 * @param output
	 */
	public static String removePeriods(String output) 
	{
		StringBuffer buffer = new StringBuffer(output);
		boolean isFirstPeriod = true;
		for ( int i = buffer.length() -1; i >= 0; i-- )
		{
			if ( buffer.charAt(i) == '.' )
			{
				if ( isFirstPeriod )
				{
					isFirstPeriod = false;
				}
				else
				{
					buffer.deleteCharAt(i);
				}
			}
		}
		return buffer.toString();
	}
}
