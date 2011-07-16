package nisbet.andrew.latex;

import static org.junit.Assert.*;

import org.junit.Test;

public class CleanerTest {

	@Test
	public void testClean() {
		System.out.println(">>>" + Cleaner.removePeriods("one.two.jpg"));
	}

}
