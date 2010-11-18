package idgenerator;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Finds all files of a certain sort under a directory. The files are filtered
 * to have the same ending
 * 
 * @author Björn Ekryd
 * 
 */
public class FileList {

	private final File directory;
	private List<File> files;
	private FileFilter xHtmlFilter;
	private final FileFilter dirFilter = new DirectoryFilter();

	public FileList(File directory) {
		this.directory = directory;
	}

	private void findFiles(File dir) {
		files.addAll(Arrays.asList(dir.listFiles(xHtmlFilter)));
		File[] children = dir.listFiles(dirFilter);
		for (File file : children) {
			findFiles(file);
		}
	}

	public void findFiles(FileFilter xHtmlFilter) {
		this.xHtmlFilter = xHtmlFilter;
		files = new ArrayList<File>();
		findFiles(directory);
	}

	public List<File> getFiles() {
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
