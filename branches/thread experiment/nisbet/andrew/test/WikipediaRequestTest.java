package nisbet.andrew.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nisbet.andrew.service.ServiceRequest;
import nisbet.andrew.service.WikipediaServiceRequest;

import org.junit.Test;

public class WikipediaRequestTest {

	
	@Test
	public void testWikiXMLResultParser() {
		ServiceRequest service = new WikipediaServiceRequest("car","1");
		assertNotNull( service );
//		System.out.println( service.getLink() );
//		System.out.println( service.getImage() );
//		System.out.println( service.getDescription() );
	}

	@Test
	public void testHasError() {
		ServiceRequest service = new WikipediaServiceRequest("nfsfds","1");
		assertNotNull( service );
//		System.out.println( service.getLink() );
//		System.out.println( service.getImage() );
//		System.out.println( service.getDescription() );
		assertTrue( service.serviceSucceeded() == false );
	}
	
	@Test
	public void testImage() {
		ServiceRequest service = new WikipediaServiceRequest("car","1");
		assertNotNull( service );
		System.out.println( service.getLink() );
		System.out.println( service.getImage() );
		System.out.println( service.getDescription() );
		assertTrue( service.serviceSucceeded() );
		service.getImage();
	}

}
