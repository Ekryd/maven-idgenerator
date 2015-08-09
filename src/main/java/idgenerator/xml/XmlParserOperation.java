package idgenerator.xml;

import org.jdom.Element;

import java.io.File;
import java.util.List;

/**
 * Defines an operation that can be performed when traversing an xml file
 */
public interface XmlParserOperation<T> {
    T getInitialValue();

    T perform(File file, List<Element> elements, T oldValue);
}
