package idgenerator.xml;

import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;
import org.junit.Test;

import java.io.FileFilter;

public class AddIdOperationTest {
    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".xhtml");

    private MavenLogger log;

    @Before
    public void setUp() throws Exception {
        log = new MavenLoggerImpl(new SystemStreamLog());
    }

        @Test
    public void dummy() {}

//    @Test
//    public void testGenerate2FindIds() throws MojoFailureException {
//        XmlParser xmlParser = new XmlParser();
//        FileList xhtmlFiles = new FileList(new File("src/test/resources/gen"));
//        xhtmlFiles.findFiles(fileFilter);
//        IdGenerator idGenerator = new IdGenerator(log, "gen");
//        xmlParser.parse(xhtmlFiles, new AddIdOperation(idGenerator));
//        assertEquals(3, idGenerator.getIdSet().size());
//        assertEquals(true, idGenerator.contains("test1"));
//        assertEquals(true, idGenerator.contains("test2"));
//        assertEquals(true, idGenerator.contains("test3"));
//    }

}
