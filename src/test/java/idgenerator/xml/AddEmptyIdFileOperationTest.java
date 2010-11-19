package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class AddEmptyIdFileOperationTest {
	@Test
	public void testGenerate1FindFiles() throws MojoFailureException {
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		List<File> files = xmlParser.parse(xhtmlFiles, new AddEmptyIdFileOperation());
		Assert.assertEquals(2, files.size());
		Assert.assertEquals("out.xhtml", files.get(0).getName());
		Assert.assertEquals("out4.xhtml", files.get(1).getName());
	}

}
