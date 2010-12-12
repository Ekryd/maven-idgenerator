package idgenerator;

import java.io.File;

import junit.framework.Assert;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import cheesymock.Cheesy;
import cheesymock.Recorder;

public class CheckDuplicateMojoTest extends AbstractMojoTestCase {
	private Recorder recorder;

	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		// required
		super.setUp();
		recorder = new Recorder();
	}

	/** {@inheritDoc} */
	@Override
	protected void tearDown() throws Exception {
		// required
		super.tearDown();
	}

	/**
	 * @throws Exception
	 *             if any
	 */
	public void testFail() throws Exception {
		File pom = getTestFile("src/test/resources/test-pom-fail.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		CheckDuplicateMojo myMojo = (CheckDuplicateMojo) lookupMojo("check-duplicate", pom);
		Log log = Cheesy.mock(Log.class);
		recorder.expect(log)
				.info("Scanning all files ending with '.xhtml' under the directory src/test/resources/fail");
		recorder.expect(log)
				.error("The files src/test/resources/fail/out.xhtml and src/test/resources/fail/out.xhtml both contain the id test");
		recorder.expect(log)
				.error("The files src/test/resources/fail/out2.xhtml and src/test/resources/fail/out2.xhtml both contain the id gurka");
		myMojo.setLog(log);
		assertNotNull(myMojo);
		try {
			myMojo.execute();
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Contains duplicate ids", e.getMessage());
		}
		recorder.check();
	}

	/**
	 * @throws Exception
	 *             if any
	 */
	public void testFailOnlyGenerated() throws Exception {
		File pom = getTestFile("src/test/resources/test-pom-fail_onlyGen.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		CheckDuplicateMojo myMojo = (CheckDuplicateMojo) lookupMojo("check-duplicate", pom);
		Log log = Cheesy.mock(Log.class);
		recorder.expect(log)
				.info("Scanning all files ending with '.xhtml' under the directory src/test/resources/fail");
		recorder.expect(log)
				.error("The files src/test/resources/fail/out.xhtml and src/test/resources/fail/out.xhtml both contain the id test");
		myMojo.setLog(log);
		assertNotNull(myMojo);
		try {
			myMojo.execute();
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Contains duplicate ids", e.getMessage());
		}
		recorder.check();
	}

	/**
	 * @throws Exception
	 *             if any
	 */
	public void testOk() throws Exception {
		File pom = getTestFile("src/test/resources/test-pom-ok.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		CheckDuplicateMojo myMojo = (CheckDuplicateMojo) lookupMojo("check-duplicate", pom);
		Log log = Cheesy.mock(Log.class);
		recorder.expect(log).info("Scanning all files ending with '.xhtml' under the directory src/test/resources/ok");
		myMojo.setLog(log);
		assertNotNull(myMojo);
		myMojo.execute();
		recorder.check();
	}
}
