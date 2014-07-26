/**
 * 
 */
package com.blogspot.jesfre.projectmavenizer;

/**
 * Constant values
 * @author <a href="jorge.ruiz.aquino@gmail.com">Jorge Ruiz Aquino</a>
 * 24/07/2014
 */
public final class Constants {
	private Constants() {
	}

	public static final String SEPARATOR = System.getProperty("file.separator");

	// Property names
	public static final String PROJECT_NAME = "project.name";
	public static final String ROOT_DIRECTORY = "project.root.location";
	public static final String SOURCE_DIRECTORY = "project.source.locations";
	public static final String WEBAPP_DIRECTORY = "project.webapp.location";
	public static final String LIBRARY_DIRECTORY = "project.library.location";

}
