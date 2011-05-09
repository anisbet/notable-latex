/**
 * 
 */
package nisbet.andrew.service;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nisbet.andrew.latex.LaTeXLink;
import nisbet.andrew.link.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This Service takes a search term and queries Wikipedia and parses the response.
 * @author anisbet
 *
 */
public class WikipediaServiceRequest implements ServiceRequest 
{
	private final String wikiUrl = "http://en.wikipedia.org/w/api.php";

	private String queryPhrase;
	private boolean hasError;
	private String imageUrl;
	private boolean isValidImageType;
	private String description;
	private String linkTarget;

	/**
	 * @param query The term to search for.
	 * @param limit The limit of results you would like. Some queries like 'flower' will return hundreds potentially.
	 * Wikipedia currently limits robots to 50 responses.
	 */
	public WikipediaServiceRequest( String query, String limit ) 
	{
		isValidImageType = false;
		queryPhrase = query;
		String data = new String();
		URL url = null;
		try 
		{
			
			data  = 	  URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("opensearch", "UTF-8");
			data += "&" + URLEncoder.encode("format", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8");
		    data += "&" + URLEncoder.encode("limit", "UTF-8") + "=" + URLEncoder.encode(limit, "UTF-8");
		    data += "&" + URLEncoder.encode("namespace", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
		    data += "&" + URLEncoder.encode("search", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8");
		    
		    // Send data
		    // requested in the api.
		    System.setProperty("http.agent", "MyCuteBot/0.1");
		    url = new URL( wikiUrl );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		InputStream xmlStream = createConnection( url, data );
		parseDocument( xmlStream );
		setImage();
	}
	
	/**
	 * Creates the input stream for reading the xml.
	 * @param url
	 * @param data
	 * @return
	 */
	private InputStream createConnection( URL url, String data )
	{
		InputStream xmlContent = null;
		try
		{
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput( true );
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write( data );
		    wr.flush();
		    // Get the response
		    xmlContent = conn.getInputStream();
		    wr.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println( "An error occured while fetching the URL." + url );
			this.hasError = true;
		}
		
		return xmlContent;
	}
	
	/**
	 * @param xmlContent
	 */
	private void parseDocument( InputStream xmlContent )
	{		
		this.hasError = false;
		Document dom = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			dom = docBuilder.parse( xmlContent );
		} 
		catch (Exception e)
		{
			System.err.println( e );
			hasError = true;
			return;
		}
		
		Element rootElement = dom.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName( "Item" );
		if( nodeList != null && nodeList.getLength() > 0 ) 
		{
			///////////////////// warning what do you do with more than one 
			for( int i = 0 ; i < nodeList.getLength(); i++ )  
			{
				//get the Item element
				Element element = ( Element )nodeList.item( i );
				this.linkTarget = getTextValue( element, "Url" );
				this.imageUrl = getAttributeValue( element, "Image", "source");
				this.description = getTextValue( element, "Description" );
			}
		}
		else
		{
			this.hasError = true; // the search didn't find any required elements.
		}
	}
	
	
	
	/**
	 * @param ele
	 * @param tagName
	 * @param attribute
	 * @return
	 */
	private String getAttributeValue( Element ele, String tagName, String attribute )
	{
		String text = null;
		NodeList nodeList = ele.getElementsByTagName( tagName );
		if( nodeList != null && nodeList.getLength() > 0 ) 
		{
			Element element = ( Element )nodeList.item( 0 );
			text = element.getAttribute( attribute );
		}

		return text;
	}

	/**
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue( Element ele, String tagName ) 
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

	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.ServiceRequest#hasError()
	 */
	@Override
	public boolean serviceSucceeded() 
	{
		return hasError == false;
	}

	/* (non-Javadoc)
	 * @see nisbet.andrew.notecrawler.ServiceRequest#getLink()
	 */
	@Override
	public Link getLink() 
	{
		if ( this.linkTarget == null )
		{
			// if there was an error getting the information then just return the text with no link.
			return new Link( this.queryPhrase );
		}
		//return "\\href{" + this.linkTarget + "}{" + this.queryPhrase + "}";
		return new LaTeXLink( linkTarget, queryPhrase );
	}

	
	public void setImage() 
	{
		if ( this.imageUrl == null ) 
		{
			return;
		}
		// get the name of the image
		// request the page and search it for the image src attribute.
		// use that as your image url.
		String myImageName = getImageQueryName( this.imageUrl );
		ImageFetcher imageFetcher = new ImageFetcher( this.linkTarget, myImageName );
		this.imageUrl = imageFetcher.getImageURL();
		setValidImageType( this.imageUrl );
	}
	
	@Override
	public String getImage() 
	{
		if ( this.imageUrl == null || this.isValidImageType == false ) 
		{
			return "";
		}

		return this.imageUrl;
	}
	

	/**
	 * @param image
	 */
	private void setValidImageType( String image )
	{
		if ( image.endsWith( ".jpg" ) || image.endsWith( ".jpg" ) )
		{
			this.isValidImageType = true;
		}
		else
		{
			this.isValidImageType = false;
		}
	}

	/**
	 * Given a URL it will strip off the file name and even remove the query if any.
	 * @param url
	 * @return
	 */
	private String getImageQueryName( String url ) 
	{
		String[] dirs = url.split("/");
		if ( dirs.length > 0 )
		{
			String name =  dirs[ dirs.length -2 ];
			return name;
		}
		return null;
	}

	@Override
	public String getDescription() 
	{
		if ( this.description == null ) 
		{
			return "";
		}
		return this.description;
	}

	@Override
	public boolean isImageAvailable() 
	{
		return this.isValidImageType;
	}

}
