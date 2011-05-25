package nisbet.andrew.service;

import nisbet.andrew.link.Link;

public interface ServiceRequest {

	public abstract boolean serviceSucceeded();

	/**
	 * @return url for the specified topic in Wikipedia or the unaltered link if 
	 * an error occurred or a reference could not be found.
	 */
	public abstract Link getLink();
	
	public abstract boolean isImageAvailable();

	public abstract String getImage();

	public abstract String getDescription();

	void downloadImage();

}