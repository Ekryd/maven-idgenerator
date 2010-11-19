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
import java.util.Arrays;
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

	/**
	 * Encoding for the files.
	 * 
	 * @parameter expression="${sort.encoding}" default-value="UTF-8"
	 */
	private String encoding;

	/**
	 * Line separator for sorted pom. Can be either \n, \r or \r\n
	 * 
	 * @parameter expression="${sort.lineSeparator}"
	 *            default-value="${line.separator}"
	 */
	private String lineSeparator;

	/**
	 * Number of space characters to use as indentation. A value of -1 indicates
	 * that tab character should be used instead.
	 * 
	 * @parameter expression="${sort.nrOfIndentSpace}" default-value="2"
	 */
	private int nrOfIndentSpace;

	private static final int MAX_INDENT_SPACES = 255;

	/** Indicates that a tab character should be used instead of spaces. */
	private static final int INDENT_TAB = -1;

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

		XmlModifier xmlModifier = new XmlModifier(idGenerator, encoding, getIndentCharacters(), lineSeparator);
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

	/**
	 * Gets the indent characters from parameter.
	 * 
	 * @return the indent characters
	 * @throws MojoFailureException
	 *             the mojo failure exception
	 */
	private String getIndentCharacters() throws MojoFailureException {
		if (nrOfIndentSpace == 0) {
			return "";
		}
		if (nrOfIndentSpace == INDENT_TAB) {
			return "\t";
		}
		if (nrOfIndentSpace < INDENT_TAB || nrOfIndentSpace > MAX_INDENT_SPACES) {
			throw new MojoFailureException("nrOfIndentSpace cannot be below -1 or above 255: " + nrOfIndentSpace);
		}
		char[] chars = new char[nrOfIndentSpace];
		Arrays.fill(chars, ' ');
		return new String(chars);
	}

	private void outputInfo() {
		getLog().info(
				String.format(
						"Generating ids for files ending with '%s' under the directory %s. Ids are scanned from directory %s",
						fileSuffix, generateDirectory, baseDirectory));

	}
}
