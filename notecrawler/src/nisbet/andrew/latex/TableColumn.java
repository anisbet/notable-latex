/**
 * 
 */
package nisbet.andrew.latex;

/**
 * Creates justification definitions for table columns.
 * @author anisbet
 *
 */
public class TableColumn 
{
	private String justification;
	// now set a flag for long lines (> 60 chars).
	public final static int LONG_LINE = 20;
	
	public TableColumn( String columnString )
	{
		String test = columnString.trim();
		if ( test.length() > TableColumn.LONG_LINE )
		{
			justification = "|l";
		}
		else if ( test.matches( "^\\d+\\.?\\d+$" ) )
		{
			justification = "|r";
		}
		else
		{
			justification = "|c";
		}
	}

	/**
	 * @return justification as a char for latex justification.
	 */
	public Object getJustification()
	{
		return justification;
	}
}
