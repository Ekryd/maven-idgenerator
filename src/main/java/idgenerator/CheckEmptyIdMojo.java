package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.FileUtil;
import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import idgenerator.xml.CheckEmptyIdOperation;
import idgenerator.xml.XmlParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;

/**
 * Check xml files for element without ids
 *
 * @author Bjorn Ekryd
 * @goal check-emptyid
 * @phase test
 */
@SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
public class CheckEmptyIdMojo extends AbstractMojo {

	/**
	 * Base directory for all xml-files
	 * 
	 * @parameter property="idgen.baseDirectory"
	 *            default-value="${project.build.sourceDirectory}"
	 */
	private File baseDirectory;

	/**
	 * File suffix för xml-files
	 * 
	 * @parameter property="idgen.fileSuffix" default-value=".xhtml";
	 */
	private String fileSuffix;

	/**
	 * Regular expression for all elements that should have ids
	 * 
	 * @parameter property="idgen.elements"
	 */
	private String elements;
	
	private XmlParser parser;
	private FileList xhtmlFiles;
	private MavenLogger logger;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		outputInfo();
		setupEnvironment();
		runPlugin();
	}

	void setupEnvironment() {
		logger = new MavenLoggerImpl(getLog());
		xhtmlFiles = FileUtil.findFiles(baseDirectory, fileSuffix);
		parser = new XmlParser();
	}

	void runPlugin() throws MojoFailureException {
		boolean containsEmptyIds = parser.parse(xhtmlFiles, new CheckEmptyIdOperation(logger, elements));
		if (containsEmptyIds) {
			throw new MojoFailureException("Contains missing ids");
		}
	}

	private void outputInfo() {
		getLog().info(String.format("Scanning all files ending with '%s' under the directory %s", 
				fileSuffix, baseDirectory));

	}
}
