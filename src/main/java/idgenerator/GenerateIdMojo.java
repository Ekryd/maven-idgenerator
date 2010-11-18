package idgenerator;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Check xml files for duplicate element ids
 * 
 * @author Bjorn Ekryd
 * @goal check-generateid
 * @phase test
 */
public class GenerateIdMojo extends AbstractMojo {

	/**
	 * @parameter expression="${idgen.baseFileDirectory}"
	 *            default-value="${project.build.sourceDirectory}"
	 */
	private File baseFileDirectory;

	/**
	 * @parameter expression="${idgen.idPrefix}" default-value="generated"
	 */
	private String idPrefix;

	/**
	 * @parameter expression="${idgen.fileSuffix}" default-value=".xhtml";
	 */
	private String fileSuffix;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		outputInfo();
		FileList xhtmlFiles = findXHtmlFiles();
		XmlParser parser = new XmlParser(getLog(), idPrefix);
		List<GeneratedFile> files = parser.parseFiles(xhtmlFiles);
		for (GeneratedFile generatedFiles : files) {
			System.out.println(generatedFiles);
			// generatedFiles.saveFile(getLog());
		}
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
