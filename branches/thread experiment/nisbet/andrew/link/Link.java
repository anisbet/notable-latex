package nisbet.andrew.link;

/**
 * A basic link object usually used when the query fails.
 * @author anisbet
 *
 */
public class Link 
{
	private String name;
	
	/**
	 * @param linkName
	 */
	public Link(String linkName) 
	{
		this.setName(linkName);
	}

	/**
	 * @return the link target or the href itself if there is no URL.
	 */
	public String getLink() 
	{
		return this.getName();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return getLink();
	}

	/**
	 * @return the length of the link used to tell if a link has content.
	 */
	public int length() 
	{
		return this.getName().length();
	}

	/**
	 * @return link URL as a string without any adornments.
	 */
	public String getRawLink() 
	{
		return this.getName();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
