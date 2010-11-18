package idgenerator.xml;

import idgenerator.util.ElementFilter;
import idgenerator.util.IdGenerator;

import java.io.File;
import java.util.List;

import org.jdom.Element;
import org.jdom.filter.Filter;

public class AddIdOperation implements XmlParserOperation<Object> {
	private final IdGenerator idGenerator;
	private final Filter elementFilter = new ElementFilter();

	public AddIdOperation(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(Element element) {
		return element.getContent(elementFilter);
	}

	@Override
	public Object getInitialValue() {
		return null;
	}

	@Override
	public Boolean perform(File file, List<Element> elements, Object dummy) {
		for (Element element : elements) {
			String idValue = element.getAttributeValue("id");
			if (idValue != null && !idValue.isEmpty()) {
				idGenerator.addId(file, idValue);
			}
			perform(file, getElements(element), dummy);
		}
		return null;
	}

}
