package idgenerator.xml;

import idgenerator.parser.ParserOperation;
import idgenerator.structure.PluginElement;
import idgenerator.util.GeneratedElementFilter;
import idgenerator.util.IdGenerator;

import java.io.File;
import java.util.List;

/**
 * Checks for duplicate ids
 *
 * @author bjorn
 */
public class CheckDuplicateOperation implements ParserOperation<Boolean> {
    private final IdGenerator idGenerator;
    private final GeneratedElementFilter generatedElementFilter;
    private final boolean onlyDuplicateGeneratedIds;
    private final String idPrefix;

    public CheckDuplicateOperation(final IdGenerator idGenerator, final String regExMatch,
                                   boolean onlyDuplicateGeneratedIds, String idPrefix) {
        this.idGenerator = idGenerator;
        generatedElementFilter = new GeneratedElementFilter(regExMatch);
        this.onlyDuplicateGeneratedIds = onlyDuplicateGeneratedIds;
        this.idPrefix = idPrefix;
    }
    
    @Override
    public Boolean getInitialValue() {
        return false;
    }

    @Override
    public Boolean perform(final File file, final List<PluginElement> elements) {
        boolean containsDuplicates = false;
//        for (Element element : elements) {
//            String idValue = element.getAttributeValue("id");
//            if (idValue != null && !idValue.isEmpty()) {
//                if (idGenerator.contains(idValue) && generatedElementFilter.matches(element)) {
//                    if (!onlyDuplicateGeneratedIds || idValue.startsWith(idPrefix)) {
//                        idGenerator.outputDuplicateMessage(file, idValue);
//                        containsDuplicates = true;
//                    }
//                }
//                idGenerator.addId(file, idValue);
//            }
//            containsDuplicates = perform(file, ElementUtil.getElements(element), containsDuplicates) || containsDuplicates;
//        }
        return containsDuplicates;
    }
}
