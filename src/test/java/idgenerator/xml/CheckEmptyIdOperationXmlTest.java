package idgenerator.xml;

import org.junit.Test;

import java.io.FileFilter;

public class CheckEmptyIdOperationXmlTest {
    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".xhtml");

        @Test
    public void dummy() {}

//    @Test
//    public void testNoIdFailParse() throws MojoFailureException {
//        MavenLogger log = new MavenLoggerImpl(new SystemStreamLog());
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
//        xhtmlFiles.findFiles(fileFilter);
//        boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
//                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
//        assertEquals(true, actual);
//    }
//
//    @Test
//    public void testNoIdOkParse() throws MojoFailureException {
//        MavenLogger log = new MavenLoggerImpl(new SystemStreamLog());
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/ok"));
//        xhtmlFiles.findFiles(fileFilter);
//        boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
//                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
//        assertEquals(false, actual);
//    }
//
}
