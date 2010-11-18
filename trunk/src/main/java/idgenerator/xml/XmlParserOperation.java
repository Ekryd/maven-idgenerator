package idgenerator.xml;

import java.io.File;
import java.util.List;

import org.jdom.Element;

public interface XmlParserOperation<T> {
	public T getInitialValue();

	public T perform(File file, List<Element> elements, T oldValue);
}
