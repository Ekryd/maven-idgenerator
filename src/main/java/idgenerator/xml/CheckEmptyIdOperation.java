package idgenerator.xml;

import idgenerator.logger.MavenLogger;
import idgenerator.parser.ParserOperation;
import idgenerator.structure.PluginElement;
import idgenerator.util.GeneratedElementFilter;

import java.io.File;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * Checks for elements that doesn't contain ids
 *
 * @author bjorn
 */
public class CheckEmptyIdOperation implements ParserOperation<Boolean> {
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
    public Boolean perform(final File file, final Stream<PluginElement> elements) {
        return elements
                .filter(generatedElementFilter::matches)
                .filter(this::checkEmptyId)
                .peek(e -> logError(file, e))
                .reduce((e1, e2) -> e1)
                .isPresent();
    }

    private void logError(final File file, final PluginElement element) {
        log.error(String.format("The file %s contains an element %s which doesn't have an id", 
                file.getPath(), element.getElementName()));

    }

    private boolean checkEmptyId(PluginElement element) {
        String idValue = element.getAttribute("id");
        return idValue == null || idValue.isEmpty();
    }

    @Override
    public BinaryOperator<Boolean> getAccumulator() {
        return (b1, b2) -> b1 || b2;
    }
}
