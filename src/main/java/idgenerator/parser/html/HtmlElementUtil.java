package idgenerator.parser.html;


import idgenerator.structure.PluginElement;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Filters out Element objects
 *
 * @author bjorn
 */
public final class HtmlElementUtil {

    @SuppressWarnings("unchecked")
    public List<PluginElement> getElements(final Document doc) {
        return singletonList(elementFunc(doc.body()));
    }

    private PluginElement elementFunc(final Element element) {
        return new PluginElement() {

            @Override
            public String getAttribute(String key) {
                return element.attr(key);
            }

            @Override
            public List<PluginElement> getChildElements() {
                return element.childNodes().stream()
                        .filter(n -> n instanceof Element)
                        .map(n -> elementFunc((Element) n))
                        .collect(toList());
            }

            @Override
            public String getElementName() {
                return element.nodeName();
            }

            @Override
            public String toString() {
                return element.nodeName();
            }
        };
    }


}
