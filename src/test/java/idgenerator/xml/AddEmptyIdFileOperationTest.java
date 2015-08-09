package idgenerator.xml;

import idgenerator.file.FileList;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddEmptyIdFileOperationTest {
    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".xhtml");

    @Test
    public void testGenerate1FindFiles() throws MojoFailureException {
        XmlParser xmlParser = new XmlParser();
        FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
        xhtmlFiles.findFiles(fileFilter);
        List<File> files = xmlParser.parse(xhtmlFiles, new AddEmptyIdFileOperation(
                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
        assertEquals(2, files.size());
        assertEquals("out.xhtml", files.get(0).getName());
        assertEquals("out4.xhtml", files.get(1).getName());
    }

}
