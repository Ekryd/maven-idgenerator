package idgenerator.xml;

import idgenerator.util.*;

import java.io.File;
import java.util.*;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Returns all files that contains elements without ids
 *
 * @author bjorn
 *
 */
public class AddEmptyIdFileOperation implements XmlParserOperation<List<File>> {
	private final Filter elementFilter = new ElementFilter();
	private final GeneratedElementFilter generatedElementFilter;

	public AddEmptyIdFileOperation(final String regExMatch) {
		generatedElementFilter = new GeneratedElementFilter(regExMatch);
	}

	private boolean containsEmptyIds(final List<Element> elements) {
		for (Element element : elements) {
			if (generatedElementFilter.matches(element)) {
				String idValue = element.getAttributeValue("id");
				if (idValue == null || idValue.isEmpty()) {
					return true;
				}
			}
			if (containsEmptyIds(getElements(element))) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(final Element element) {
		return element.getContent(elementFilter);
	}

	@Override
	public List<File> getInitialValue() {
		return new ArrayList<File>();
	}

	@Override
	public List<File> perform(final File file, final List<Element> elements, final List<File> fileList) {
		boolean containsEmptyIds = containsEmptyIds(elements);
		if (containsEmptyIds) {
			fileList.add(file);
		}
		return fileList;
	}
}
