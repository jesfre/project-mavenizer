package com.blogspot.jesfre.projectmavenizer.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.blogspot.jesfre.projectmavenizer.Constants;

/**
 * Utility to do the velocity job.
 * @author <a href="mailto:jorge.ruiz.aquino@gmail.com">Jorge Ruiz Aquino<a>
 *         23/06/2012
 */
public class VelocityUtils {
	private static final Log LOG = LogFactory.getLog(VelocityUtils.class);

	/**
	 * @param vParams
	 * @param templatePath
	 * @param outputDir
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws SuiteGenerationException
	 */
	public static String evaluate(Map<String, Object> vParams, String templatePath, String outputDir, String fileName) throws URISyntaxException, IOException {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		VelocityContext context = new VelocityContext();
		Iterator<Entry<String, Object>> paramIt = vParams.entrySet().iterator();
		while (paramIt.hasNext()) {
			Entry<String, Object> e = paramIt.next();
			context.put(e.getKey(), e.getValue());
		}

		Template template = ve.getTemplate(templatePath);
		String fileLocation = outputDir + Constants.SEPARATOR + fileName;
		File dir = new File(outputDir);
		dir.mkdirs();
		File file = new File(fileLocation);
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileLocation)));

		template.merge(context, writer);

		writer.flush();
		writer.close();
		LOG.trace("Generated file: " + fileLocation);
		return fileLocation;
	}

}