package idgenerator.structure;

import java.util.List;

/**
 * @author bjorn
 * @since 15-08-23
 */
public interface PluginElement {
    String getAttribute(String key);
    
    List<PluginElement> getChildElements();

    String getElementName();
}
