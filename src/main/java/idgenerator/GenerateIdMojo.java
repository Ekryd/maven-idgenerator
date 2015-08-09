package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.util.IdGenerator;
import idgenerator.xml.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Check xml files for duplicate element ids
 *
 * @author Bjorn Ekryd
 * @goal generateid
 * @phase process-resources
 * @description Generates ids for elements in xml-files
 * @since 1.0.0
 */
public class GenerateIdMojo extends AbstractMojo {

	/**
	 * @parameter property="idgen.baseDirectory"
	 *            default-value="${project.build.sourceDirectory}"
	 * @description base directory for all xml-files
	 */
	private File baseDirectory;

	/**
	 * @parameter property="idgen.generateDirectory"
	 *            default-value="${project.build.sourceDirectory}"
	 * @description base directory for xml-files that will get generated ids
	 */
	private File generateDirectory;

	/**
	 * @parameter property="idgen.idPrefix" default-value="gen"
	 * @description prefix för generated idvalues.
	 */
	private String idPrefix;

	/**
	 * property="idgen.encoding"eSuffix" default-value=".xhtml";
	 * @description file suffix för xml-files
	 */
	private String fileSuffix;

	/**
	 * Encoding for the files.
	 *
	 * @parameter property="idgen.encoding" default-value="UTF-8"
	 * @description encoding used when parsing xml and writing files
	 */
	private String encoding;

	/**
	 * Line separator for xml. Can be either \n, \r or \r\n
	 *
	 * @parameter property="idgen.lineSeparator"
	 *            default-value="${line.separator}"
	 * @description line separator used when writing xml-files
	 */
	private String lineSeparator;

	/**
	 * Number of space characters to use as indentation. A value of -1 indicates
	 * that tab character should be used instead.
	 *
	 * @parameter property="idgen.nrOfIndentSpace" default-value="2"
	 * @description indentation used when writing xml-files
	 */
	private int nrOfIndentSpace;

	/**
	 * @parameter property="idgen.elements"
	 * @description regular expression for all elements that should have ids
	 */
	private String elements;

	private static final int MAX_INDENT_SPACES = 255;

	/** Indicates that a tab character should be used instead of spaces. */
	private static final int INDENT_TAB = -1;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		outputInfo();
		FileList xhtmlGenFiles = findGenerateXHtmlFiles();
		XmlParser parser = new XmlParser();
		List<File> fileToGenerate = parser.parse(xhtmlGenFiles, new AddEmptyIdFileOperation(elements));
		if (fileToGenerate.isEmpty()) {
			getLog().info("No files without ids");
			return;
		} else {
			for (File file : fileToGenerate) {
				getLog().info("Will generate ids for file: " + file);
			}
		}

		FileList xhtmlBaseFiles = findBaseXHtmlFiles();
		IdGenerator idGenerator = new IdGenerator(getLog(), idPrefix);
		parser.parse(xhtmlBaseFiles, new AddIdOperation(idGenerator));

		XmlModifier xmlModifier = new XmlModifier(idGenerator, encoding, getIndentCharacters(), lineSeparator, elements);
		List<GeneratedFile> files = xmlModifier.parseFiles(fileToGenerate);
		for (GeneratedFile generatedFiles : files) {
			generatedFiles.saveFile(getLog());
		}
	}

	private FileList findBaseXHtmlFiles() {
		FileList fileList = new FileList(baseDirectory);
		fileList.findFiles(new XmlFileFilter(fileSuffix));
		return fileList;
	}

	private FileList findGenerateXHtmlFiles() {
		FileList fileList = new FileList(generateDirectory);
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
		getLog()
				.info(
						String
								.format(
										"Generating ids for files ending with '%s' under the directory %s. Ids are scanned from directory %s",
										fileSuffix, generateDirectory, baseDirectory));

	}
}
