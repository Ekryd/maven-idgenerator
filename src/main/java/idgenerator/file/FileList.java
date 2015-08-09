package idgenerator.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Finds all files of a certain sort under a directory. The files are filtered
 * to have the same ending
 *
 * @author Björn Ekryd
 */
public class FileList {

    private final File directory;
    private final Collection<File> files = new TreeSet<>();
    private FileFilter fileFilter;

    public FileList(final File directory) {
        this.directory = directory;
    }

    public void findFiles(final FileFilter fileFilter) {
        this.fileFilter = fileFilter;
        files.clear();
        findFiles(directory);
    }

    private void findFiles(final File dir) {
        files.addAll(Arrays.asList(dir.listFiles(fileFilter)));
        File[] children = dir.listFiles(File::isDirectory);
        Arrays.asList(children).forEach(this::findFiles);
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
