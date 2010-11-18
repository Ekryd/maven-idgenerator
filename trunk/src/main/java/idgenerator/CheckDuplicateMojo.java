package idgenerator;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Check xml files for duplicate element ids
 * 
 * @author Bjorn Ekryd
 * @goal check-duplicate
 * @phase test
 */
public class CheckDuplicateMojo extends AbstractMojo {

	/**
	 * @parameter expression="${idgen.baseFileDirectory}"
	 *            default-value="${project.build.sourceDirectory}"
	 */
	private File baseFileDirectory;

	/**
	 * @parameter expression="${idgen.fileSuffix}" default-value=".xhtml";
	 */
	private String fileSuffix;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		outputInfo();
		FileList xhtmlFiles = findXHtmlFiles();
		XmlParser parser = new XmlParser(getLog(), "");
		parser.checkForDuplicateIds(xhtmlFiles);
	}

	private FileList findXHtmlFiles() {
		FileList fileList = new FileList(baseFileDirectory);
		fileList.findFiles(new XmlFileFilter(fileSuffix));
		return fileList;
	}

	private void outputInfo() {
		getLog().info(
				String.format("Scanning all files ending with '%s' under the directory %s", fileSuffix,
						baseFileDirectory));

	}
}
