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

public class AddIdOperationTest {
	@Test
	public void testGenerate2FindIds() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		IdGenerator idGenerator = new IdGenerator(log, "gen");
		xmlParser.parse(xhtmlFiles, new AddIdOperation(idGenerator));
		Assert.assertEquals(3, idGenerator.getIdSet().size());
		Assert.assertEquals(true, idGenerator.contains("test1"));
		Assert.assertEquals(true, idGenerator.contains("test2"));
		Assert.assertEquals(true, idGenerator.contains("test3"));
	}

}
