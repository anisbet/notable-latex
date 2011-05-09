package nisbet.andrew.test;

import nisbet.andrew.latex.Document;

import org.junit.Test;

public class LaTeXDocumentTest {

	@Test
	public void testLaTeXDocumentBoilerPlate() {
		Document latexDocument = new Document( "test_report.tex", "Andrew Nisbet", "Some Title" );
		latexDocument.writeHeader();
		latexDocument.writeFooter();
	}

}
