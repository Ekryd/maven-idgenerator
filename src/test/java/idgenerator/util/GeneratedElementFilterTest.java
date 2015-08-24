package idgenerator.util;

import org.junit.Test;

public class GeneratedElementFilterTest {
    @Test
    public void dummy() {}
//    @Test
//    public void testElementName() {
//        Element element = new Element("elem", Namespace.getNamespace("e", "uri"));
//        assertEquals("e", element.getNamespace().getPrefix());
//        assertEquals("elem", element.getName());
//        assertEquals("e:elem", element.getQualifiedName());
//    }
//
//    @Test
//    public void testMultipleElement() {
//        GeneratedElementFilter generatedElementFilter = new GeneratedElementFilter("(a:elem)|(e:elem)|(xx:elem)");
//        assertEquals(true, generatedElementFilter
//                .matches(new Element("elem", Namespace.getNamespace("e", "uri"))));
//        assertEquals(false, generatedElementFilter.matches(new Element("element", Namespace.getNamespace("e",
//                "uri"))));
//        assertEquals(false, generatedElementFilter
//                .matches(new Element("ele", Namespace.getNamespace("e", "uri"))));
//        assertEquals(false, generatedElementFilter.matches(new Element("elem", Namespace
//                .getNamespace("x", "uri"))));
//    }
//
//    @Test
//    public void testSimpleElement() {
//        GeneratedElementFilter generatedElementFilter = new GeneratedElementFilter("e:elem");
//        assertEquals(true, generatedElementFilter
//                .matches(new Element("elem", Namespace.getNamespace("e", "uri"))));
//        assertEquals(false, generatedElementFilter.matches(new Element("element", Namespace.getNamespace("e",
//                "uri"))));
//        assertEquals(false, generatedElementFilter
//                .matches(new Element("ele", Namespace.getNamespace("e", "uri"))));
//        assertEquals(false, generatedElementFilter.matches(new Element("elem", Namespace
//                .getNamespace("x", "uri"))));
//    }
}
