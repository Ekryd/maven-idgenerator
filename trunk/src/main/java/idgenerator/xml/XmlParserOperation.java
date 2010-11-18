package idgenerator.xml;

import java.io.File;
import java.util.List;

import org.jdom.Element;

/**
 * Defines an operation that can be performed when traversing an xml file
 * 
 * @author bjorn
 * 
 * @param <T>
 */
public interface XmlParserOperation<T> {
	T getInitialValue();

	T perform(File file, List<Element> elements, T oldValue);
}
