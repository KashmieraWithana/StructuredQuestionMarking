package datasetparser.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.SQLiteUtils;

public class SentenceEntry {

	private String sentence;
	private int sentenceNo;
	private String documentId;
	private String source;
	private int dbId;

	private static SQLiteUtils sqLiteUtils;

	static {
		try {
			sqLiteUtils = new SQLiteUtils();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		sentence = sentence.trim();
		String[] arr = sentence.split("^[0-9]+");

		if (arr.length == 2) {
			sentence = arr[1].trim();
		}
		sentence = sentence.replaceAll("\"", "'");
		this.sentence = sentence;
	}

	public int getSentenceNo() {
		return sentenceNo;
	}

	public void setSentenceNo(int sentenceNo) {
		this.sentenceNo = sentenceNo;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getDbId() {
		return dbId;
	}

	public void save() throws SQLException {
		String sql = "INSERT INTO SENTENCE_ENTRY (SNO,SENT,DID,SOURCE) " +
				"VALUES ("
				+ sentenceNo + ", " +
				"\"" + sentence + "\"" + ", " +
				"'" + documentId + "'" + ", " +
				"'" + source + "'" + ");";
		System.out.println(sql);
		sqLiteUtils.executeUpdate(sql);
	}

	public static SentenceEntry getEntry(String documentId, int sentenceNo) throws SQLException {
		String sql = "SELECT * FROM SENTENCE_ENTRY WHERE SNO=" + sentenceNo + " AND " + "DID='" + documentId + "';";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		SentenceEntry sentenceEntry = null;

		if(!resultSet.isClosed()){
			sentenceEntry = new SentenceEntry();
			sentenceEntry.dbId = resultSet.getInt("ID");
			sentenceEntry.documentId = resultSet.getString("DID");
			sentenceEntry.sentenceNo = resultSet.getInt("SNO");
			sentenceEntry.sentence = resultSet.getString("SENT");
			sentenceEntry.source = resultSet.getString("SOURCE");
		}

		return sentenceEntry;
	}
}
