/**
 * 
 */
package nisbet.andrew.notecrawler;

import java.util.Vector;

/**
 * @author anisbet
 *
 */
public final class Logger 
{
	// Feel free to add other catagories of error strings.
	private static Vector<String> unresolvedLinks = new Vector<String>();
	
	private Logger(){}
	
	public static void setUnresolvedLink(String link)
	{
		unresolvedLinks.add(link);
	}
	

	/**
	 * @return String of problems found in your document.
	 */
	public static String getMessages()
	{
		StringBuffer outBuff = new StringBuffer();
		
		if ( unresolvedLinks.size() > 0 )
		{
			outBuff.append("The following terms failed to get results because either the term could not be found. "+
				"If you are sure your connection to the Internet is good, try to "+
				"reformulate the link request, or test your request in a browser.\n===");
			for ( String unresolvedLink : unresolvedLinks )
			{
				outBuff.append("-> " + unresolvedLink + "\n");
			}
			outBuff.append("=== End of unresolved links ===\n");
		}
		return outBuff.toString();
	}

	/**
	 * @return true if there are messages to output and false otherwise.
	 */
	public static boolean hasMessages() 
	{
		// you will want to change this if you extend error reporting.
		return unresolvedLinks.size() > 0;
	}
}
