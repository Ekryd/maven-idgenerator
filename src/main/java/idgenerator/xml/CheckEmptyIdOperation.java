package idgenerator.xml;

import idgenerator.logger.MavenLogger;
import idgenerator.util.ElementUtil;
import idgenerator.util.GeneratedElementFilter;
import org.jdom.Element;

import java.io.File;
import java.util.List;

/**
 * Checks for elements that doesn't contain ids
 *
 * @author bjorn
 */
public class CheckEmptyIdOperation implements XmlParserOperation<Boolean> {
    private final GeneratedElementFilter generatedElementFilter;
    private final MavenLogger log;

    public CheckEmptyIdOperation(final MavenLogger log, final String regExMatch) {
        this.log = log;
        generatedElementFilter = new GeneratedElementFilter(regExMatch);
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
            containsEmptyIds = perform(file, ElementUtil.getElements(element), containsEmptyIds) || containsEmptyIds;
        }
        return containsEmptyIds;
    }
}
