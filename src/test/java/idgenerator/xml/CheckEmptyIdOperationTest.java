package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class CheckEmptyIdOperationTest {
	@Test
	public void testNoIdFailParse() throws MojoFailureException {
		MavenLogger log = new MavenLoggerImpl(new SystemStreamLog());
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
		assertEquals(true, actual);
	}

	@Test
	public void testNoIdOkParse() throws MojoFailureException {
		MavenLogger log = new MavenLoggerImpl(new SystemStreamLog());
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/ok"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
		assertEquals(false, actual);
	}

}
