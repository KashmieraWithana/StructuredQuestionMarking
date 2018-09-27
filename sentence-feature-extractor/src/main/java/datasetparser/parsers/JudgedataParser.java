package datasetparser.parsers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

import datasetparser.models.RelationshipEntry;

public class JudgedataParser {

	/**
	 * Parses all files in the given folder and returns all relationship entries
	 *
	 * @param folderName
	 * @return arraylist of relationship entries
	 * @throws JAXBException
	 */
	public ArrayList<RelationshipEntry> parseFolder(String folderName) throws JAXBException {

		String folderPath = new File("").getAbsolutePath();
		folderPath += "/src/main/resources/judgedata/" + folderName;

		JAXBContext context = JAXBContext.newInstance(RelationshipEntry.Table.class);

		File[] files = new File(folderPath).listFiles();

		ArrayList<RelationshipEntry> entries = new ArrayList<RelationshipEntry>();

		for (File file : files) {
			Unmarshaller um = context.createUnmarshaller();
			RelationshipEntry.Table table = (RelationshipEntry.Table) um.unmarshal(file);
			entries.addAll(table.getRelationshipEntries());
		}

		for (RelationshipEntry entry:entries) {
			entry.setSource(folderName);
		}

		return entries;
	}
}
