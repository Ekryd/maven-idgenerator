package idgenerator.xml;

import idgenerator.util.*;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Checks for elements that doesn't contain ids
 *
 * @author bjorn
 *
 */
public class CheckEmptyIdOperation implements XmlParserOperation<Boolean> {
	private final Filter elementFilter = new ElementFilter();
	private final GeneratedElementFilter generatedElementFilter;
	private final Log log;

	public CheckEmptyIdOperation(final Log log, final String regExMatch) {
		this.log = log;
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
		boolean containsEmptyIds = oldValue;
		for (Element element : elements) {
			if (generatedElementFilter.matches(element)) {
				String idValue = element.getAttributeValue("id");
				if (idValue == null || idValue.isEmpty()) {
					log.error("The file " + file + " contains an element " + element.getNamespace().getPrefix() + ":"
							+ element.getName() + " which doesn't have an id");
					containsEmptyIds = true;
				}
			}
			containsEmptyIds = perform(file, getElements(element), containsEmptyIds) || containsEmptyIds;
		}
		return containsEmptyIds;
	}
}
