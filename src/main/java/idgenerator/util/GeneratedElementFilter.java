package idgenerator.util;

import idgenerator.structure.PluginElement;

import java.util.regex.Pattern;

/**
 * Filters out components that should have ids
 *
 * @author bjorn
 */
public class GeneratedElementFilter {
    private final Pattern matchPattern;

    public GeneratedElementFilter(final String regExMatch) {
        super();
        this.matchPattern = Pattern.compile(regExMatch.toLowerCase());
    }

    public boolean matches(final PluginElement element) {
        String qualifiedName = element.getElementName();
        return matchPattern.matcher(qualifiedName).matches();
    }

}
