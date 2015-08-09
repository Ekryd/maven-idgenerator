package idgenerator;

import idgenerator.logger.MavenLogger;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.ExpectedException;
import refutils.ReflectionHelper;

import java.io.File;

import static org.mockito.Mockito.*;

public class GenerateIdMojoTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MavenLogger mavenLogger = mock(MavenLogger.class);

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteQuietly(new File("src/test/resources/gen2"));
        verifyNoMoreInteractions(mavenLogger);
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.copyDirectory(new File("src/test/resources/gen"), new File("src/test/resources/gen2"));
    }

    @Test
    public void generateIdsForFileShouldWork() throws Exception {
        GenerateIdMojo myMojo = new GenerateIdMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources"));
        mojoHelper.setField("generateDirectory", new File("src/test/resources/gen2"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("nrOfIndentSpace", 2);
        mojoHelper.setField("lineSeparator", "nl");
        mojoHelper.setField("encoding", "UTF-8");
        mojoHelper.setField("idPrefix", "test");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");

        myMojo.setupEnvironment();

        mojoHelper.setField(mavenLogger);

        myMojo.runPlugin();

        verify(mavenLogger).info("Will generate ids for file: src/test/resources/gen2/out.xhtml");
        verify(mavenLogger).info("Will generate ids for file: src/test/resources/gen2/out4.xhtml");

        String file = FileUtils.readFileToString(new File("src/test/resources/gen2/out.xhtml"));
        Assert.assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">nl"
                        + "nl"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:h=\"http://java.sun.com/jsf/html\" xmlns:f=\"http://java.sun.com/jsf/core\" xmlns:a4j=\"http://richfaces.org/a4j\" xmlns:rich=\"http://richfaces.org/rich\" xmlns:c=\"http://java.sun.com/jstl/core\" xmlns:fn=\"http://java.sun.com/jsp/jstl/functions\" xml:lang=\"en\" lang=\"en\">nl"
                        + "  <body>nl"
                        + "    <ui:composition>nl"
                        + "      <h:outputText id=\"test1\" value=\"Out\" />nl"
                        + "      <h:outputText id=\"test2\" value=\"Out\" />nl"
                        + "      <h:outputText id=\"test4\" value=\"Out\" />nl"
                        + "    </ui:composition>nl"
                        + "  </body>nl" + "</html>nl" + "nl", file);
        file = FileUtils.readFileToString(new File("src/test/resources/gen2/out4.xhtml"));
        Assert.assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">nl"
                        + "nl"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:h=\"http://java.sun.com/jsf/html\" xmlns:f=\"http://java.sun.com/jsf/core\" xmlns:a4j=\"http://richfaces.org/a4j\" xmlns:rich=\"http://richfaces.org/rich\" xmlns:c=\"http://java.sun.com/jstl/core\" xmlns:fn=\"http://java.sun.com/jsp/jstl/functions\" xml:lang=\"en\" lang=\"en\">nl"
                        + "  <body>nl"
                        + "    <ui:composition>nl"
                        + "      <h:outputText id=\"test5\" value=\"Out\" />nl"
                        + "    </ui:composition>nl"
                        + "  </body>nl" + "</html>nl" + "nl", file);
    }

    @Test
    public void tyrToGenerateIdsButNoFilesFound() throws Exception {
        GenerateIdMojo myMojo = new GenerateIdMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources"));
        mojoHelper.setField("generateDirectory", new File("src/test/resources/ok"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("nrOfIndentSpace", 2);
        mojoHelper.setField("lineSeparator", "nl");
        mojoHelper.setField("encoding", "UTF-8");
        mojoHelper.setField("idPrefix", "test");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");

        myMojo.setupEnvironment();

        mojoHelper.setField(mavenLogger);

        myMojo.runPlugin();

        verify(mavenLogger).info("No files without ids");
    }

}
