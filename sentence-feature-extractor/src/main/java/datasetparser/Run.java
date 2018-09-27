package datasetparser;

import java.util.ArrayList;

import datasetparser.models.Relationship;
import datasetparser.models.RelationshipEntry;
import datasetparser.models.SentenceEntry;

public class Run {

	public static void main(String[] args) throws Exception {

		// Takes sentence entries from xml and save to db
		/*
		DocsentParser docsentParser = new DocsentParser();
		ArrayList<SentenceEntry> sentenceEntries = docsentParser.parseFolder("mds");
		sentenceEntries.addAll(docsentParser.parseFolder("gulfair11"));
		sentenceEntries.addAll(docsentParser.parseFolder("milan9"));

		for(SentenceEntry entry:sentenceEntries){
			entry.save();
		}
		*/

		// Takes relationship entries from xml and save to db
		/*
		JudgedataParser judgedataParser = new JudgedataParser();
		ArrayList<RelationshipEntry> relationshipEntries = judgedataParser.parseFolder("mds");
		relationshipEntries.addAll(judgedataParser.parseFolder("gulfair11"));
		relationshipEntries.addAll(judgedataParser.parseFolder("milan9"));

		for(RelationshipEntry entry:relationshipEntries){
			entry.save();
		}
		*/


		// Take all unique relationship entries, map them with sentences and save in relationship table
		/*
		ArrayList<RelationshipEntry> entries = RelationshipEntry.getAllUnique();
		ArrayList<Relationship> relationships = new ArrayList<>();
		Relationship relationship;
		for(RelationshipEntry entry:entries){
			SentenceEntry sourceEntry = SentenceEntry.getEntry(entry.getSdId(),entry.getSSent());
			SentenceEntry targetEntry = SentenceEntry.getEntry(entry.getTdId(),entry.getTSent());

			relationship = new Relationship();
			relationship.setEntryId(entry.getDbId());
			relationship.setSourceSent(sourceEntry.getSentence());
			relationship.setTargetSent(targetEntry.getSentence());
			relationship.setType(entry.getType());
			relationships.add(relationship);
//			relationship.save();
		}
		*/
	}

}
