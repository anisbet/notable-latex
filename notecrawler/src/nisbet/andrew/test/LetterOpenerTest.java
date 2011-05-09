package nisbet.andrew.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import nisbet.andrew.notecrawler.LetterOpener;

import org.junit.Test;

public class LetterOpenerTest {

	@Test
	public void testLetterOpener() {
		LetterOpener letterOpener;
		try 
		{
			letterOpener = new LetterOpener( "report.tex" );
			assertNotNull( letterOpener );
			letterOpener.close();
		} catch (Exception e) {
			assertTrue( e instanceof FileNotFoundException);
		}
		
		try
		{
			letterOpener = new LetterOpener( "invalid_file" );
		} catch (Exception e) {
			assertTrue( e instanceof FileNotFoundException );
		}
		
		try
		{
			letterOpener = new LetterOpener( "/home" );
		} catch (Exception e) {
			assertTrue( e instanceof FileNotFoundException );
		}
	}
	
	@Test
	public void AuthorTitleTest()
	{
		try {
			BufferedWriter out = new BufferedWriter( new FileWriter( new File("test.txt")));
			out.write( "AUTHOR:Andrew Nisbet\n" );
			out.write( "TITLE:Some Interesting Title" );
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LetterOpener letterOpener = null;
		try {
			letterOpener = new LetterOpener( "test.txt" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertNotNull( letterOpener );
		assertTrue( letterOpener.getAuthor().compareTo("Andrew Nisbet") == 0);
		assertTrue( letterOpener.getTitle().compareTo("Some Interesting Title") == 0);
		letterOpener.close();
		
	}

	

}
