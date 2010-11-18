package idgenerator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlParser {

	private final Filter elementFilter = getElementFilter();
	private final SAXBuilder saxBuilder;
	private final IdGenerator idGenerator;
	private final Log log;

	public XmlParser(Log log, String idPrefix) {
		saxBuilder = new SAXBuilder(false);
		saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		idGenerator = new IdGenerator(log, idPrefix);
		this.log = log;
	}

	private boolean allowModifyElement(Element element) {
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
		return false;
	}

	private boolean checkForDuplicateIds(File file) throws MojoFailureException {
		try {
			Document doc = saxBuilder.build(file);
			List<Element> elements = doc.getContent(elementFilter);
			return checkForDuplicateIds(file, elements);
		} catch (Exception e) {
			throw new MojoFailureException("Cannot parse file " + file, e);
		}
	}

	private boolean checkForDuplicateIds(File file, List<Element> elements) {
		boolean containsDuplicates = false;
		for (Element element : elements) {
			String idValue = element.getAttributeValue("id");
			if (idValue != null && !idValue.isEmpty()) {
				if (idGenerator.contains(idValue)) {
					idGenerator.outputDuplicateMessage(file, idValue);
					containsDuplicates = true;
				}
				idGenerator.addId(file, idValue);
			}
			containsDuplicates = checkForDuplicateIds(file, element.getContent(elementFilter)) || containsDuplicates;
		}
		return containsDuplicates;
	}

	public void checkForDuplicateIds(FileList xhtmlFiles) throws MojoFailureException {
		boolean containsDuplicates = false;
		List<File> files = xhtmlFiles.getFiles();
		for (File file : files) {
			containsDuplicates = checkForDuplicateIds(file) || containsDuplicates;
		}
		if (containsDuplicates) {
			throw new MojoFailureException("Contains duplicate ids");
		}
	}

	private boolean checkForNoIds(File file) throws MojoFailureException {
		try {
			Document doc = saxBuilder.build(file);
			List<Element> elements = doc.getContent(elementFilter);
			return checkForNoIds(file, elements);
		} catch (Exception e) {
			throw new MojoFailureException("Cannot parse file " + file, e);
		}
	}

	private boolean checkForNoIds(File file, List<Element> elements) {
		boolean containsEmptyIds = false;
		for (Element element : elements) {
			if (allowModifyElement(element)) {
				String idValue = element.getAttributeValue("id");
				if (idValue == null || idValue.isEmpty()) {
					log.error("The file " + file + " contains an element " + element.getNamespace().getPrefix() + ":"
							+ element.getName() + " which doesn't have an id");
					containsEmptyIds = true;
				}
			}
			containsEmptyIds = checkForNoIds(file, element.getContent(elementFilter)) || containsEmptyIds;
		}
		return containsEmptyIds;
	}

	public void checkForNoIds(FileList xhtmlFiles) throws MojoFailureException {
		boolean containsEmptyIds = false;
		List<File> files = xhtmlFiles.getFiles();
		for (File file : files) {
			containsEmptyIds = checkForNoIds(file) || containsEmptyIds;
		}
		if (containsEmptyIds) {
			throw new MojoFailureException("Contains missing ids");
		}
	}

	private void collectIds(File file) {
		try {
			Document doc = saxBuilder.build(file);
			List<Element> elements = doc.getContent(elementFilter);
			collectIds(file, elements);
		} catch (Exception e) {
			System.err.println("XMLFel Fil: " + file + e.getMessage());
		}
	}

	private void collectIds(File file, List<Element> elements) {
		for (Element element : elements) {
			String idValue = element.getAttributeValue("id");
			if (idValue != null && !idValue.isEmpty()) {
				idGenerator.addId(file, idValue);
			}
			collectIds(file, element.getContent(elementFilter));
		}

	}

	private Filter getElementFilter() {
		return new Filter() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean matches(Object obj) {
				return obj instanceof Element;
			}
		};
	}

	private String getXmlString(Document doc) throws IOException {
		XMLOutputter outputter = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setExpandEmptyElements(false);
		format.setEncoding("UTF-8");
		format.setLineSeparator("\n");
		format.setIndent("  ");
		format.setOmitDeclaration(true);
		outputter.setFormat(format);
		StringWriter stringWriter = new StringWriter();
		outputter.output(doc, stringWriter);
		String returnValue = stringWriter.toString();
		stringWriter.close();
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	private boolean modifyElements(File file, List<Element> elements) {
		boolean modified = false;
		for (Element element : elements) {
			if (allowModifyElement(element)) {
				String idValue = element.getAttributeValue("id");
				if (idValue == null || idValue.isEmpty()) {
					element.getAttributes().add(0, new Attribute("id", idGenerator.generateId(file)));
					modified = true;
				}
			}
			modified = modifyElements(file, element.getContent(elementFilter)) || modified;
		}
		return modified;
	}

	@SuppressWarnings("unchecked")
	private XmlValue parseFile(File file) {
		try {
			Document doc = saxBuilder.build(file);
			List<Element> elements = doc.getContent(elementFilter);
			boolean modified = modifyElements(file, elements);
			if (modified) {
				return new XmlValue(getXmlString(doc));
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println("XMLFel Fil: " + file + e.getMessage());
			return null;
		}
	}

	public List<GeneratedFile> parseFiles(FileList xhtmlFiles) {
		ArrayList<GeneratedFile> generatedFiles = new ArrayList<GeneratedFile>();
		List<File> files = xhtmlFiles.getFiles();
		for (File file : files) {
			collectIds(file);
			XmlValue xmlContent = parseFile(file);
			if (xmlContent != null) {
				GeneratedFile generatedFile = new GeneratedFile(file, xmlContent);
				generatedFiles.add(generatedFile);
			}
		}
		return generatedFiles;
	}

}
