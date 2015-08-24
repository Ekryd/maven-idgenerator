package idgenerator.xml;

import idgenerator.parser.ParserOperation;
import idgenerator.structure.PluginElement;
import idgenerator.util.GeneratedElementFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns all files that contains elements without ids
 *
 * @author bjorn
 */
public class AddEmptyIdFileOperation implements ParserOperation<List<File>> {
    private final GeneratedElementFilter generatedElementFilter;

    public AddEmptyIdFileOperation(final String regExMatch) {
        generatedElementFilter = new GeneratedElementFilter(regExMatch);
    }

    @Override
    public List<File> getInitialValue() {
        return new ArrayList<>();
    }

    @Override
    public List<File> perform(final File file, final List<PluginElement> elements) {
        boolean containsEmptyIds = containsEmptyIds(elements);
        List<File> fileList= new ArrayList<>();
        if (containsEmptyIds) {
            fileList.add(file);
        }
        return fileList;
    }

    private boolean containsEmptyIds(final List<PluginElement> elements) {
//        for (Element element : elements) {
//            if (generatedElementFilter.matches(element)) {
//                String idValue = element.getAttributeValue("id");
//                if (idValue == null || idValue.isEmpty()) {
//                    return true;
//                }
//            }
//            if (containsEmptyIds(ElementUtil.getElements(element))) {
//                return true;
//            }
//        }
        return false;
    }

}
