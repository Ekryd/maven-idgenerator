package idgenerator.parser;


import idgenerator.structure.PluginElement;

import java.io.File;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Defines an operation that can be performed when traversing an xml file
 */
public interface ParserOperation<T> {
    T getInitialValue();

    T perform(File file, List<PluginElement> elements);
    
    BinaryOperator<T> getAccumulator();
}
