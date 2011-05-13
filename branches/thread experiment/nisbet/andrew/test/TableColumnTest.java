/**
 * 
 */
package nisbet.andrew.test;

import nisbet.andrew.latex.TableColumn;

import org.junit.Test;

/**
 * @author anisbet
 *
 */
public class TableColumnTest {

	/**
	 * Test method for {@link nisbet.andrew.latex.TableColumn#getJustification()}.
	 */
	@Test
	public void testGetJustification() {
		TableColumn tc = new TableColumn("one");
		assert( tc.getJustification() == "|c" );
		tc = new TableColumn( "123" );
		assert( tc.getJustification() == "|r" );
		tc = new TableColumn( "123.45" );
		assert( tc.getJustification() == "|r" );
		tc = new TableColumn( "123." );
		assert( tc.getJustification() == "|r" );
		tc = new TableColumn( "123.45678901234567890" );
		assert( tc.getJustification() == "|r" );
		tc = new TableColumn( "this is a long line that should justify left." );
		assert( tc.getJustification() == "|l" );
		tc = new TableColumn( " 1234" );
		assert( tc.getJustification() == "|r" );
	}

}
