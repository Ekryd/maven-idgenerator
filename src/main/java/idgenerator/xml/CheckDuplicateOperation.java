package idgenerator.xml;

import idgenerator.util.ElementFilter;
import idgenerator.util.IdGenerator;

import java.io.File;
import java.util.List;

import org.jdom.Element;
import org.jdom.filter.Filter;

public class CheckDuplicateOperation implements XmlParserOperation<Boolean> {
	private final IdGenerator idGenerator;
	private final Filter elementFilter = new ElementFilter();

	public CheckDuplicateOperation(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(Element element) {
		return element.getContent(elementFilter);
	}

	@Override
	public Boolean getInitialValue() {
		return false;
	}

	@Override
	public Boolean perform(File file, List<Element> elements, Boolean oldValue) {
		boolean containsDuplicates = oldValue;
		for (Element element : elements) {
			String idValue = element.getAttributeValue("id");
			if (idValue != null && !idValue.isEmpty()) {
				if (idGenerator.contains(idValue)) {
					idGenerator.outputDuplicateMessage(file, idValue);
					containsDuplicates = true;
				}
				idGenerator.addId(file, idValue);
			}
			containsDuplicates = perform(file, getElements(element), containsDuplicates) || containsDuplicates;
		}
		return containsDuplicates;
	}
}
