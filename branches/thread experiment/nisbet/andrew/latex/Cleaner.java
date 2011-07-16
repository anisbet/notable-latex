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
//		System.out.println("\n\n\n=====\n" + string + "\n\n\n=====\n");
		if ( string == null || string.length() == 0 )
		{
			return "";
		}
		String output = new String();
		output = string.replaceAll("%", "");
//		output = output.replaceAll(".", "0");
//		output = output.replaceAll("0jpg", ".jpg");
		return output;
	}
}
