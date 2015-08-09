package idgenerator;

public class GenerateIdMojoTest {
//	private Recorder recorder;
//
//	/** {@inheritDoc} */
//	@Override
//	protected void setUp() throws Exception {
//		// required
//		super.setUp();
//		FileUtils.copyDirectory(new File("src/test/resources/gen"), new File("src/test/resources/gen2"));
//	}
//
//	/** {@inheritDoc} */
//	@Override
//	protected void tearDown() throws Exception {
//		FileUtils.deleteQuietly(new File("src/test/resources/gen2"));
//		// required
//		super.tearDown();
//	}
//
//	/**
//	 * @throws Exception
//	 *             if any
//	 */
//	public void testGen() throws Exception {
//		File pom = getTestFile("src/test/resources/test-pom-gen-gen.xml");
//		assertNotNull(pom);
//		assertTrue(pom.exists());
//
//		GenerateIdMojo myMojo = (GenerateIdMojo) lookupMojo("generateid", pom);
//		Log log = Cheesy.mock(Log.class);
//		recorder = new Recorder();
//		Log expect = recorder.expect(log);
//		expect.info("Generating ids for files ending with '.xhtml' under the directory src/test/resources/gen2. Ids are scanned from directory src/test/resources");
//		expect.info("Will generate ids for file: src/test/resources/gen2/out.xhtml");
//		expect.info("Will generate ids for file: src/test/resources/gen2/out4.xhtml");
//		myMojo.setLog(log);
//		assertNotNull(myMojo);
//		myMojo.execute();
//		recorder.check();
//		recorder = null;
//		String file = FileUtils.readFileToString(new File("src/test/resources/gen2/out.xhtml"));
//		Assert.assertEquals(
//				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">nl"
//						+ "nl"
//						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:h=\"http://java.sun.com/jsf/html\" xmlns:f=\"http://java.sun.com/jsf/core\" xmlns:a4j=\"http://richfaces.org/a4j\" xmlns:rich=\"http://richfaces.org/rich\" xmlns:c=\"http://java.sun.com/jstl/core\" xmlns:fn=\"http://java.sun.com/jsp/jstl/functions\" xml:lang=\"en\" lang=\"en\">nl"
//						+ "  <body>nl"
//						+ "    <ui:composition>nl"
//						+ "      <h:outputText id=\"test1\" value=\"Out\" />nl"
//						+ "      <h:outputText id=\"test2\" value=\"Out\" />nl"
//						+ "      <h:outputText id=\"test4\" value=\"Out\" />nl"
//						+ "    </ui:composition>nl"
//						+ "  </body>nl" + "</html>nl" + "nl", file);
//		file = FileUtils.readFileToString(new File("src/test/resources/gen2/out4.xhtml"));
//		Assert.assertEquals(
//				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">nl"
//						+ "nl"
//						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://java.sun.com/jsf/facelets\" xmlns:h=\"http://java.sun.com/jsf/html\" xmlns:f=\"http://java.sun.com/jsf/core\" xmlns:a4j=\"http://richfaces.org/a4j\" xmlns:rich=\"http://richfaces.org/rich\" xmlns:c=\"http://java.sun.com/jstl/core\" xmlns:fn=\"http://java.sun.com/jsp/jstl/functions\" xml:lang=\"en\" lang=\"en\">nl"
//						+ "  <body>nl"
//						+ "    <ui:composition>nl"
//						+ "      <h:outputText id=\"test5\" value=\"Out\" />nl"
//						+ "    </ui:composition>nl"
//						+ "  </body>nl" + "</html>nl" + "nl", file);
//	}
//
//	/**
//	 * @throws Exception
//	 *             if any
//	 */
//	public void testOk() throws Exception {
//		File pom = getTestFile("src/test/resources/test-pom-gen-ok.xml");
//		assertNotNull(pom);
//		assertTrue(pom.exists());
//
//		GenerateIdMojo myMojo = (GenerateIdMojo) lookupMojo("generateid", pom);
//		Log log = Cheesy.mock(Log.class);
//		recorder = new Recorder();
//		recorder.expect(log)
//				.info("Generating ids for files ending with '.xhtml' under the directory src/test/resources/ok. Ids are scanned from directory src/test/resources");
//		recorder.expect(log).info("No files without ids");
//		myMojo.setLog(log);
//		assertNotNull(myMojo);
//		myMojo.execute();
//		recorder.check();
//		recorder = null;
//	}
}
