package nisbet.andrew.test;

import static org.junit.Assert.*;

import nisbet.andrew.service.ClosestString;

import org.junit.Test;

public class ClosestStringTest {

	@Test
	public void testClosestString() {
		String[] a = {"twostring", "someotherthing",  "threestring", "onestring" };
		new ClosestString( a );
		String[] b = { "a", "a" };
		new ClosestString( b );
		String[] c = { "c" };
		new ClosestString( c );
		String[] d = { "a", "b", "c", "d" };
		new ClosestString( d );
		try{
			new ClosestString(null);
		} catch (Exception e)
		{
			assertTrue( e instanceof NullPointerException );
		}
	}

}
