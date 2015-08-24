package idgenerator.parser;

import idgenerator.file.FileList;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.util.Collection;

/**
 * @author bjorn
 * @since 15-08-23
 */
public interface PluginParser {
        
    default <T> T parse(final FileList fileList, final ParserOperation<T> operation) throws MojoFailureException {
        T returnValue = operation.getInitialValue();
        Collection<File> files = fileList.getFiles();
        for (File file : files) {
            returnValue = parse(file, operation, returnValue);
        }
        return returnValue;
    }

    <T> T parse(final File file, final ParserOperation<T> operation, final T oldValue)
            throws MojoFailureException;
}
