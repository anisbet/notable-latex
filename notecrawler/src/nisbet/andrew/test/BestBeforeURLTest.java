package nisbet.andrew.test;

import static org.junit.Assert.*;

import nisbet.andrew.notecrawler.BestBeforeURL;

import org.junit.Test;

public class BestBeforeURLTest {

	@Test
	public void testIsFresh() {
		BestBeforeURL bbu = new BestBeforeURL("#");
		assertTrue( bbu.isFresh() );
		bbu.setMonthsFreshness( -1 );
		assertFalse( bbu.isFresh() );
		bbu.setMonthsFreshness( 6 );
		assertTrue( bbu.isFresh() );
	}

	@Test
	public void testGetLink() {
		BestBeforeURL bbu = new BestBeforeURL( "#" );
		bbu.setLink("http://www.google.ca/");
		assertTrue( bbu.isFresh() );
		assertTrue( bbu.getRawLink().compareTo("http://www.google.ca/") == 0);
	}
	
	@Test
	public void testToString() {
		BestBeforeURL bbu = new BestBeforeURL("http://www.google.ca/");
		System.out.println( "'" + bbu + "'" );
		assertTrue( bbu.getRawLink().compareTo("http://www.google.ca/") == 0);
		System.out.println( bbu );
	}
	
	@Test
	public void testBestBeforeURL() {
		
		BestBeforeURL bbu = new BestBeforeURL( "http://www.google.ca/" );
//		String date = bbu.toString();
//		String[] dates = date.split("\\$");
		BestBeforeURL bbu1 = new BestBeforeURL( "some_name" );
		System.out.println( "'" + bbu + "'" );
		System.out.println( "'" + bbu1 + "'" );
		assertTrue( bbu.getRawLink().compareTo("http://www.google.ca/") == 0);
		assertTrue( bbu.isFresh() );
		assertTrue( bbu.getLink().toString().compareTo(bbu1.getRawLink()) == 0 );
	}
	
	
	@Test
	public void testImageFunctions()
	{
		BestBeforeURL  bbu = new BestBeforeURL( "www.google.com" );
		bbu.setImage( "220px-Benz-velo.jpg" );
		assertTrue( bbu.imageExists() );
		bbu.setImage( "swim.jpg" ); // should not find image.
		assertTrue( bbu.imageExists() == false );
		assertTrue( bbu.isFresh() );
	}

}
