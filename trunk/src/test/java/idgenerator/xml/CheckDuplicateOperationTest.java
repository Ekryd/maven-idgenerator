package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.util.IdGenerator;

import java.io.File;

import junit.framework.Assert;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Test;

public class CheckDuplicateOperationTest {
	@Test
	public void testDuplicateIdFailParse() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckDuplicateOperation(new IdGenerator(log, "gen"),
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)", false, ""));
		Assert.assertEquals(true, actual);
	}

	@Test
	public void testDuplicateIdOkParse() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/ok"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckDuplicateOperation(new IdGenerator(log, "gen"),
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)", false, ""));
		Assert.assertEquals(false, actual);
	}

	@Test
	public void testDuplicateIdOnlyGenFailParse() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckDuplicateOperation(new IdGenerator(log, "gen"),
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)", true, "test"));
		Assert.assertEquals(true, actual);
	}

}
