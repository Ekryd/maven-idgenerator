package idgenerator.xml;

import idgenerator.util.ElementFilter;
import idgenerator.util.GeneratedElementFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	private final GeneratedElementFilter generatedElementFilter = new GeneratedElementFilter();

	public AddEmptyIdFileOperation() {
	}

	private boolean containsEmptyIds(List<Element> elements) {
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
	private List<Element> getElements(Element element) {
		return element.getContent(elementFilter);
	}

	@Override
	public List<File> getInitialValue() {
		return new ArrayList<File>();
	}

	@Override
	public List<File> perform(File file, List<Element> elements, List<File> fileList) {
		boolean containsEmptyIds = containsEmptyIds(elements);
		if (containsEmptyIds) {
			fileList.add(file);
		}
		return fileList;
	}
}
