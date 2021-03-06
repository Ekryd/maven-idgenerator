package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static org.junit.Assert.assertEquals;

public class CheckEmptyIdOperationTest {
    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".xhtml");

    @Test
    public void testNoIdFailParse() throws MojoFailureException {
        MavenLogger log = new MavenLoggerImpl(new SystemStreamLog());
        XmlParser xmlParser = new XmlParser();
        FileList xhtmlFiles = new FileList(new File("src/test/resources/fail"));
        xhtmlFiles.findFiles(fileFilter);
        boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
        assertEquals(true, actual);
    }

    @Test
    public void testNoIdOkParse() throws MojoFailureException {
        MavenLogger log = new MavenLoggerImpl(new SystemStreamLog());
        XmlParser xmlParser = new XmlParser();
        FileList xhtmlFiles = new FileList(new File("src/test/resources/ok"));
        xhtmlFiles.findFiles(fileFilter);
        boolean actual = xmlParser.parse(xhtmlFiles, new CheckEmptyIdOperation(log,
                "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)"));
        assertEquals(false, actual);
    }

}
