package nisbet.andrew.test;

import java.util.Vector;

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

	}
	
	@Test
	public void ClosestStringConstructorTest(){
		ClosestString cs = new ClosestString("threestring");
		cs.addString("twostring");
		cs.addString("someotherthing");
		cs.addString("onestring");
		cs.start();
	}
	
	@Test
	public void ClosestStringVectorConstructor()
	{
		Vector<String> a = new Vector<String>();
		a.add( "twostring" );
		a.add( "someotherthing" );
		a.add( "threestring" );
		//a.add( "onestring" );
		ClosestString cStr = new ClosestString("onestring", a);
		System.out.println( ">>> The closest match to 'onestring' is '" + cStr.getClosestMatch() + "'");
		
		a = new Vector<String>();
//		a.add( "twostring" );
//		a.add( "someotherthing" );
//		a.add( "threestring" );
		//a.add( "onestring" );
		cStr = new ClosestString("onestring", a);
		System.out.println( ">>> The closest match to 'onestring' is '" + cStr.getClosestMatch() + "'");
	}

}
