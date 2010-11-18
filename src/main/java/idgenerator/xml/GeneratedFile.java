package idgenerator.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * A changed file that contains generated ids
 * 
 * @author Björn Ekryd
 * 
 */
public class GeneratedFile {

	private final String document;

	private final File fileName;

	public GeneratedFile(File fileName, String document) {
		this.fileName = fileName;
		this.document = document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GeneratedFile other = (GeneratedFile) obj;
		if (fileName == null) {
			if (other.fileName != null) {
				return false;
			}
		} else if (!fileName.equals(other.fileName)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the fileName
	 */
	public File getFileName() {
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	public void saveFile(Log log) throws MojoFailureException {
		if (!fileName.canWrite()) {
			log.warn(fileName + " is not writeable");
		} else {
			try {
				fileName.delete();
				fileName.createNewFile();
				FileWriter fileOutputStream = new FileWriter(fileName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileOutputStream);
				bufferedWriter.write(document);
				bufferedWriter.close();
				fileOutputStream.close();
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
