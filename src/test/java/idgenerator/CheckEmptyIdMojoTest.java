package idgenerator;

import idgenerator.logger.MavenLogger;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import refutils.ReflectionHelper;

import java.io.File;

import static org.mockito.Mockito.*;


public class CheckEmptyIdMojoTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MavenLogger mavenLogger = mock(MavenLogger.class);

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(mavenLogger);
    }

    @Test
    public void testProjectWithFaultyXhtmlFilesShouldFail() throws Exception {
        CheckEmptyIdMojo myMojo = new CheckEmptyIdMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources/fail"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");

        myMojo.setupEnvironment();

        expectedException.expectMessage("Contains missing ids");
        expectedException.expect(MojoFailureException.class);

        mojoHelper.setField(mavenLogger);

        try {
            myMojo.runPlugin();
        } finally {
            verify(mavenLogger).error(
                    "The file src/test/resources/fail/out.xhtml contains an element h:outputText which doesn't have an id");
        }

    }

    @Test
    public void testProjectWithOkXhtmlFilesShouldBeSuccessful() throws Exception {
        CheckEmptyIdMojo myMojo = new CheckEmptyIdMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources/ok"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");

        myMojo.setupEnvironment();

        mojoHelper.setField(mavenLogger);

        myMojo.runPlugin();
    }
}
