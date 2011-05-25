/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.UnknownHostException;

import nisbet.andrew.service.ImageFetcher;

import org.junit.Test;

/**
 * @author anisbet
 *
 */
public class ImageFetcherTest {

	/**
	 * Test method for {@link nisbet.andrew.service.ImageFetcher#ImageFetcher(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testImageFetcher() {
		ImageFetcher imageFetcher = null;
		try {
			imageFetcher = new ImageFetcher( "http://en.wikipedia.org/wiki/Apiaceae", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
		} catch (UnknownHostException e) {
			return;
		}
		assertNotNull( imageFetcher );
		System.out.println( imageFetcher.getImageURL() );
		assertTrue( imageFetcher.isBigEnough() );
		
		try {
			imageFetcher = new ImageFetcher( "http://www.p.fds", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
		} catch (UnknownHostException e) {
			System.err.println( "The host: '" + "http://www.p.fds" + "' is unknown." );
		}
		assertNotNull( imageFetcher );
		System.out.println( imageFetcher.getImageURL() );
		assertTrue( imageFetcher.isBigEnough() == false );
	}
	
	@Test
	public void testDownloadImage()
	{
		ImageFetcher imageFetcher = null;
		try {
			imageFetcher = new ImageFetcher( "http://en.wikipedia.org/wiki/Apiaceae", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
		} catch (UnknownHostException e) {
			return;
		}
		assertNotNull( imageFetcher );
		System.out.println( imageFetcher.getImageURL() );
		assertTrue( imageFetcher.isBigEnough() );
		imageFetcher.download();
		File file = new File( "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
		assertTrue( file.exists() );
	}

}
