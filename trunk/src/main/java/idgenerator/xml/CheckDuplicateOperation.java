package idgenerator.xml;

import idgenerator.util.*;

import java.io.File;
import java.util.List;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Checks for duplicate ids
 *
 * @author bjorn
 *
 */
public class CheckDuplicateOperation implements XmlParserOperation<Boolean> {
	private final IdGenerator idGenerator;
	private final Filter elementFilter = new ElementFilter();
	private final GeneratedElementFilter generatedElementFilter;

	public CheckDuplicateOperation(final IdGenerator idGenerator, final String regExMatch) {
		this.idGenerator = idGenerator;
		generatedElementFilter = new GeneratedElementFilter(regExMatch);
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(final Element element) {
		return element.getContent(elementFilter);
	}

	@Override
	public Boolean getInitialValue() {
		return false;
	}

	@Override
	public Boolean perform(final File file, final List<Element> elements, final Boolean oldValue) {
		boolean containsDuplicates = oldValue;
		for (Element element : elements) {
			String idValue = element.getAttributeValue("id");
			if (idValue != null && !idValue.isEmpty()) {
				if (idGenerator.contains(idValue) && generatedElementFilter.matches(element)) {
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
