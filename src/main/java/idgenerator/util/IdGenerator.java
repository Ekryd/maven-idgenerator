package idgenerator.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

/**
 * Handles generation and storage of unique ids
 * 
 * @author Björn Ekryd
 * 
 */
public class IdGenerator {

	private final Map<String, File> idSet = new HashMap<String, File>();
	private final String idPrefix;

	private final Log log;

	public IdGenerator(Log log, String idPrefix) {
		this.log = log;
		this.idPrefix = idPrefix;
	}

	public void addId(File file, String id) {
		idSet.put(id, file);
	}

	public boolean contains(String id) {
		return idSet.containsKey(id);
	}

	public String generateId(File file) {
		long idNumber = 1;
		while (idSet.containsKey(idPrefix + idNumber)) {
			idNumber++;
		}
		idSet.put(idPrefix + idNumber, file);
		return idPrefix + idNumber;
	}

	/**
	 * @return the idSet
	 */
	public Map<String, File> getIdSet() {
		return new HashMap<String, File>(idSet);
	}

	public void outputDuplicateMessage(File file, String id) {
		if (idSet.containsKey(id)) {
			log.error("The files " + file + " and " + idSet.get(id) + " both contain the id " + id);
		}
	}
}
