package idgenerator.parser.html;

import idgenerator.parser.ParserOperation;
import idgenerator.parser.PluginParser;
import idgenerator.structure.PluginElement;
import org.apache.maven.plugin.MojoFailureException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.List;

/**
 * Traverses xml files without modifying them
 *
 * @author bjorn
 */
public class HtmlParser implements PluginParser {

    private final HtmlElementUtil elementUtil;

    public HtmlParser() {
        elementUtil = new HtmlElementUtil();
    }


    @Override
    public <T> T parse(final File file, final ParserOperation<T> operation, final T oldValue)
            throws MojoFailureException {
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            List<PluginElement> elements = elementUtil.getElements(doc);
            return operation.perform(file, elements);
        } catch (Exception e) {
            throw new MojoFailureException("Cannot parse file " + file, e);
        }

    }
}
