package idgenerator.xml;

import idgenerator.file.FileList;
import idgenerator.util.ElementFilter;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;

public class XmlParser {

	private final Filter elementFilter = new ElementFilter();
	private final SAXBuilder saxBuilder;

	public XmlParser() {
		saxBuilder = new SAXBuilder(false);
		saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(Document doc) {
		return doc.getContent(elementFilter);
	}

	private <T> T parse(File file, XmlParserOperation<T> operation, T oldValue) throws MojoFailureException {
		try {
			Document doc = saxBuilder.build(file);
			List<Element> elements = getElements(doc);
			return operation.perform(file, elements, oldValue);
		} catch (Exception e) {
			throw new MojoFailureException("Cannot parse file " + file, e);
		}

	}

	public <T> T parse(FileList fileList, XmlParserOperation<T> operation) throws MojoFailureException {
		T returnValue = operation.getInitialValue();
		List<File> files = fileList.getFiles();
		for (File file : files) {
			returnValue = parse(file, operation, returnValue);
		}
		return returnValue;
	}

}
