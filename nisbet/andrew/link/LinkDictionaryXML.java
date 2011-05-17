/**
 * 
 */
package nisbet.andrew.link;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nisbet.andrew.latex.LaTeXLink;
import nisbet.andrew.notecrawler.BestBeforeURL;
import nisbet.andrew.notecrawler.NotableDictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author anisbet
 *
 */
public class LinkDictionaryXML implements NotableDictionary
{
	private Document dom = null;
	protected String dictionaryName = null;
	protected Hashtable<String, BestBeforeURL> dictionary = null;
	/**
	 * @param dictionary
	 */
	public LinkDictionaryXML( String dictionary ) 
	{
		this.dictionaryName = dictionary;
		this.dictionary     = new Hashtable<String, BestBeforeURL>();
		init();
	}
	
	/**
	 * 
	 */
	private void init()
	{
		File dictFile = new File( this.dictionaryName );
		if ( dictFile.exists() == false )
		{
			initDomDoc();
		}
	}
	
	/**
	 * Sets the DOM object.
	 */
	private void initDomDoc()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try 
		{
			docBuilder = dbf.newDocumentBuilder();
			dom = docBuilder.newDocument();
			Node dictionaryElement = dom.createElement( "dictionary" );
			dom.appendChild( dictionaryElement );
		} 
		catch ( Exception e ) 
		{
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.link.LinkDictionary#readDictionary()
	 */
	@Override
	public synchronized void readDictionary()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			dom = docBuilder.parse( this.dictionaryName );
		} 
		catch (Exception e)
		{
			return;
		}
		
		Element rootElement = dom.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName( "entry" );
		if( nodeList != null && nodeList.getLength() > 0 ) 
		{
			String key   = null;
			BestBeforeURL value = null;
			for( int i = 0 ; i < nodeList.getLength(); i++ )  
			{
				// have the entry element
				Element element = ( Element )nodeList.item( i );
				key   = getTextValue( element, "key" );
				value = getBestBeforeURL( element, key );
				addSymbol( key, value );
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.link.LinkDictionary#writeToFile()
	 */
	@Override
	public boolean writeToFile()
	{
		this.serialize();
		Transformer transformer = null;
		try 
		{
			transformer = TransformerFactory.newInstance().newTransformer();
		} 
		catch (Exception e) 
		{
			return false;
		}
		
		transformer.setOutputProperty( OutputKeys.INDENT, "yes" );

		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult( new File( this.dictionaryName ) );
		DOMSource source = new DOMSource( dom );
		try 
		{
			transformer.transform( source, result );
		} 
		catch (Exception e) 
		{
			return false;
		}

		return true;
	}
	
	/**
	 * @param entry
	 * @param anchorText
	 * @return BestBeforeURL populated with the data from the XML file.
	 */
	private BestBeforeURL getBestBeforeURL( Element entry, String anchorText ) 
	{
		// read the values from the entry element.
		String serializedDate = getTextValue( entry, "date" );
		String target         = getTextValue( entry, "url" );
		Link link = new LaTeXLink( target, anchorText );
		BestBeforeURL bbURL = new BestBeforeURL( serializedDate, link );
		bbURL.setImage( getTextValue( entry, "image" ) );
		bbURL.setImageCaption( getTextValue( entry, "caption" ));
		return bbURL;
	}


	/**
	 * Override in subsequent classes to output the dataTypes stored there.
	 */
	private void serialize()
	{
		initDomDoc(); // re-initialize the dom document.
		Enumeration<String> e = dictionary.keys();
		//iterate through Hashtable keys Enumeration
		String key = new String();
		BestBeforeURL value = null;
		while( e.hasMoreElements() )
		{
			Node entry = dom.createElement( "entry" );
			key = e.nextElement();
			Node keyElement = dom.createElement( "key" );
			keyElement.setTextContent( key );
			entry.appendChild( keyElement );
			// now the value
			value = this.dictionary.get( key );
			Node valueElement = getValueNode( value );
			entry.appendChild( valueElement );
			// now add the entry to the dictionary element
			dom.getDocumentElement().appendChild( entry );
		}
	}
	
	
	/**
	 * @param value
	 * @return
	 */
	private Node getValueNode( Object value )
	{
		Node valueElement = dom.createElement( "value" );
		// now add the BestBeforeURL as XML.
		Node dateElement = dom.createElement( "date" );
		dateElement.setTextContent( ((BestBeforeURL)value).getBestBeforeDate() );
		valueElement.appendChild( dateElement );
		
		Node urlElement = dom.createElement( "url" );
		urlElement.setTextContent( ((BestBeforeURL)value).getURL() );
		valueElement.appendChild( urlElement );
		
		Node imageElement = dom.createElement( "image" );
		imageElement.setTextContent( ((BestBeforeURL)value).getImage() );
		valueElement.appendChild( imageElement );
		
		Node captionElement = dom.createElement( "caption" );
		captionElement.setTextContent( ((BestBeforeURL)value).getImageCaption() );
		valueElement.appendChild( captionElement );
		
		return valueElement;
	}
	
	/**
	 * @param ele
	 * @param tagName
	 * @return String with the text of the tag with tagName that is a child of ele.
	 */
	private String getTextValue( Element ele, String tagName ) 
	{
		String text = "";
		NodeList nodeList = ele.getElementsByTagName( tagName );
		if( nodeList != null && nodeList.getLength() > 0 ) 
		{
			Element element = ( Element )nodeList.item( 0 );
			// guard for empty elements which can happen if there is no image.
			if ( element.getFirstChild() == null )
			{
				return "";
			}
			text = element.getFirstChild().getNodeValue();
		}

		return text;
	}

	/**
	 * Adds a key and value to the dictionary.
	 * @param key
	 * @param bestBeforeURL
	 */
	public void addSymbol(String key, Object bestBeforeURL) 
	{
		dictionary.put(key, (BestBeforeURL)bestBeforeURL);
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
	public Object getValue(String searchValue) 
	{
		return dictionary.get( searchValue );
	}

	/**
	 * @param word
	 * @return true if the dictionary contains a link and false otherwise.
	 */
	public boolean containsEntry(String word) {
		System.out.print( "searching '" );
		System.out.print( word );
		System.out.println( "," );
		return dictionary.containsKey( word );
	}
}