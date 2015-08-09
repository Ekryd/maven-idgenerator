package idgenerator.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

/**
 * File filter to select files to process
 * 
 * @author Björn Ekryd
 */
public class XmlFileFilter implements FileFilter {
	private final String suffix;

	public XmlFileFilter(String suffix) {
		this.suffix = suffix.toLowerCase(Locale.getDefault());
	}

	@Override
	public boolean accept(File pathname) {
		if (!pathname.isFile()) {
			return false;
		}
		String filename = pathname.getName().toLowerCase(Locale.getDefault());
		return filename.endsWith(suffix);
	}

}
