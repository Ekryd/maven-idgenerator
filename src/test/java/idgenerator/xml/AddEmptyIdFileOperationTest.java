package idgenerator.xml;

import org.junit.Test;

import java.io.FileFilter;

public class AddEmptyIdFileOperationTest {
    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".xhtml");

        @Test
    public void dummy() {}

//    @Test
//    public void testGenerate1FindFiles() throws MojoFailureException {
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
//        xhtmlFiles.findFiles(fileFilter);
//        List<File> files = xmlParser.parse(xhtmlFiles, new AddEmptyIdFileOperation(
//                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
//        assertEquals(2, files.size());
//        assertEquals("out.xhtml", files.get(0).getName());
//        assertEquals("out4.xhtml", files.get(1).getName());
//    }
//
}
