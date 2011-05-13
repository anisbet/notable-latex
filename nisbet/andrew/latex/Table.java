/**
 * 
 */
package nisbet.andrew.latex;

import java.util.Vector;

/**
 * Formats a LaTeX table and does other handy things like take a constructor that will optionally 
 * add a caption to your table, center the table and allow the user to use either tabs OR bars '|'
 * to deliniate cells.
 * @author anisbet
 *
 */
public class Table
{
	private final static String DELIMITER = "\\|";
	private Vector<TableColumn> columns;
	private int lineNumber;
	private StringBuffer justifification;
	private StringBuffer table;
	private String caption;
	private boolean hasCaption;
	
	/**
	 * Default constructor will not check for a caption.
	 */
	public Table()
	{
		init();
	}
	
	/**
	 * 
	 */
	private void init()
	{
		justifification = new StringBuffer();
		table = new StringBuffer();
		lineNumber = 0;
		columns = new Vector<TableColumn>();
		hasCaption = false;
	}

	/**
	 * Default constructor used by LateXCrawler. Checks if there is a valid caption 
	 * and if so adds it to the bottom of the table.
	 * @param line
	 */
	public Table( String line ) {
		init();
		String[] titles = line.split( ":" );
		if ( titles.length > 1 )
		{
			hasCaption = true;
			caption = titles[1];
		}
	}

	/**
	 * @param tableRow
	 */
	public void setRow(String tableRow) 
	{
		String thisRow = tableRow;
		
		if ( tableRow.startsWith( "-" ) )
		{
			table.append( "\\hline\n" );
		}
		else
		{
			lineNumber++;
			thisRow = thisRow.replaceAll( "\t+", DELIMITER );   // this allows users to format with tabs for cell delimiters too.
			//check justification of this line. start by setting the number of columns you will count the number of '|'
			if ( checkRow( thisRow ) == false )
			{
				System.err.println( "Table Warning: line " + lineNumber + " mismatch number of columns." );;
			}
			thisRow = thisRow.replaceAll( DELIMITER, "&" );
			thisRow = thisRow + "\\\\";
			table.append( thisRow + "\n" );
		}
	}
	
	/**
	 * @param tableRow
	 * @return true if the row checks out ok and false otherwise. Ok currently means that the 
	 * number of columns match all rows. This function also resets the justification if a row 
	 * is too wide.
	 */
	private boolean checkRow( String tableRow ) {
		// count the number of columns and their lengths
		String[] cols = tableRow.split( DELIMITER );
		
		if ( lineNumber <= 2 ) // do this for the first and second row 1 incase there is just one row and 2 to set 
		{                      // the tables most likely data sizes rather than just relying on the table head.
			adjustJustification( cols );
		}
		else
		{
			if ( cols.length != columns.size() )
			{
				adjustJustification( cols );
				return false;
			}
		}
		
		return true;
	}

	/**
	 * @param cols
	 */
	private void adjustJustification( String[] cols )
	{
		columns.removeAllElements();
		for ( int i = 0; i < cols.length; i++ )
		{
			columns.add( new TableColumn( cols[i] ) );
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		
		justifification.append( "\\begin{table}\n" );
		justifification.append( "\\begin{center}\n" );
		justifification.append( "\\begin{tabular}{" );
		setJustification();
		justifification.append( "|}\n" ); // close the justification line.
		justifification.append( table.toString() );
		justifification.append( "\\end{tabular}\n" );
		if ( hasCaption )
		{
			justifification.append( "\\caption{" + caption + "}\n" );
		}
		justifification.append( "\\end{center}\n" );
		justifification.append( "\\end{table}\n" );
		
		return justifification.toString();
	}

	/**
	 * Appends the appropriate type and number of justification arguments.
	 */
	private void setJustification() 
	{
		for ( TableColumn tableColumn : this.columns )
		{
			this.justifification.append( tableColumn.getJustification() );
		}
	}
}
