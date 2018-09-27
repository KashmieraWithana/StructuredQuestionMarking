package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteUtils {

	private static Connection connection = null;

	public SQLiteUtils() throws SQLException {
		if(connection==null){
			connection = DriverManager.getConnection("jdbc:sqlite:/media/gathika/Other/repos/LegalDisourseRelationParser/extractorDatabase.db");
		}
	}

	public Connection getConnection() throws SQLException {
		return connection;
	}

	/**
	 * Inserts, Updates..
	 *
	 * @param sql
	 * @throws SQLException
	 */
	public void executeUpdate(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	/**
	 * Selects ONLY
	 *
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql);
	}



	// TABLE CREATION SQLs
	/*
	String sql = "CREATE TABLE RELATIONSHIP_ENTRY " +
			"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
			" SDID      CHAR(2)     NOT NULL, " +
			" SSENT     INT         NOT NULL, " +
			" TDID      CHAR(2)     NOT NULL, " +
			" TSENT     INT         NOT NULL, " +
			" TYPE      INT         NOT NULL, " +
			" JUDGE     CHAR(1)     NOT NULL, " +
			" SOURCE    CHAR(20)    NOT NULL )";

	String sql = "CREATE TABLE SENTENCE_ENTRY " +
				"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				" SNO       INT     NOT NULL, " +
				" SENT      TEXT        NOT NULL, " +
				" DID       CHAR(2)     NOT NULL, " +
				" SOURCE    CHAR(20)    NOT NULL )";

	String sql = "CREATE TABLE RELATIONSHIP " +
		"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
		" ENTRYID   INT     NOT NULL," +
		" SSENT     TEXT     NOT NULL, " +
		" TSENT     TEXT    NOT NULL, " +
		" TYPE      INT     NOT NULL, )";

	CREATE TABLE FEATURE_ENTRY (ID INTEGER PRIMARY KEY AUTOINCREMENT, RELATIONSHIP_ID INT NOT NULL, TYPE INT NOT NULL, ADJECTIVE_SIMI REAL NOT NULL, NOUN_SIMI REAL NOT NULL, VERB_SIMI REAL NOT NULL, WORD_SIMI REAL NOT NULL, WOVERLAP_S REAL NOT NULL, WOVERLAP_T REAL NOT NULL, ETRANSITION REAL NOT NULL, CTRANSITION REAL NOT NULL, LCS REAL NOT NULL, SOVERLAP REAL NOT NULL, OOVERLAP REAL NOT NULL, SNOVERLAP REAL NOT NULL, NER_RATIO REAL NOT NULL, LENGTH_RATIO REAL NOT NULL, TOS_SCORE INT NOT NULL, SEMANTIC_SCORE REAL NOT NULL);
	*/

}
