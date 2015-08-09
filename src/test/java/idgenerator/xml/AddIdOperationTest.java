package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.util.IdGenerator;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class AddIdOperationTest {
	@Test
	public void testGenerate2FindIds() throws MojoFailureException {
		Log log = new SystemStreamLog();
		XmlParser xmlParser = new XmlParser();
		FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		IdGenerator idGenerator = new IdGenerator(log, "gen");
		xmlParser.parse(xhtmlFiles, new AddIdOperation(idGenerator));
		assertEquals(3, idGenerator.getIdSet().size());
		assertEquals(true, idGenerator.contains("test1"));
		assertEquals(true, idGenerator.contains("test2"));
		assertEquals(true, idGenerator.contains("test3"));
	}

}
