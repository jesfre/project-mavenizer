package com.blogspot.jesfre.projectmavenizer.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.model.Dependency;

import com.blogspot.jesfre.projectmavenizer.Constants;

/**
 * Utility to generate a pom.xml file for a Java Web project with a non-standard
 * Maven structure
 * 
 * @author <a href="mailto:jorge.ruiz.aquino@gmail.com">Jorge Ruiz Aquino<a>
 *         17/07/2012
 */
public class MavenizerUtil {
	private static final Log LOG = LogFactory.getLog(MavenizerUtil.class);

	private static String rootDir;
	private static String srcDir;
	private static String webDir;
	private static String repoDir;
	private static String projectName;

	/**
	 * Mavenize a Java Web project with a non-standard Maven structure
	 * @param rootDirectory The project root folder system path
	 * @param sourceDirectory The source folder path from root folder
	 * @param webappDirectory The webapp content folder path from root folder
	 * @param webinfDirectory The WEBINF folder path from root folder
	 * @param libraryDirectory The lib or library folder path from root folder
	 * @param projectName Optional project name. If null, the root folder name is set as project name.
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws SuiteGenerationException
	 */
	public String mavenize(String rootDirectory, String sourceDirectory, String webappDirectory, String libraryDirectory, String projectName) throws URISyntaxException, IOException {
		LOG.trace("Received parameters: "
				+ "\n\t projectName=" + projectName
				+ "\n\t rootDirectory=" + rootDirectory
				+ "\n\t sourceDirectory=" + sourceDirectory
				+ "\n\t webappDirectory=" + webappDirectory
				+ "\n\t libraryDirectory=" + libraryDirectory);

		MavenizerUtil.rootDir = rootDirectory;
		MavenizerUtil.srcDir = sourceDirectory;
		MavenizerUtil.webDir = webappDirectory;
		MavenizerUtil.repoDir = libraryDirectory;
		MavenizerUtil.projectName = projectName;

		if (StringUtils.isBlank(projectName)) {
			File rootDirFile = new File(rootDir);
			String rootDirName = rootDirFile.getName();
			projectName = rootDirName;
			LOG.trace("New project name: " + projectName);
		}

		List<String> sourceDirList = new ArrayList<String>();
		String[] sourceDirArray = StringUtils.split(srcDir, ',');
		for (String srcDir : sourceDirArray) {
			sourceDirList.add(srcDir);
		}

		List<Dependency> dependencyList = new ArrayList<Dependency>();
		File libDirFile = new File(repoDir);
		Iterator<File> dependencyFileIt = FileUtils.iterateFiles(libDirFile, new String[] { "jar" }, true);
		for (; dependencyFileIt.hasNext();) {
			File file = dependencyFileIt.next();
			String fileName = file.getName();
			String message = "Next file: " + fileName;

			Dependency dependency = getDependency(fileName);
			dependencyList.add(dependency);
			message += " - OK";
			LOG.trace(message);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectName", projectName);
		params.put("rootDir", rootDir);
		params.put("srcDir", srcDir);
		params.put("webDir", webDir);
		params.put("repoDir", repoDir);
		params.put("srcDirList", sourceDirList);
		params.put("dependencyList", dependencyList);
		String generatedFile = VelocityUtils.evaluate(params, "/mavenizer-resources/pom.vm", rootDir, "pom.xml");
		return generatedFile;
	}

	/**
	 * Creates a new Maven Dependency object obtaining the parts from a JAR filename
	 * @param fileName
	 * @return
	 */
	private Dependency getDependency(String fileName) {
		String artifactId, groupId, version = "";
		String  basename = FilenameUtils.getBaseName(fileName);

		groupId = artifactId= basename;
		version = "1.0";
		Dependency dependency = new Dependency();
		dependency.setSystemPath(fileName);
		dependency.setGroupId(groupId);
		dependency.setArtifactId(artifactId);
		dependency.setVersion(version);
		return dependency;
	}

	public static String getRootDir() {
		return rootDir;
	}

	public static void setRootDir(String rootDir) {
		MavenizerUtil.rootDir = rootDir;
	}

	public static String getSrcDir() {
		return srcDir;
	}

	public static void setSrcDir(String srcDir) {
		MavenizerUtil.srcDir = srcDir;
	}

	public static String getWebDir() {
		return webDir;
	}

	public static void setWebDir(String webDir) {
		MavenizerUtil.webDir = webDir;
	}

	public static String getRepoDir() {
		return repoDir;
	}

	public static void setRepoDir(String repoDir) {
		MavenizerUtil.repoDir = repoDir;
	}

}
