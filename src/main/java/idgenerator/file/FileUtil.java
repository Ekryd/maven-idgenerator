package idgenerator.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

/**
 * Utility functions for file handling
 *
 * @author bjorn
 * @since 15-08-09
 */
public class FileUtil {
    
    public static FileList findFiles(File baseDirectory, final String fileSuffix) {
        FileList fileList = new FileList(baseDirectory);
        fileList.findFiles(getFileFilter(fileSuffix));
        return fileList;
    }

    private static FileFilter getFileFilter(String fileSuffix) {
        String fileSuffixLowerCase = fileSuffix.toLowerCase(Locale.getDefault());
        return file -> file.getName().toLowerCase(Locale.getDefault()).endsWith(fileSuffixLowerCase);
    }


}
