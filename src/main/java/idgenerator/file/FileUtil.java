package idgenerator.file;

import java.io.File;

/**
 * Utility functions for file handling
 * @author bjorn
 * @since 15-08-09
 */
public class FileUtil {
    public static FileList findFiles(File baseDirectory, String fileSuffix) {
        FileList fileList = new FileList(baseDirectory);
        fileList.findFiles(new XmlFileFilter(fileSuffix));
        return fileList;
    }


}
