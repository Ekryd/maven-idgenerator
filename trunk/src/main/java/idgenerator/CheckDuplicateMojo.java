package idgenerator;

import idgenerator.file.*;
import idgenerator.util.IdGenerator;
import idgenerator.xml.*;

import java.io.File;

import org.apache.maven.plugin.*;

/**
 * Check xml files for duplicate element ids
 *
 * @author Bjorn Ekryd
 * @goal check-duplicate
 * @phase test
 * @description Checks if xml-files contains elements with duplicate ids
 * @since 1.0.0
 */
public class CheckDuplicateMojo extends AbstractMojo {

	/**
	 * @parameter expression="${idgen.baseDirectory}"
	 *            default-value="${project.build.sourceDirectory}"
	 * @description base directory for all xml-files
	 */
	private File baseDirectory;

	/**
	 * @parameter expression="${idgen.fileSuffix}" default-value=".xhtml";
	 * @description file suffix för xml-files
	 */
	private String fileSuffix;

	/**
	 * @parameter expression="${idgen.elements}"
	 * @description regular expression for all elements that should have ids
	 */
	private String elements;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		outputInfo();
		FileList xhtmlFiles = findXHtmlFiles();
		XmlParser parser = new XmlParser();
		boolean containsDuplicates = parser.parse(xhtmlFiles, new CheckDuplicateOperation(
				new IdGenerator(getLog(), ""), elements));
		if (containsDuplicates) {
			throw new MojoFailureException("Contains duplicate ids");
		}

	}

	private FileList findXHtmlFiles() {
		FileList fileList = new FileList(baseDirectory);
		fileList.findFiles(new XmlFileFilter(fileSuffix));
		return fileList;
	}

	private void outputInfo() {
		getLog().info(
				String.format("Scanning all files ending with '%s' under the directory %s", fileSuffix, baseDirectory));

	}
}
