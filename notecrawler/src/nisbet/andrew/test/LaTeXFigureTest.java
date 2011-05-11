/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.*;

import java.io.File;

import nisbet.andrew.latex.Figure;

import org.junit.Test;

/**
 * @author anisbet
 *
 */
public class LaTeXFigureTest {

	/**
	 * Test method for {@link nisbet.andrew.latex.Figure}.
	 */
	@Test
	public void testLaTeXFigureString() 
	{
		Figure lFigure = new Figure( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg" );
		assertNotNull(lFigure);
		File testFile = new File( "swimming_girl.jpg" );
		assertTrue(testFile.exists());
	}
	
	@Test
	public void testLaTeXFigure() {
		Figure lFigure = new Figure( );
		assertNotNull(lFigure);
		assertTrue( lFigure.toString() == "" );
	}
	
	@Test
	public void testGetImageDownload() {
		Figure lFigure = new Figure( );
		assertNotNull(lFigure);
		lFigure.getImageDownload( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg" );
		assertNotNull(lFigure);
		File testFile = new File( "swimming_girl.jpg" );
		assertTrue(testFile.exists());
	}

	/**
	 * Test method for {@link nisbet.andrew.latex.Figure#Figure(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLaTeXFigureStringString() {
		Figure lFigure = new Figure( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg", "Swimming Fun" );
		assertNotNull(lFigure);
		File testFile = new File( "swimming_girl.jpg" );
		assertTrue(testFile.exists());
		System.out.println(lFigure);
	}

	/**
	 * Test method for {@link nisbet.andrew.latex.Figure#isSuccessful()}.
	 */
	@Test
	public void testGetIsSuccessful() {
		Figure lFigure = new Figure( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg" );
		assertNotNull(lFigure);
		File testFile = new File( "swimming_girl.jpg" );
		assertTrue(testFile.exists());
		assertTrue(lFigure.isSuccessful());
	}

	/**
	 * Test method for {@link nisbet.andrew.latex.Figure#toString()}.
	 */
	@Test
	public void testToString() {
		Figure lFigure = new Figure( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg", "Swimming Fun" );
		assertNotNull(lFigure);
		File testFile = new File( "swimming_girl.jpg" );
		assertTrue(testFile.exists());
		System.out.println(lFigure);
	}

	/**
	 * Test method for {@link nisbet.andrew.latex.Figure#setCaption(java.lang.String)}.
	 */
	@Test
	public void testSetCaption() {
		Figure lFigure = new Figure( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg" );
		assertNotNull(lFigure);
		File testFile = new File( "swimming_girl.jpg" );
		assertTrue(testFile.exists());
		lFigure.setCaption("Swimming is Fun");
		System.out.println(lFigure);
	}

}
