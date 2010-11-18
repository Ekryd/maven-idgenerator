package idgenerator.xml;

/**
 * Contains either
 * 
 * @author bjorn
 * 
 */
public class XmlValue {

	private final boolean modifiedXml;
	private final String string;

	public XmlValue(boolean modifiedXml) {
		this.modifiedXml = modifiedXml;
		this.string = null;
	}

	public XmlValue(String string) {
		this.modifiedXml = true;
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public boolean isModifiedXml() {
		return modifiedXml;
	}

	@Override
	public String toString() {
		if (!modifiedXml) {
			return "<no change>";
		}
		return string;
	}
}
