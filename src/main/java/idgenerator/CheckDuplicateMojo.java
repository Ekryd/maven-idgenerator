package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.FileUtil;
import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import idgenerator.util.IdGenerator;
import idgenerator.xml.CheckDuplicateOperation;
import idgenerator.xml.XmlParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;

/**
 * Checks if xml-files contains elements with duplicate ids
 * 
 * @author Bjorn Ekryd
 * @goal check-duplicate
 * @phase test
 */
@SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
public class CheckDuplicateMojo extends AbstractMojo {

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

	/**
	 * If only generated ids should be checked for duplication
	 * 
	 * @parameter eproperty="idgen.idPrefix"default-value="false";
	 */
	private boolean checkGeneratedIds;

	/**
	 * Prefix för generated idvalues.
	 * 
	 * @parameter property="idgen.idPrefix" default-value="gen"
	 */
	private String idPrefix;
	
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
		IdGenerator idGenerator = new IdGenerator(logger, "");
		boolean containsDuplicates = parser.parse(xhtmlFiles, new CheckDuplicateOperation(
				idGenerator, elements, checkGeneratedIds, idPrefix));
		if (containsDuplicates) {
			throw new MojoFailureException("Contains duplicate ids");
		}

	}

	private void outputInfo() {
		getLog().info(String.format("Scanning all files ending with '%s' under the directory %s", 
				fileSuffix, baseDirectory));

	}
}
