package nisbet.andrew.test;

import static org.junit.Assert.*;

import nisbet.andrew.link.LineLinkParser;

import org.junit.Test;

public class LineLinkParserTest {

	@Test
	public void testLineLinkParser() {
		LineLinkParser parser = new LineLinkParser("This +link+ here", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("link") == 0 );
		}
		
		parser = new LineLinkParser("+link+", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("link") == 0 );
		}
		
		parser = new LineLinkParser("++", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("") == 0 );
		}
		
		parser = new LineLinkParser("+", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("") == 0 );
		}
		
		parser = new LineLinkParser("+link", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("link") == 0 );
		}
		
		parser = new LineLinkParser("link+", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("") == 0 );
		}
		
		parser = new LineLinkParser("", '+');
		assertFalse( parser.hasMoreLinks() );
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("") == 0 );
		}
		
		parser = new LineLinkParser("this is a +link+ and $this + stink + not$.", '+');
		while ( parser.hasMoreLinks() )
		{
			assertTrue( parser.nextLink().compareTo("link") == 0 );
		}
		
		parser = new LineLinkParser("+link1+ and $this + linkx + not$ +link2+ rest.", '+');
		while ( parser.hasMoreLinks() )
		{
			System.out.println( parser.nextLink() );
		}
	}

}
