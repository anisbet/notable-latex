/**
 * 
 */
package nisbet.andrew.latex;

import java.util.Enumeration;

import nisbet.andrew.link.LineLinkParser;
import nisbet.andrew.link.Link;
import nisbet.andrew.link.LinkCrawler;
import nisbet.andrew.link.LinkDictionaryXML;
import nisbet.andrew.notecrawler.DictionaryXML;
import nisbet.andrew.notecrawler.Preprocessor;
import nisbet.andrew.notecrawler.NotableDictionary;
import nisbet.andrew.notecrawler.NoteCrawler;
import nisbet.andrew.service.WikipediaServiceRequest;

/**
 * This class uses two dictionaries and a clever {@link WikipediaServiceRequest} object to create meaningful
 * content and simplify and augment the student's note taking speed and understanding during studying.
 * @author anisbet
 *
 */
public class Crawler implements NoteCrawler 
{
	private static final char   LINK_DELIMITER = '+';
	private static final String defaultLinkDictionary = "linkdict.xml";
	private static final String defaultCharDictionary = "chardict.xml";
	
	private Preprocessor notes = null;
	private boolean isVerbatim = false;
	private int lineNumber     = 0;
	
	// There is a dictionary for the characters and their mappings
	// There is a dictionary of terms that need not be looked up actually this is a list.
	// but in this design I will use a Hashtable and if the value is empty it will not be linked.
	private NotableDictionary linkDictionary = null;
	private NotableDictionary charDictionary = null;
	private Document latexDocument;
	private boolean isNumberedCodeLines = false;
	
	/**
	 * @param openNoteBook
	 */
	public Crawler( Preprocessor openNoteBook )
	{
		notes          = openNoteBook;
		linkDictionary = new LinkDictionaryXML( defaultLinkDictionary );
		linkDictionary.readDictionary();
		charDictionary = new DictionaryXML( defaultCharDictionary );
		charDictionary.readDictionary();
	}

	@Override
	public void runAll()
	{
		
		// even though the dictionary was read it 
		this.latexDocument = new Document( 
				this.notes.getFileName(), 
				this.notes.getAuthor(), 
				this.notes.getTitle(),
				this.notes.getPackageIncludes() );
		// create the link crawler 
		LinkCrawler linkCrawler = new LinkCrawler( linkDictionary );
		this.latexDocument.writeHeader();
		String line = new String();
		while (( line = notes.readLine() ) != null)
		{
			line = clean( line );
			line = preprocess( notes, line );
			line = link( linkCrawler, line );
			// output results.
			this.latexDocument.writeLine( line );
		}
		// this must occur first because latex doc is going to copy the temp file over it.
		this.notes.close();
		// append end of document and lcose copy temp over original
		this.latexDocument.writeFooter();
		linkDictionary.writeToFile();
		// now compile the latex document
		this.latexDocument.compile();
	}
	
	/**
	 * Creates links in the final LaTeX document.
	 * @param line
	 * @return the link as a string ready to paste in the LaTeX note text.
	 */
	private String link( LinkCrawler linkCrawler, String line ) {
		// if we are in a verbatim statement don't do links.
		if ( this.isVerbatim == true )
		{
			return line;
		}
		// find the '+' symbol, it indicates the start of an item to link.
		String d = new String( );
		d += LINK_DELIMITER;       // clumsy but appends a character onto the end of the string.
		if ( ! line.contains( d ) ) return line; // short cut the search.
		// now process each of the links on a line.
		LineLinkParser lineParser = new LineLinkParser( line, LINK_DELIMITER );
		String searchWord;
		while ( lineParser.hasMoreLinks() )
		{
			searchWord = lineParser.nextLink();
			Link link = linkCrawler.getLink( searchWord );
			line = line.replace( LINK_DELIMITER + searchWord + LINK_DELIMITER, link.getLink() );
		}
		return line;
	}


	/**
	 * Parses Notable code sequences into LaTeX.
	 * @param line from the notes
	 * @return A String with the code words replaced with their LaTeX equivalent.
	 */
	private String preprocess( Preprocessor notes, String line )
	{
		StringBuffer lineBuff = new StringBuffer();
		
		// Verbatim is handled line by line so that other processes don't try and modify its contents. 
		// Specifically they don't try and replace '+' signs in code with links.
		if ( this.isVerbatim == true && ! line.startsWith( "code:" ))
		{
			line = line.replaceAll( "\t", "  " );
			if ( this.isNumberedCodeLines )
			{
				lineBuff.append( ++lineNumber + ") " + line );
			}
			else
			{
				lineBuff.append( line );
			}
		} 
		else if ( line.startsWith( "def:" ) )
		{
			System.out.println( "found 'definition'" );
			lineBuff.append( "\\textbf{Definition:}\\begin{quote}\\textit{" );
			lineBuff.append( line.substring( 4 ) );
			lineBuff.append( "}\\end{quote}\n" );
		}
		else if ( line.startsWith( "\t\t\t" ) )
		{
			System.out.println( "found 'subsubsection'" );
			lineBuff.append( "\\subsubsection{" + line.substring( 3 ) + "}\n" ); // trim off the first two chars.
		}
		else if ( line.startsWith( "\t\t" ) )
		{
			System.out.println( "found 'subsection'" );
			lineBuff.append( "\\subsection{" + line.substring( 2 ) + "}\n" ); // trim off the first two chars.
		}
		else if ( line.startsWith( "\t" ) )
		{
			System.out.println( "found 'section'" );
			lineBuff.append( "\\section{" + line.substring( 1 ) + "}\n" ); // trim off the first two chars.
		}
		else if ( line.startsWith( "===" ) )
		{
			System.out.println( "found 'chapter'" );
			lineBuff.append( "\\chapter{" + line.substring( 3 ) + "}\n" ); // trim off the first two chars.
		}
		else if ( line.startsWith( "fig:" ) )
		{
			// Figure requires another call to the line reader.
			System.out.println( "found 'figure'" );
			lineBuff.append( "\\begin{figure}[h]\n\\begin{center}\n\\includegraphics[width=\\textwidth]{" );
			lineBuff.append( line.substring( 4 ) ); // not fig:
			lineBuff.append( "}\n\\end{center}\n\\caption{" );
			String figureTitle = null;
			figureTitle = notes.readLine();
			lineBuff.append( figureTitle );
			lineBuff.append( "}\n\\end{figure}\n" );
		}
		else if ( line.startsWith( "#" ) )
		{
			// Figure requires another call to the line reader.
			System.out.println( "found 'numbered list'" );
			lineBuff.append( "\\begin{enumerate}\n\\item " + line.substring(1) + "\n" );
			String listItems = null;
			if ( notes.markSupported() == false )
			{
				System.err.println( "The stream is not supported and ordered lists may not appear properly." );
			}
			while ( true )
			{
				notes.mark(); // save the position in the stream
				listItems = notes.readLine();
				if ( listItems.startsWith( "#" ) == false)
				{
					notes.reset(); // replace the marker.
					break;
				}
				lineBuff.append( "\\item " + listItems.substring(1) + "\n" );
			}
			lineBuff.append( "\\end{enumerate}\n" );
		}
		else if ( line.startsWith( "-" ) )
		{
			// Figure requires another call to the line reader.
			System.out.println( "found 'unordered list'" );
			lineBuff.append( "\\begin{itemize}\n\\item " + line.substring(1) + "\n" );
			String listItems = null;
			if ( notes.markSupported() == false )
			{
				System.err.println( "The stream is not supported and ordered lists may not appear properly." );
			}
			while ( true )
			{
				notes.mark(); // save the position in the stream
				listItems = notes.readLine();
				if ( listItems.startsWith( "-" ) == false)
				{
					notes.reset(); // replace the marker.
					break;
				}
				lineBuff.append( "\\item " + listItems.substring(1) + "\n" );
			}
			lineBuff.append( "\\end{itemize}\n" );
		}
		else if ( line.startsWith( "code:" ) && this.isVerbatim == false )
		{
			// TODO add title for code segment optional
			if ( line.startsWith("code:#"))
			{
				this.isNumberedCodeLines = true;
			}
			System.out.println( "found 'code segment'" );
			lineBuff.append( "\\begin{verbatim}\n" );
			this.isVerbatim = true;
		}
		else if ( line.startsWith( "code:" ) && this.isVerbatim == true )
		{
			this.isVerbatim = false;
			this.isNumberedCodeLines = false;
			lineBuff.append( "\\end{verbatim}\n" );
			this.lineNumber = 0; // reset line numbering.
		}
		else if ( line.startsWith( "table:" ) )
		{
			System.out.println( "found 'table'" );
			String tableRow = null;
			Table latexTable = new Table( line ); // send this so we can capture a title if available.
			while ( (tableRow = notes.readLine()) != null )
			{
				// is this the end of the table?
				if ( tableRow.startsWith( "table:" ) )
				{
					break;
				}
				latexTable.setRow( tableRow );
			}
			lineBuff.append( latexTable.toString() );
		}
		else 
		{
			lineBuff.append( line );
		}

		return lineBuff.toString();
	}

	/**
	 * Cleans a line of any text conversions mentioned in the character dictionary. Note
	 * that conversions are not restricted to just characters either. You can replace 
	 * whole words like 'teh' with 'the'.
	 * @param line
	 * @return A line clean of offending non-LaTeX compilable text.
	 */
	private String clean( String line ) 
	{
		Enumeration<String> e = this.charDictionary.keys();
		//iterate through Hashtable keys Enumeration
		String searchValue = null;
		while( e.hasMoreElements() )
		{
			searchValue = e.nextElement();
			line = line.replaceAll( searchValue, (String)this.charDictionary.getValue( searchValue ) );
		}
		return line;
	}

	
}
