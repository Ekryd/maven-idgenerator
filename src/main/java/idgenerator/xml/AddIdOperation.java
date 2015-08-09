package idgenerator.xml;

import idgenerator.util.ElementFilter;
import idgenerator.util.IdGenerator;
import org.jdom.Element;
import org.jdom.filter.Filter;

import java.io.File;
import java.util.List;

/**
 * Collects ids from existing elements
 *
 * @author bjorn
 */
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
    public Object perform(File file, List<Element> elements, Object dummy) {
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
