package idgenerator.util;

import org.jdom.Element;
import org.jdom.filter.Filter;

import java.util.regex.Pattern;

/**
 * Filters out components that should have ids
 *
 * @author bjorn
 */
public class GeneratedElementFilter implements Filter {
    private static final long serialVersionUID = -8605449116029716276L;
    private final Pattern matchPattern;

    public GeneratedElementFilter(final String regExMatch) {
        super();
        this.matchPattern = Pattern.compile(regExMatch);
    }

    @Override
    public boolean matches(final Object obj) {
        if (obj instanceof Element) {
            Element element = (Element) obj;
            String qualifiedName = element.getQualifiedName();
            if (matchPattern.matcher(qualifiedName).matches()) {
                return true;
            }
        }
        return false;
    }

}
