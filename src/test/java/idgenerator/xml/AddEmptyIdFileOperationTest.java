package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddEmptyIdFileOperationTest {
	@Test
	public void testGenerate1FindFiles() throws MojoFailureException {
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		List<File> files = xmlParser.parse(xhtmlFiles, new AddEmptyIdFileOperation(
				"(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
		assertEquals(2, files.size());
		assertEquals("out.xhtml", files.get(0).getName());
		assertEquals("out4.xhtml", files.get(1).getName());
	}

}
