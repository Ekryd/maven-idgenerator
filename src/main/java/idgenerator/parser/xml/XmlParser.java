package idgenerator.parser.xml;

import idgenerator.parser.ParserOperation;
import idgenerator.parser.PluginParser;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;

/**
 * Traverses xml files without modifying them
 *
 * @author bjorn
 */
public class XmlParser implements PluginParser {

//    private final SAXBuilder saxBuilder;

    public XmlParser() {
//        this.saxBuilder = new SAXBuilder(false);
//        this.saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    }

    @Override
    public <T> T parse(final File file, final ParserOperation<T> operation, final T oldValue)
            throws MojoFailureException {
        return null;
    }    
    
//    @Override
//    protected <T> T parse(final File file, final XmlParserOperation<T> operation, final T oldValue)
//            throws MojoFailureException {
//        try {
//            Document doc = saxBuilder.build(file);
//            List<Element> elements = ElementUtil.getElements(doc);
//            return operation.perform(file, elements, oldValue);
//        } catch (Exception e) {
//            throw new MojoFailureException("Cannot parse file " + file, e);
//        }
//        return null;
//    }
}
