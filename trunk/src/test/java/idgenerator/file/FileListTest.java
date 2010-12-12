package idgenerator.file;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class FileListTest {

	@Test
	public void testFindFiles() {
		FileList xhtmlFiles = new FileList(new File("src/test/resources"));
		xhtmlFiles.findFiles(new XmlFileFilter(".xhtml"));
		Assert.assertEquals("src/test/resources/fail/out.xhtml\n" + "src/test/resources/fail/out2.xhtml\n"
				+ "src/test/resources/gen/out.xhtml\n" + "src/test/resources/gen/out2.xhtml\n"
				+ "src/test/resources/gen/out3.xhtml\n" + "src/test/resources/gen/out4.xhtml\n"
				+ "src/test/resources/ok/out.xhtml\n", xhtmlFiles.toString());
	}

}
