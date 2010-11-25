package idgenerator.util;

import junit.framework.Assert;

import org.jdom.*;
import org.junit.Test;

public class GeneratedElementFilterTest {
	@Test
	public void testElementName() {
		Element element = new Element("elem", Namespace.getNamespace("e", "uri"));
		Assert.assertEquals("e", element.getNamespace().getPrefix());
		Assert.assertEquals("elem", element.getName());
		Assert.assertEquals("e:elem", element.getQualifiedName());
	}

	@Test
	public void testMultipleElement() {
		GeneratedElementFilter generatedElementFilter = new GeneratedElementFilter("(a:elem)|(e:elem)|(xx:elem)");
		Assert.assertEquals(true, generatedElementFilter
				.matches(new Element("elem", Namespace.getNamespace("e", "uri"))));
		Assert.assertEquals(false, generatedElementFilter.matches(new Element("element", Namespace.getNamespace("e",
				"uri"))));
		Assert.assertEquals(false, generatedElementFilter
				.matches(new Element("ele", Namespace.getNamespace("e", "uri"))));
		Assert.assertEquals(false, generatedElementFilter.matches(new Element("elem", Namespace
				.getNamespace("x", "uri"))));
	}

	@Test
	public void testSimpleElement() {
		GeneratedElementFilter generatedElementFilter = new GeneratedElementFilter("e:elem");
		Assert.assertEquals(true, generatedElementFilter
				.matches(new Element("elem", Namespace.getNamespace("e", "uri"))));
		Assert.assertEquals(false, generatedElementFilter.matches(new Element("element", Namespace.getNamespace("e",
				"uri"))));
		Assert.assertEquals(false, generatedElementFilter
				.matches(new Element("ele", Namespace.getNamespace("e", "uri"))));
		Assert.assertEquals(false, generatedElementFilter.matches(new Element("elem", Namespace
				.getNamespace("x", "uri"))));
	}
}
