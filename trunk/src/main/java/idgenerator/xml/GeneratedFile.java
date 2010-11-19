package idgenerator.xml;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * A changed file that contains generated ids
 * 
 * @author Björn Ekryd
 * 
 */
public class GeneratedFile {

	private final File fileName;
	private final String document;
	private final String encoding;

	public GeneratedFile(File fileName, String document, String encoding) {
		this.fileName = fileName;
		this.document = document;
		this.encoding = encoding;
	}

	/**
	 * @return the fileName
	 */
	public File getFileName() {
		return fileName;
	}

	public void saveFile(Log log) throws MojoFailureException {
		if (!fileName.canWrite()) {
			log.warn(fileName + " is not writeable");
		} else {
			try {
				FileUtils.forceDelete(fileName);
				FileUtils.writeStringToFile(fileName, document, encoding);
			} catch (IOException e) {
				throw new MojoFailureException("Cannot create file " + fileName, e);
			}
		}
	}

	@Override
	public String toString() {
		return fileName.toString() + "\n" + document + "\n";
	}
}
