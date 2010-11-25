package idgenerator.util;

import java.util.regex.Pattern;

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
				// if (element.getNamespace().getPrefix().equals("h")) {
				// if
				// (element.getName().staqualifiedNamertsWith("graphicImage")) {
				// return true;
				// }
				// if (element.getName().startsWith("select")) {
				// return true;
				// }
				// if (element.getName().equals("form")) {
				// return true;
				// }
				// if (element.getName().startsWith("input")) {
				// return true;
				// }
				// if (element.getName().startsWith("command")) {
				// return true;
				// }
				// if (element.getName().startsWith("output")) {
				// return true;
				// }
				// }
				// if (element.getNamespace().getPrefix().equals("f")) {
				// if (element.getName().equals("subview")) {
				// return true;
				// }
				// }
				// if (element.getNamespace().getPrefix().equals("a4j")) {
				// if (element.getName().equals("repeat")) {
				// return true;
				// }
				// if (element.getName().equals("commandLink")) {
				// return true;
				// }
				// }
				// if (element.getNamespace().getPrefix().equals("rich")) {
				// if (element.getName().equals("tab")) {
				// return true;
				// }
				// if (element.getName().equals("panel")) {
				// return true;
				// }
				// if (element.getName().equals("calendar")) {
				// return true;
				// }
				// if (element.getName().equals("dataTable")) {
				// return true;
				// }
				// }
				// if (element.getNamespace().getPrefix().equals("pust")) {
				// if (element.getName().equals("datumTid")) {
				// return true;
				// }
				// if (element.getName().equals("selectAnvandare")) {
				// return true;
				// }
				// if (element.getName().equals("datumIntervall")) {
				// return true;
				// }
				// if (element.getName().equals("visaDatum")) {
				// return true;
				// }
				// if (element.getName().equals("ignoreraVarningarDialog")) {
				// return true;
				// }
				// }
			}
		}
		return false;
	}

}
