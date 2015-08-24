package idgenerator.html;

import idgenerator.file.FileList;
import idgenerator.logger.MavenLogger;
import idgenerator.parser.PluginParser;
import idgenerator.parser.html.HtmlParser;
import idgenerator.xml.CheckEmptyIdOperation;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CheckEmptyIdOperationHtmlTest {
    private final FileFilter fileFilter = pathname -> pathname.getName().endsWith(".html");
    private PluginParser parser;
    private MavenLogger log = mock(MavenLogger.class);

    @Before
    public void setUp() throws Exception {
        parser = new HtmlParser();
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(log);
    }

    @Test
    public void noIdsShouldOutputErrorLog() throws MojoFailureException {
        File directory = new File("src/test/resources/fail");
        FileList files = new FileList(directory);
        files.findFiles(fileFilter);
        assertThat(files.getFiles().size(), is(2));
        boolean actual = parser.parse(files, new CheckEmptyIdOperation(log,
                "(elem)|(outputText)|(gurka)"));

        verify(log).error(String.format("The file %s contains an element outputtext which doesn't have an id", 
                new File(directory, "out.html").getPath()));
        verify(log).error(String.format("The file %s contains an element elem which doesn't have an id", 
                new File(directory, "out.html").getPath()));

        assertThat(actual, is(true));
    }

    @Test
    public void okIdsShouldWork() throws MojoFailureException {
        FileList files = new FileList(new File("src/test/resources/ok"));
        files.findFiles(fileFilter);
        assertThat(files.getFiles().size(), is(1));
        boolean actual = parser.parse(files, new CheckEmptyIdOperation(log,
                "(elem)|(outputText)"));
        assertEquals(false, actual);
    }

}
