package idgenerator.file;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class FileListTest {

	@Test
	public void testFindFiles() {
		FileList xhtmlFiles = new FileList(new File("src/test/resources"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		assertEquals("src/test/resources/fail/out.xhtml\n" + "src/test/resources/fail/out2.xhtml\n"
				+ "src/test/resources/gen/out.xhtml\n" + "src/test/resources/gen/out2.xhtml\n"
				+ "src/test/resources/gen/out3.xhtml\n" + "src/test/resources/gen/out4.xhtml\n"
				+ "src/test/resources/ok/out.xhtml\n", xhtmlFiles.toString());
	}

}
