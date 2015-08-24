package idgenerator.xml;

import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;
import org.junit.Test;

import java.io.FileFilter;

public class CheckDuplicateOperationTest {

    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".xhtml");
    private MavenLogger log;

    @Before
    public void setUp() throws Exception {
        log = new MavenLoggerImpl(new SystemStreamLog());
    }

        @Test
    public void dummy() {}

//    @Test
//    public void testDuplicateIdFailParse() throws MojoFailureException {
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
//        xhtmlFiles.findFiles(fileFilter);
//        boolean actual = xmlParser.parse(xhtmlFiles, new CheckDuplicateOperation(new IdGenerator(log, "gen"),
//                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)", false, ""));
//        assertEquals(true, actual);
//    }
//
//    @Test
//    public void testDuplicateIdOkParse() throws MojoFailureException {
//
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/ok"));
//        xhtmlFiles.findFiles(fileFilter);
//        boolean actual = xmlParser.parse(xhtmlFiles, new CheckDuplicateOperation(new IdGenerator(log, "gen"),
//                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)", false, ""));
//        assertEquals(false, actual);
//    }
//
//    @Test
//    public void testDuplicateIdOnlyGenFailParse() throws MojoFailureException {
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
//        xhtmlFiles.findFiles(fileFilter);
//        boolean actual = xmlParser.parse(xhtmlFiles, new CheckDuplicateOperation(new IdGenerator(log, "gen"),
//                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)", true, "test"));
//        assertEquals(true, actual);
//    }
//
}
