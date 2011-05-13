package nisbet.andrew.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import nisbet.andrew.notecrawler.Preprocessor;

import org.junit.Test;

public class LetterOpenerTest {

	@Test
	public void testLetterOpener() {
		Preprocessor preprocessor;
		try 
		{
			preprocessor = new Preprocessor( "report.tex" );
			assertNotNull( preprocessor );
			preprocessor.close();
		} catch (Exception e) {
			assertTrue( e instanceof FileNotFoundException);
		}
		
		try
		{
			preprocessor = new Preprocessor( "invalid_file" );
		} catch (Exception e) {
			assertTrue( e instanceof FileNotFoundException );
		}
		
		try
		{
			preprocessor = new Preprocessor( "/home" );
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
		Preprocessor preprocessor = null;
		try {
			preprocessor = new Preprocessor( "test.txt" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertNotNull( preprocessor );
		assertTrue( preprocessor.getAuthor().compareTo("Andrew Nisbet") == 0);
		assertTrue( preprocessor.getTitle().compareTo("Some Interesting Title") == 0);
		preprocessor.close();
		
	}

	

}
