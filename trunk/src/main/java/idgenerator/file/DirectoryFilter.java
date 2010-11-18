package idgenerator.file;

import java.io.File;
import java.io.FileFilter;

/**
 * Accepts directories only
 * 
 * @author Björn Ekryd
 * 
 */
public class DirectoryFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory();
	}

}
