/**
 * 
 */
package nisbet.andrew.test;

import static org.junit.Assert.assertTrue;
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
//	@Test
//	public void testImageFetcher() {
//		ImageFetcher imageFetcher = null;
//		try {
//			imageFetcher = new ImageFetcher( "http://en.wikipedia.org/wiki/Apiaceae", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
//		} catch (UnknownHostException e) {
//			return;
//		}
//		assertNotNull( imageFetcher );
//		System.out.println( imageFetcher.getImageURL() );
//		assertTrue( imageFetcher.isBigEnough() );
//		
//		try {
//			imageFetcher = new ImageFetcher( "http://www.p.fds", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
//		} catch (UnknownHostException e) {
//			System.err.println( "The host: '" + "http://www.p.fds" + "' is unknown." );
//		}
//		assertNotNull( imageFetcher );
//		System.out.println( imageFetcher.getImageURL() );
//		assertTrue( imageFetcher.isBigEnough() == false );
//	}
	
//	@Test
//	public void testDownloadImage()
//	{
//		ImageFetcher imageFetcher = null;
//		try {
//			imageFetcher = new ImageFetcher( "http://en.wikipedia.org/wiki/Apiaceae", "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
//		} catch (UnknownHostException e) {
//			return;
//		}
//		assertNotNull( imageFetcher );
//		System.out.println( imageFetcher.getImageURL() );
//		assertTrue( imageFetcher.isBigEnough() );
//		imageFetcher.download();
//		File file = new File( "Umbelliferae-apium-daucus-foeniculum-eryngium-petroselinum.jpg" );
//		assertTrue( file.exists() );
//	}

	@Test
	public void testSize()
	{
		String size = "100px";
		//System.out.println( ImageFetcher.stripScale(size) );
		assertTrue( ImageFetcher.stripScale(size).equalsIgnoreCase("100"));
		assertTrue( ImageFetcher.stripScale("word").equalsIgnoreCase(""));
		assertTrue( ImageFetcher.stripScale("1px").equalsIgnoreCase("1"));
		assertTrue( ImageFetcher.stripScale("1000000cm").equalsIgnoreCase("1000000"));
		assertTrue( ImageFetcher.stripScale("1").equalsIgnoreCase("1"));
		assertTrue( ImageFetcher.stripScale("1 px").equalsIgnoreCase("1"));
		assertTrue( ImageFetcher.stripScale("").equalsIgnoreCase(""));
		assertTrue( ImageFetcher.stripScale(null).equalsIgnoreCase(""));
	}
	
	@Test
	public void testOptimalImage()
	{
		ImageFetcher fetcher = null;
		fetcher = new ImageFetcher("http://en.wikipedia.org/wiki/Apiaceae");
		assertTrue(fetcher.download());
		assertTrue( ImageFetcher.testImageExists( fetcher.getImageName() ) );
		System.out.println( fetcher.getImageName());
	}

}
