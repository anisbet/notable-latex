/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.*;

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
		ImageFetcher imageFetcher = new ImageFetcher( "http://en.wikipedia.org/wiki/Apiaceae", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
		assertNotNull( imageFetcher );
		System.out.println( imageFetcher.getImageURL() );
	}

}
