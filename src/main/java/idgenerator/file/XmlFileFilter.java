package idgenerator.file;

import java.io.File;
import java.io.FileFilter;

/**
 * File filter to select files to process
 * 
 * @author Björn Ekryd
 */
public class XmlFileFilter implements FileFilter {
	private final String suffix;

	public XmlFileFilter(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public boolean accept(File pathname) {
		if (!pathname.isFile()) {
			return false;
		}
		String filename = pathname.getName().toLowerCase();
		return filename.endsWith(suffix);
	}

}
