package com.blogspot.jesfre.projectmavenizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.blogspot.jesfre.projectmavenizer.utils.MavenizerUtil;

/**
 * The runnable main class.
 * @author <a href="jorge.ruiz.aquino@gmail.com">Jorge Ruiz Aquino</a>
 * 24/07/2014
 */
public class Main {
	public static void main(String[] args) {
		// Get JAR location
		String jarLocation = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String propertiesLocation = FilenameUtils.getFullPath(jarLocation);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Set the properties file name. <Enter> to use sample.properties: ");
		String propertiesFilename = scanner.nextLine();
		if(StringUtils.isBlank(propertiesFilename)) {
			propertiesFilename = "sample.properties";
		}
		propertiesLocation += propertiesFilename;

		System.out.println("Loading " + propertiesLocation);
		Main main = new Main();
		String generatedFile = main.mavenizeMe(propertiesLocation);
		System.out.println("Generated file is: " + generatedFile);
	}

	public String mavenizeMe(String propertiesFileLocation) {
		InputStream in = null;
		String generatedFile = null;
		try {
			// Load properties from location
			in = FileUtils.openInputStream(new File(propertiesFileLocation));
			Properties properties = new Properties();
			properties.load(in);

			// Load parameters for mavenization
			String projectName = properties.getProperty(Constants.PROJECT_NAME);
			String rootDirectory = properties.getProperty(Constants.ROOT_DIRECTORY);
			String sourceDirectory = properties.getProperty(Constants.SOURCE_DIRECTORY);
			String webappDirectory = properties.getProperty(Constants.WEBAPP_DIRECTORY);
			String libraryDirectory = properties.getProperty(Constants.LIBRARY_DIRECTORY);

			// Do the job
			MavenizerUtil mavenizer = new MavenizerUtil();
			generatedFile = mavenizer.mavenize(rootDirectory, sourceDirectory, webappDirectory, libraryDirectory, projectName);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return generatedFile;
	}
}
