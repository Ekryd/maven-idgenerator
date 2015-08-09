package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.util.IdGenerator;
import idgenerator.xml.CheckDuplicateOperation;
import idgenerator.xml.XmlParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;

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
	 * @parameter property="idgen.baseDirectory"
	 *            default-value="${project.build.sourceDirectory}"
	 * @description base directory for all xml-files
	 */
	private File baseDirectory;

	/**
	 * @parameter property="idgen.fileSuffix" default-value=".xhtml";
	 * @description file suffix för xml-files
	 */
	private String fileSuffix;

	/**
	 * @parameter property="idgen.elements"
	 * @description regular expression for all elements that should have ids
	 */
	private String elements;

	/**
	 * @parameter eproperty="idgen.idPrefix"default-value="false";
	 * @description if only generated ids should be checked for duplication
	 */
	private boolean checkGeneratedIds;

	/**
	 * @parameter property="idgen.idPrefix" default-value="gen"
	 * @description prefix för generated idvalues.
	 */
	private String idPrefix;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		outputInfo();
		FileList xhtmlFiles = findXHtmlFiles();
		XmlParser parser = new XmlParser();
		boolean containsDuplicates = parser.parse(xhtmlFiles, new CheckDuplicateOperation(
				new IdGenerator(getLog(), ""), elements, checkGeneratedIds, idPrefix));
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
