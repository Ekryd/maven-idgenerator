package idgenerator.util;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;

import java.util.List;

/**
 * Filters out Element objects
 *
 * @author bjorn
 */
public final class ElementUtil {

    @SuppressWarnings("unchecked")
    public static void addAttribute(final Element element, final Attribute attribute) {
        element.getAttributes().add(0, attribute);
    }

    @SuppressWarnings("unchecked")
    public static List<Element> getElements(final Document doc) {
        return doc.getContent((Filter) obj -> obj instanceof Element);
    }

    @SuppressWarnings("unchecked")
    public static List<Element> getElements(final Element element) {
        return element.getContent((Filter) obj -> obj instanceof Element);
    }


}
