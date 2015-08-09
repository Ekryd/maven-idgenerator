package idgenerator.xml;

import idgenerator.util.ElementUtil;
import idgenerator.util.GeneratedElementFilter;
import idgenerator.util.IdGenerator;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles modifications of xml files when generating ids
 *
 * @author bjorn
 */
public class XmlModifier {

    private final GeneratedElementFilter generatedElementFilter;
    private final SAXBuilder saxBuilder;
    private final IdGenerator idGenerator;
    private final String encoding;
    private final String indent;
    private final String lineSeparator;

    public XmlModifier(final IdGenerator idGenerator, final String encoding, final String indent,
                       final String lineSeparator, final String regExMatch) {
        this.saxBuilder = new SAXBuilder(false);
        this.saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        this.idGenerator = idGenerator;
        this.encoding = encoding;
        this.indent = indent;
        this.lineSeparator = lineSeparator;
        this.generatedElementFilter = new GeneratedElementFilter(regExMatch);
    }

    private String getXmlString(final Document doc) throws IOException {
        XMLOutputter outputter = new XMLOutputter();
        Format format = Format.getPrettyFormat();
        format.setExpandEmptyElements(false);
        format.setEncoding(encoding);
        format.setLineSeparator(lineSeparator);
        format.setIndent(indent);
        format.setOmitDeclaration(true);
        outputter.setFormat(format);
        StringWriter stringWriter = new StringWriter();
        outputter.output(doc, stringWriter);
        String returnValue = stringWriter.toString();
        stringWriter.close();
        return returnValue;
    }

    private void modifyElements(final File file, final List<Element> elements) {
        for (Element element : elements) {
            if (generatedElementFilter.matches(element)) {
                String idValue = element.getAttributeValue("id");
                if (idValue == null || idValue.isEmpty()) {
                    Attribute attribute = new Attribute("id", idGenerator.generateId(file));
                    ElementUtil.addAttribute(element, attribute);
                }
            }
            modifyElements(file, ElementUtil.getElements(element));
        }
    }

    private GeneratedFile parseFile(final File file) {
        try {
            Document doc = saxBuilder.build(file);
            List<Element> elements = ElementUtil.getElements(doc);
            modifyElements(file, elements);
            return new GeneratedFile(file, getXmlString(doc), encoding);
        } catch (RuntimeException e) {
            System.err.println("XMLFel Fil: " + file + e.getMessage());
            return null;
        } catch (JDOMException e) {
            System.err.println("XMLFel Fil: " + file + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("XMLFel Fil: " + file + e.getMessage());
            return null;
        }
    }

    public List<GeneratedFile> parseFiles(final List<File> filesToModify) {
        ArrayList<GeneratedFile> generatedFiles = new ArrayList<>();
        for (File file : filesToModify) {
            generatedFiles.add(parseFile(file));
        }
        return generatedFiles;
    }
}
