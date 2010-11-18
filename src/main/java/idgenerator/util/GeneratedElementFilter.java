package idgenerator.util;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Filters out components that should have ids
 * 
 * @author bjorn
 * 
 */
public class GeneratedElementFilter implements Filter {
	private static final long serialVersionUID = -8605449116029716276L;

	@Override
	public boolean matches(Object obj) {
		if (obj instanceof Element) {
			Element element = (Element) obj;
			if (element.getNamespace().getPrefix().equals("h")) {
				if (element.getName().startsWith("graphicImage")) {
					return true;
				}
				if (element.getName().startsWith("select")) {
					return true;
				}
				if (element.getName().equals("form")) {
					return true;
				}
				if (element.getName().startsWith("input")) {
					return true;
				}
				if (element.getName().startsWith("command")) {
					return true;
				}
				if (element.getName().startsWith("output")) {
					return true;
				}
			}
			if (element.getNamespace().getPrefix().equals("f")) {
				if (element.getName().equals("subview")) {
					return true;
				}
			}
		}
		return false;
	}

}
