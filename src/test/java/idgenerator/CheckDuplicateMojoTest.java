package idgenerator;

import idgenerator.logger.MavenLogger;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import refutils.ReflectionHelper;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CheckDuplicateMojoTest  {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MavenLogger mavenLogger = mock(MavenLogger.class);

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(mavenLogger);
    }

    
    @Test
	public void duplicateIdsShouldGenerateException() throws Exception {
        CheckDuplicateMojo myMojo = new CheckDuplicateMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources/fail"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");

        myMojo.setupEnvironment();

        expectedException.expectMessage("Contains duplicate ids");
        expectedException.expect(MojoFailureException.class);

        mojoHelper.setField(mavenLogger);

        try {
            myMojo.runPlugin();
        } finally {
            verify(mavenLogger).error(
                    "The files src/test/resources/fail/out.xhtml and src/test/resources/fail/out.xhtml both contain the id test");
            verify(mavenLogger).error(
                    "The files src/test/resources/fail/out2.xhtml and src/test/resources/fail/out2.xhtml both contain the id gurka");
        }
    }

    @Test
	public void checkGeneratedShouldOnlyReportErrorOnGenerated() throws Exception {
        CheckDuplicateMojo myMojo = new CheckDuplicateMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources/fail"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");
        mojoHelper.setField("checkGeneratedIds", true);
        mojoHelper.setField("idPrefix", "test");

        myMojo.setupEnvironment();

        expectedException.expectMessage("Contains duplicate ids");
        expectedException.expect(MojoFailureException.class);

        mojoHelper.setField(mavenLogger);

        try {
            myMojo.runPlugin();
        } finally {
            verify(mavenLogger).error(
                    "The files src/test/resources/fail/out.xhtml and src/test/resources/fail/out.xhtml both contain the id test");
        }
    }

    @Test
	public void okIdsShouldNotReportErrors() throws Exception {
        CheckDuplicateMojo myMojo = new CheckDuplicateMojo();
        ReflectionHelper mojoHelper = new ReflectionHelper(myMojo);
        mojoHelper.setField("baseDirectory", new File("src/test/resources/ok"));
        mojoHelper.setField("fileSuffix", ".xhtml");
        mojoHelper.setField("elements", "(a:elem)|(h:outputText)|(e:elem)|(xx:elem)");

        myMojo.setupEnvironment();

        mojoHelper.setField(mavenLogger);

        myMojo.runPlugin();
    }

}
