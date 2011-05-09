/**
 * 
 */
package nisbet.andrew.notecrawler;

import java.io.File;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This dictionary is meant to take over from the original dictionary in reading and writing 
 * the Dictionary in XML.
 * writing the entries in XML.
 * @author anisbet
 *
 */
public class DictionaryXML extends Dictionary
{
	protected Document dom = null;
	
	/**
	 * @param dictionary named file in file system.
	 */
	public DictionaryXML( String dictionary ) 
	{
		super( dictionary );
		init();
	}

	
	protected void init()
	{
		File dictFile = new File( this.dictionaryName );
		if ( dictFile.exists() == false )
		{
			initDomDoc();
		}
	}
	
	protected void initDomDoc()
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
	 * @see nisbet.andrew.notecrawler.Dictionary#readDictionary()
	 */
	@Override
	public void readDictionary()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			dom = docBuilder.parse( this.dictionaryName );
		} 
		catch (Exception e) // fires for DocumentBuilderFactoryException and FileNotFoundException.
		{
			return;
		}
		
		Element rootElement = dom.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName( "entry" );
		if( nodeList != null && nodeList.getLength() > 0 ) 
		{
			String key   = null;
			String value = null;
			for( int i = 0 ; i < nodeList.getLength(); i++ )  
			{
				//get the Item element
				Element element = ( Element )nodeList.item( i );
				key   = getTextValue( element, "key" );
				value = getTextValue( element, "value" );
				this.addSymbol( key, value );
			}
		}
		
	}
	



	/**
	 * Override in subsequent classes to output the dataTypes stored there.
	 */
	protected void serialize()
	{
		initDomDoc(); // re-initialize the dom document.
		Enumeration<String> e = dictionary.keys();
		//iterate through Hashtable keys Enumeration
		String key = new String();
		String value = new String();
		while( e.hasMoreElements() )
		{
			Node entry = dom.createElement( "entry" );
			key = e.nextElement();
			Node keyElement = dom.createElement( "key" );
			keyElement.setTextContent( key );
			entry.appendChild( keyElement );
			// now the value
			value = this.dictionary.get( key );
			Node valueElement = dom.createElement( "value" );
			valueElement.setTextContent( value );
			entry.appendChild( valueElement );
			// now add the entry to the dictionary element
			dom.getDocumentElement().appendChild( entry );
		}
	}
	
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
	 * @param ele
	 * @param tagName
	 * @return
	 */
	protected String getTextValue( Element ele, String tagName ) 
	{
		String text = null;
		NodeList nodeList = ele.getElementsByTagName( tagName );
		if( nodeList != null && nodeList.getLength() > 0 ) 
		{
			Element element = ( Element )nodeList.item( 0 );
			text = element.getFirstChild().getNodeValue();
		}

		return text;
	}

}
