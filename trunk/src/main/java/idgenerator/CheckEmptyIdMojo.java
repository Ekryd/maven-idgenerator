package idgenerator;

import idgenerator.file.*;
import idgenerator.xml.*;

import java.io.File;

import org.apache.maven.plugin.*;

/**
 * Check xml files for element without ids
 *
 * @author Bjorn Ekryd
 * @goal check-emptyid
 * @phase test
 * @description Checks if xml-files contains elements without ids
 * @since 1.0.0
 */
public class CheckEmptyIdMojo extends AbstractMojo {

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
		boolean containsEmptyIds = parser.parse(xhtmlFiles, new CheckEmptyIdOperation(getLog(), elements));
		if (containsEmptyIds) {
			throw new MojoFailureException("Contains missing ids");
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
