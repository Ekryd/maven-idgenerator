package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.util.IdGenerator;
import idgenerator.xml.AddEmptyIdFileOperation;
import idgenerator.xml.AddIdOperation;
import idgenerator.xml.GeneratedFile;
import idgenerator.xml.XmlModifier;
import idgenerator.xml.XmlParser;

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
	 * @parameter expression="${idgen.baseDirectory}"
	 *            default-value="${project.build.sourceDirectory}"
	 */
	private File baseDirectory;

	/**
	 * @parameter expression="${idgen.generateDirectory}"
	 *            default-value="${project.build.sourceDirectory}"
	 */
	private File generateDirectory;

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
		FileList xhtmlGenFiles = findGenerateXHtmlFiles();
		XmlParser parser = new XmlParser();
		List<File> fileToGenerate = parser.parse(xhtmlGenFiles, new AddEmptyIdFileOperation());
		if (fileToGenerate.isEmpty()) {
			getLog().info("No files without ids");
			return;
		}

		FileList xhtmlBaseFiles = findBaseXHtmlFiles();
		IdGenerator idGenerator = new IdGenerator(getLog(), idPrefix);
		parser.parse(xhtmlBaseFiles, new AddIdOperation(idGenerator));

		XmlModifier xmlModifier = new XmlModifier(idGenerator);
		List<GeneratedFile> files = xmlModifier.parseFiles(fileToGenerate);
		for (GeneratedFile generatedFiles : files) {
			System.out.println(generatedFiles);
			// generatedFiles.saveFile(getLog());
		}
	}

	private FileList findBaseXHtmlFiles() {
		FileList fileList = new FileList(baseDirectory);
		fileList.findFiles(new XmlFileFilter(fileSuffix));
		return fileList;
	}

	private FileList findGenerateXHtmlFiles() {
		FileList fileList = new FileList(baseDirectory);
		fileList.findFiles(new XmlFileFilter(fileSuffix));
		return fileList;
	}

	private void outputInfo() {
		getLog().info(
				String.format(
						"Generating ids for files ending with '%s' under the directory %s. Ids are scanned from directory %s",
						fileSuffix, generateDirectory, baseDirectory));

	}
}
