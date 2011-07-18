package nisbet.andrew.test;

import static org.junit.Assert.*;

import nisbet.andrew.latex.Cleaner;

import org.junit.Test;

public class CleanerTest {

	@Test
	public void testClean() {
		System.out.println(">>>" + Cleaner.cleanSpecialCharacters("1.2.3.jpg") + "<<<" );
		System.out.println(">>>" + Cleaner.cleanSpecialCharacters("300px-0453_-_Roma2C_Museo_d._civiltC3A0_romana_-_Sarcofago_Mattei_Foto_Giovanni_Dall27Orto2C_12-Apr-2008.jpg") + "<<<" );
	}

}
