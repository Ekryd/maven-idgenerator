package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.XmlFileFilter;
import idgenerator.logger.MavenLoggerImpl;
import idgenerator.util.IdGenerator;
import idgenerator.xml.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Generates ids for elements in xml-files
 *
 * @author Bjorn Ekryd
 * @goal generateid
 * @phase process-resources
 */
@SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
public class GenerateIdMojo extends AbstractMojo {

    /**
     * Base directory for all xml-files
     *
     * @parameter property="idgen.baseDirectory"
     * default-value="${project.build.sourceDirectory}"
     */
    private File baseDirectory;

    /**
     * Base directory for xml-files that will get generated ids
     *
     * @parameter property="idgen.generateDirectory"
     * default-value="${project.build.sourceDirectory}"
     */
    private File generateDirectory;

    /**
     * Prefix för generated idvalues.
     *
     * @parameter property="idgen.idPrefix" default-value="gen"
     */
    private String idPrefix;

    /**
     * File suffix för xml-files
     * <p>
     * property="idgen.encoding"eSuffix" default-value=".xhtml";
     */
    private String fileSuffix;

    /**
     * Encoding used when parsing xml and writing files
     *
     * @parameter property="idgen.encoding" default-value="UTF-8"
     */
    private String encoding;

    /**
     * Line separator for xml. Can be either \n, \r or \r\n
     *
     * @parameter property="idgen.lineSeparator"
     * default-value="${line.separator}"
     */
    private String lineSeparator;

    /**
     * Number of space characters to use as indentation. A value of -1 indicates
     * that tab character should be used instead.
     *
     * @parameter property="idgen.nrOfIndentSpace" default-value="2"
     */
    private int nrOfIndentSpace;

    /**
     * Regular expression for all elements that should have ids
     *
     * @parameter property="idgen.elements"
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
        MavenLoggerImpl logger = new MavenLoggerImpl(getLog());
        IdGenerator idGenerator = new IdGenerator(logger, idPrefix);
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
     * @throws MojoFailureException the mojo failure exception
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
        getLog().info(String.format("Generating ids for files ending with '%s' under the directory %s. Ids are scanned from directory %s",
                fileSuffix, generateDirectory, baseDirectory));

    }
}
