package idgenerator;

import idgenerator.file.FileList;
import idgenerator.file.FileUtil;
import idgenerator.logger.MavenLogger;
import idgenerator.logger.MavenLoggerImpl;
import idgenerator.parser.PluginParser;
import idgenerator.parser.xml.XmlParser;
import idgenerator.util.IdGenerator;
import idgenerator.xml.AddEmptyIdFileOperation;
import idgenerator.xml.AddIdOperation;
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
     * Base directory for all xml-files. This is needed to generate non-duplicated ids.
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

    private PluginParser parser;
    private FileList xhtmlBaseFiles;
    private MavenLogger logger;
    private FileList xhtmlGenFiles;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        outputInfo();
        setupEnvironment();
        runPlugin();
    }

    void setupEnvironment() {
        logger = new MavenLoggerImpl(getLog());
        xhtmlBaseFiles = FileUtil.findFiles(baseDirectory, fileSuffix);
        parser = new XmlParser();
        xhtmlGenFiles = FileUtil.findFiles(generateDirectory, fileSuffix);
    }

    void runPlugin() throws MojoFailureException {
        List<File> fileToGenerate = parser.parse(xhtmlGenFiles, new AddEmptyIdFileOperation(elements));
        if (fileToGenerate.isEmpty()) {
            logger.info("No files without ids");
            return;
        } else {
            for (File file : fileToGenerate) {
                logger.info("Will generate ids for file: " + file);
            }
        }

        IdGenerator idGenerator = new IdGenerator(logger, idPrefix);
        parser.parse(xhtmlBaseFiles, new AddIdOperation(idGenerator));

//        XmlModifier xmlModifier = new XmlModifier(idGenerator, encoding, getIndentCharacters(), lineSeparator, elements);
//        List<GeneratedFile> files = xmlModifier.parseFiles(fileToGenerate);
//        for (GeneratedFile generatedFiles : files) {
//            generatedFiles.saveFile(logger);
//        }
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
