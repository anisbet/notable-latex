package nisbet.andrew.latex;

import nisbet.andrew.link.Link;

/**
 * This formats a link into valid LaTeX href is a url was successfully retreived from a service, 
 * but will also output just plain text without linking decorations if the  object
 * @author anisbet
 *
 */
public class LaTeXLink extends Link 
{
	private String target;
	private String formatedLatexImage;
	
	/**
	 * @param target
	 * @param name
	 */
	public LaTeXLink( String target, String name ) 
	{
		super(name);
		this.target = target;
		this.formatedLatexImage = new String();
	}

	/* (non-Javadoc)
	 * @see nisbet.andrew.link.Link#getLink()
	 */
	@Override
	public String getLink() 
	{
		return "\\href{" + target + "}{" + getName() + "}";
	}
	
	/**
	 * @return formatted Image
	 */
	public String getFormattedImage()
	{
		return this.formatedLatexImage;
	}
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.link.Link#toString()
	 */
	public String toString()
	{
		return getLink();
	}
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.link.Link#length()
	 */
	public int length()
	{
		return this.target.length();
	}
	
	/* (non-Javadoc)
	 * @see nisbet.andrew.link.Link#getRawLink()
	 */
	public String getRawLink() 
	{
		return this.target;
	}

	/**
	 * @param latexFigure
	 */
	public void setLaTeXFigure( Figure latexFigure ) 
	{
		this.formatedLatexImage = latexFigure.toString();
	}

}
