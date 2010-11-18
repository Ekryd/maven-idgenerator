package idgenerator.xml;

import idgenerator.util.ElementFilter;
import idgenerator.util.GeneratedElementFilter;
import idgenerator.util.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Handles modifications of xml files when generating ids
 * 
 * @author bjorn
 * 
 */
public class XmlModifier {

	private final Filter elementFilter = new ElementFilter();
	private final GeneratedElementFilter generatedElementFilter = new GeneratedElementFilter();
	private final SAXBuilder saxBuilder;
	private final IdGenerator idGenerator;

	public XmlModifier(IdGenerator idGenerator) {
		saxBuilder = new SAXBuilder(false);
		saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		this.idGenerator = idGenerator;
	}

	@SuppressWarnings("unchecked")
	private void addAttribute(Element element, Attribute attribute) {
		element.getAttributes().add(0, attribute);
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(Document doc) {
		return doc.getContent(elementFilter);
	}

	@SuppressWarnings("unchecked")
	private List<Element> getElements(Element element) {
		return element.getContent(elementFilter);
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

	private void modifyElements(File file, List<Element> elements) {
		for (Element element : elements) {
			if (generatedElementFilter.matches(element)) {
				String idValue = element.getAttributeValue("id");
				if (idValue == null || idValue.isEmpty()) {
					Attribute attribute = new Attribute("id", idGenerator.generateId(file));
					addAttribute(element, attribute);
				}
			}
			modifyElements(file, getElements(element));
		}
	}

	private GeneratedFile parseFile(File file) {
		try {
			Document doc = saxBuilder.build(file);
			List<Element> elements = getElements(doc);
			modifyElements(file, elements);
			return new GeneratedFile(file, getXmlString(doc));
		} catch (Exception e) {
			System.err.println("XMLFel Fil: " + file + e.getMessage());
			return null;
		}
	}

	public List<GeneratedFile> parseFiles(List<File> filesToModify) {
		ArrayList<GeneratedFile> generatedFiles = new ArrayList<GeneratedFile>();
		for (File file : filesToModify) {
			generatedFiles.add(parseFile(file));
		}
		return generatedFiles;
	}
}
