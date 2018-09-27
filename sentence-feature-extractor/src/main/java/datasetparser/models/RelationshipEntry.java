package datasetparser.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLiteUtils;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "R")
public class RelationshipEntry {

	private String source;
	private int dbId;
	private int type;
	private String judge;

	@XmlAttribute(name = "SDID")
	private String sdId;

	@XmlAttribute(name = "SSENT")
	private int sSent;

	@XmlAttribute(name = "TDID")
	private String tdId;

	@XmlAttribute(name = "TSENT")
	private int tSent;

	@XmlElement(name = "RELATION")
	private List<Relation> relations;

	public String getSdId() {
		return sdId;
	}

	public int getSSent() {
		return sSent;
	}

	public String getTdId() {
		return tdId;
	}

	public int getTSent() {
		return tSent;
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

	public int getType() {
		return type;
	}

	public String getJudge() {
		return judge;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name = "TABLE")
	public static class Table {

		@XmlElement(name = "R")
		private ArrayList<RelationshipEntry> relationshipEntries;

		public ArrayList<RelationshipEntry> getRelationshipEntries() {
			return relationshipEntries;
		}
	}

	public void save() throws SQLException {
		SQLiteUtils sqLiteUtils = new SQLiteUtils();
		for (Relation relation : relations) {
			String sql = "INSERT INTO RELATIONSHIP_ENTRY (SDID,SSENT,TDID,TSENT,TYPE,JUDGE,SOURCE) " +
					"VALUES ("
					+ "'" + sdId + "'" + ", " +
					sSent + ", " +
					"'" + tdId + "'" + ", " +
					tSent + ", " +
					relation.getType() + ", " +
					"'" + relation.getJudge() + "'" + ", " +
					"'" + source + "'" + ");";
			System.out.println(sql);
			sqLiteUtils.executeUpdate(sql);
		}
	}

	public static ArrayList<RelationshipEntry> getAll() throws SQLException {
		SQLiteUtils sqLiteUtils = new SQLiteUtils();
		String sql = "SELECT * FROM RELATIONSHIP_ENTRY;";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		ArrayList<RelationshipEntry> relationshipEntries = new ArrayList<RelationshipEntry>();
		RelationshipEntry entry;
		while (resultSet.next()){
			entry = new RelationshipEntry();
			entry.dbId = resultSet.getInt("ID");
			entry.sdId = resultSet.getString("SDID");
			entry.sSent = resultSet.getInt("SSENT");
			entry.tdId = resultSet.getString("TDID");
			entry.tSent = resultSet.getInt("TSENT");
			entry.type = resultSet.getInt("TYPE");
			entry.judge = resultSet.getString("JUDGE");
			entry.source = resultSet.getString("SOURCE");

			relationshipEntries.add(entry);
		}

		return relationshipEntries;
	}

	public static ArrayList<RelationshipEntry> getAllUnique() throws SQLException {
		SQLiteUtils sqLiteUtils = new SQLiteUtils();
		String sql = "SELECT * FROM RELATIONSHIP_ENTRY GROUP BY SDID,SSENT,TDID,TSENT,TYPE,SOURCE;";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		ArrayList<RelationshipEntry> relationshipEntries = new ArrayList<RelationshipEntry>();
		RelationshipEntry entry;
		while (resultSet.next()){
			entry = new RelationshipEntry();
			entry.dbId = resultSet.getInt("ID");
			entry.sdId = resultSet.getString("SDID");
			entry.sSent = resultSet.getInt("SSENT");
			entry.tdId = resultSet.getString("TDID");
			entry.tSent = resultSet.getInt("TSENT");
			entry.type = resultSet.getInt("TYPE");
			entry.judge = resultSet.getString("JUDGE");
			entry.source = resultSet.getString("SOURCE");

			relationshipEntries.add(entry);
		}

		return relationshipEntries;
	}

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RELATION")
class Relation {

	@XmlAttribute(name = "TYPE")
	private int type;

	@XmlAttribute(name = "JUDGE")
	private String judge;

	public int getType() {
		return type;
	}

	public String getJudge() {
		return judge;
	}
}
