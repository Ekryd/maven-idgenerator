package idgenerator.util;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Filters out Element objects
 * 
 * @author bjorn
 * 
 */
public final class ElementFilter implements Filter {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean matches(Object obj) {
		return obj instanceof Element;
	}
}
