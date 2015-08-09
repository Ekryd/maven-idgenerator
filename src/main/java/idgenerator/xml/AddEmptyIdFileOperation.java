package idgenerator.xml;

import idgenerator.util.ElementUtil;
import idgenerator.util.GeneratedElementFilter;
import org.jdom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns all files that contains elements without ids
 *
 * @author bjorn
 */
public class AddEmptyIdFileOperation implements XmlParserOperation<List<File>> {
    private final GeneratedElementFilter generatedElementFilter;

    public AddEmptyIdFileOperation(final String regExMatch) {
        generatedElementFilter = new GeneratedElementFilter(regExMatch);
    }

    @Override
    public List<File> getInitialValue() {
        return new ArrayList<>();
    }

    @Override
    public List<File> perform(final File file, final List<Element> elements, final List<File> fileList) {
        boolean containsEmptyIds = containsEmptyIds(elements);
        if (containsEmptyIds) {
            fileList.add(file);
        }
        return fileList;
    }

    private boolean containsEmptyIds(final List<Element> elements) {
        for (Element element : elements) {
            if (generatedElementFilter.matches(element)) {
                String idValue = element.getAttributeValue("id");
                if (idValue == null || idValue.isEmpty()) {
                    return true;
                }
            }
            if (containsEmptyIds(ElementUtil.getElements(element))) {
                return true;
            }
        }
        return false;
    }

}
