/**
 * 
 */
package com.blogspot.jesfre.projectmavenizer;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class MavenizationTest.
 *
 * @author <a href="jorge.ruiz.aquino@gmail.com">Jorge Ruiz Aquino</a>
 * 24/07/2014
 */
public class MavenizationTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.blogspot.jesfre.projectmavenizer.Main#mavenizeMe(String)}.
	 */
	@Test
	public void testMavenizeMe() {
		Main main = new Main();
		URL proUrl = MavenizationTest.class.getClassLoader().getResource("sample.properties");
		String filePath = proUrl.getFile();
		String generatedPom = main.mavenizeMe(filePath);
		assertTrue(new File(generatedPom).exists());
	}

}
