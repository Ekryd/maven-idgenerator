package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.util.ElementUtil;
import org.apache.maven.plugin.MojoFailureException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Traverses xml files without modifying them
 *
 * @author bjorn
 */
public class XmlParser {

    private final SAXBuilder saxBuilder;

    public XmlParser() {
        this.saxBuilder = new SAXBuilder(false);
        this.saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    }
    
    private <T> T parse(final File file, final XmlParserOperation<T> operation, final T oldValue)
            throws MojoFailureException {
        try {
            Document doc = saxBuilder.build(file);
            List<Element> elements = ElementUtil.getElements(doc);
            return operation.perform(file, elements, oldValue);
        } catch (Exception e) {
            throw new MojoFailureException("Cannot parse file " + file, e);
        }

    }

    public <T> T parse(final FileList fileList, final XmlParserOperation<T> operation) throws MojoFailureException {
        T returnValue = operation.getInitialValue();
        Collection<File> files = fileList.getFiles();
        for (File file : files) {
            returnValue = parse(file, operation, returnValue);
        }
        return returnValue;
    }

}
