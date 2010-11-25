package idgenerator.xml;

import idgenerator.file.*;

import java.io.File;

import junit.framework.Assert;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.*;
import org.junit.Test;

public class CheckEmptyIdOperationTest {
	@Test
	public void testNoIdFailParse() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
		Assert.assertEquals(true, actual);
	}

	@Test
	public void testNoIdOkParse() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/ok"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
		Assert.assertEquals(false, actual);
	}

}
