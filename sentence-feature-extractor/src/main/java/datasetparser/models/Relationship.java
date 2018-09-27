package datasetparser.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.SQLiteUtils;

public class Relationship {

	private int dbId;
	private int entryId;
	private String sourceSent;
	private String targetSent;
	private int type;

	private static SQLiteUtils sqLiteUtils;

	static {
		try {
			sqLiteUtils = new SQLiteUtils();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getDbId() {
		return dbId;
	}

	public int getEntryId() {
		return entryId;
	}

	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	public String getSourceSent() {
		return sourceSent;
	}

	public void setSourceSent(String sourceSent) {
		this.sourceSent = sourceSent;
	}

	public String getTargetSent() {
		return targetSent;
	}

	public void setTargetSent(String targetSent) {
		this.targetSent = targetSent;
	}

	public int getRelation() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void save() throws SQLException {
		String sql = "INSERT INTO RELATIONSHIP (ENTRYID,SSENT,TSENT,TYPE) " +
				"VALUES ("
				+ entryId + ", " +
				"\"" + sourceSent + "\"" + ", " +
				"\"" + targetSent + "\"" + ", " +
				type + ");";
		System.out.println(sql);
		sqLiteUtils.executeUpdate(sql);
	}

	public static ArrayList<Relationship> getAll() throws SQLException {
		String sql = "SELECT * FROM RELATIONSHIP;";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		Relationship entry;
		while (resultSet.next()){
			entry = new Relationship();
			entry.dbId = resultSet.getInt("ID");
			entry.entryId = resultSet.getInt("ENTRYID");
			entry.sourceSent = resultSet.getString("SSENT");
			entry.targetSent = resultSet.getString("TSENT");
			entry.type = resultSet.getInt("TYPE");

			relationships.add(entry);
		}

		return relationships;
	}

	public static ArrayList<Relationship> getAllLegal() throws SQLException {
		String sql = "SELECT * FROM LEGAL_TEXTS;";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		Relationship entry;
		while (resultSet.next()){
			entry = new Relationship();
			entry.dbId = resultSet.getInt("ID");
			entry.sourceSent = resultSet.getString("SSENT");
			entry.targetSent = resultSet.getString("TSENT");

			relationships.add(entry);
		}

		return relationships;
	}
}
