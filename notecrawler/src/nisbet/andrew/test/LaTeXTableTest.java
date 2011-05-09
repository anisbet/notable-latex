package nisbet.andrew.test;

import nisbet.andrew.latex.Table;

import org.junit.Test;

public class LaTeXTableTest {

	@Test
	public void testLaTeXTable() 
	{
		Table lt = new Table();
		lt.setRow( "-" );
		lt.setRow( "Col 1 | Col 2" );
		lt.setRow( "-" );
		lt.setRow( "row 1 | row 1" );
		lt.setRow( "row 2 | row 2" );
		lt.setRow( "row 3 | row 3" );
		lt.setRow( "-" );
		System.out.println( lt.toString() );
		
		lt = new Table();
		lt.setRow( "-" );
		lt.setRow( "Col 1 | Col 2" );
		lt.setRow( "-" );
		lt.setRow( "row 1 | 1234 " );
		lt.setRow( "-" );
		System.out.println( lt.toString() );
		
		lt = new Table();
		lt.setRow( "-" );
		lt.setRow( "Col 1 | Col 2 | Col 1 | Col 2 | Col 1 | Col 2 " );
		lt.setRow( "-" );
		lt.setRow( "row 1 | 1234 | very long line needs to be left justified | 12334 | andrew | Jerry" );
		lt.setRow( "-" );
		System.out.println( lt.toString() );
	}
	
	@Test
	public void testStringConstructor()
	{
		Table lt = new Table( "table:" );
		lt.setRow( "-" );
		lt.setRow( "Col 1 | Col 2" );
		lt.setRow( "-" );
		lt.setRow( "row 1 | row 1" );
		lt.setRow( "row 2 | row 2" );
		lt.setRow( "row 3 | row 3" );
		lt.setRow( "-" );
		System.out.println( lt.toString() );
		
		lt = new Table( "table:With a Caption." );
		lt.setRow( "-" );
		lt.setRow( "Col 1 | Col 2" );
		lt.setRow( "-" );
		lt.setRow( "row 1		row 1" );
		lt.setRow( "row 2	row 2" );
		lt.setRow( "row 3 	 row 3" );
		lt.setRow( "-" );
		System.out.println( lt.toString() );
		
	}

}
