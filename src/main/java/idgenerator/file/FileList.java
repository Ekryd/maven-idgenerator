package idgenerator.file;

import java.io.*;
import java.util.*;

/**
 * Finds all files of a certain sort under a directory. The files are filtered
 * to have the same ending
 *
 * @author Björn Ekryd
 *
 */
public class FileList {

	private final File directory;
	private final Collection<File> files = new TreeSet<File>();
	private FileFilter xHtmlFilter;
	private final FileFilter dirFilter = new DirectoryFilter();

	public FileList(final File directory) {
		this.directory = directory;
	}

	private void findFiles(final File dir) {
		files.addAll(Arrays.asList(dir.listFiles(xHtmlFilter)));
		File[] children = dir.listFiles(dirFilter);
		for (File file : children) {
			findFiles(file);
		}
	}

	public void findFiles(final FileFilter xHtmlFilter) {
		this.xHtmlFilter = xHtmlFilter;
		files.clear();
		findFiles(directory);
	}

	public Collection<File> getFiles() {
		return files;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (File file : files) {
			stringBuilder.append(file).append('\n');
		}
		return stringBuilder.toString();
	}
}
