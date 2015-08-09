package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.util.IdGenerator;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class XmlModifierTest {
	@Test
	public void testGenerate3GenerateFiles() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		List<File> files = xmlParser.parse(xhtmlFiles, new AddEmptyIdFileOperation(
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
		IdGenerator idGenerator = new IdGenerator(log, "gen");
		xmlParser.parse(xhtmlFiles, new AddIdOperation(idGenerator));
		XmlModifier xmlModifier = new XmlModifier(idGenerator, "UTF-8", "  ", "\n",
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");
		List<GeneratedFile> parseFiles = xmlModifier.parseFiles(files);
		assertEquals(2, parseFiles.size());
		assertEquals("out.xhtml", parseFiles.get(0).getFileName().getName());
		assertEquals(
				"src/test/resources/gen/out.xhtml\n"
						+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
						+ "\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:h=\"http://java.sun.com/jsf/html\" xmlns:f=\"http://java.sun.com/jsf/core\" xmlns:a4j=\"http://richfaces.org/a4j\" xmlns:rich=\"http://richfaces.org/rich\" xmlns:c=\"http://java.sun.com/jstl/core\" xmlns:fn=\"http://java.sun.com/jsp/jstl/functions\" xml:lang=\"en\" lang=\"en\">\n"
						+ "  <body>\n" + "    <ui:composition>\n"
						+ "      <h:outputText id=\"test1\" value=\"Out\" />\n"
						+ "      <h:outputText id=\"test2\" value=\"Out\" />\n"
						+ "      <h:outputText id=\"gen1\" value=\"Out\" />\n" + "    </ui:composition>\n"
						+ "  </body>\n" + "</html>\n\n\n", parseFiles.get(0).toString());

		assertEquals("out4.xhtml", parseFiles.get(1).getFileName().getName());
		assertEquals(
				"src/test/resources/gen/out4.xhtml\n"
						+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
						+ "\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:h=\"http://java.sun.com/jsf/html\" xmlns:f=\"http://java.sun.com/jsf/core\" xmlns:a4j=\"http://richfaces.org/a4j\" xmlns:rich=\"http://richfaces.org/rich\" xmlns:c=\"http://java.sun.com/jstl/core\" xmlns:fn=\"http://java.sun.com/jsp/jstl/functions\" xml:lang=\"en\" lang=\"en\">\n"
						+ "  <body>\n" + "    <ui:composition>\n"
						+ "      <h:outputText id=\"gen2\" value=\"Out\" />\n" + "    </ui:composition>\n"
						+ "  </body>\n" + "</html>\n\n\n", parseFiles.get(1).toString());
	}

}
