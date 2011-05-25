/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nisbet.andrew.latex.Figure;

import org.junit.Test;

/**
 * @author anisbet
 *
 */
public class LaTeXFigureTest {

	
	@Test
	public void testLaTeXFigure() {
		Figure lFigure = new Figure( );
		assertNotNull(lFigure);
		assertTrue( lFigure.toString() == "" );
	}
	
//	@Test
//	public void testGetImageDownload() {
//		Figure lFigure = new Figure( );
//		assertNotNull(lFigure);
//		lFigure.getImageDownload( "http://www.drclay.com/wp-content/uploads/2010/11/swimming_girl.jpg" );
//		assertNotNull(lFigure);
//		File testFile = new File( "swimming_girl.jpg" );
//		assertTrue(testFile.exists());
//	}

}
