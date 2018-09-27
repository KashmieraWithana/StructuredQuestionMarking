package datasetparser.models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.SQLiteUtils;

public class FeatureEntry implements Serializable {

	public static final int FEATURE_NO = 16;

	private int dbId;
	private int relationshipId;
	private int type;
	private int ssid;
	private int tsid;

	private double adjectiveSimilarity;
	private double nounSimilarity;
	private double verbSimilarity;
	private double wordSimilarity;

	private double wordOverlapSSent;
	private double wordOverlapTSent;

	private double ellaborationTransitionScore;
	private double changeTransitionScore;

	private double lcs;

	private double subjectOverlap;
	private double objectOverlap;
	private double subjectNounOverlap;

	private double nerRatio;
	private double lengthRatio;
	private int tosScore;

	private double semanticSimilarityScore;

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

	public double getEllaborationTransitionScore() {
		return ellaborationTransitionScore;
	}

	public double getChangeTransitionScore() {
		return changeTransitionScore;
	}

	public int getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(int relationshipId) {
		this.relationshipId = relationshipId;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

	public int getTsid() {
		return tsid;
	}

	public void setTsid(int tsid) {
		this.tsid = tsid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAdjectiveSimilarity() {
		return adjectiveSimilarity;
	}

	public void setEllaborationTransitionScore(double ellaborationTransitionScore) {
		this.ellaborationTransitionScore = ellaborationTransitionScore;
	}

	public void setChangeTransitionScore(double changeTransitionScore) {
		this.changeTransitionScore = changeTransitionScore;
	}

	public void setAdjectiveSimilarity(double adjectiveSimilarity) {
		this.adjectiveSimilarity = adjectiveSimilarity;
	}

	public double getNounSimilarity() {
		return nounSimilarity;
	}

	public void setNounSimilarity(double nounSimilarity) {
		this.nounSimilarity = nounSimilarity;
	}

	public double getVerbSimilarity() {
		return verbSimilarity;
	}

	public void setVerbSimilarity(double verbSimilarity) {
		this.verbSimilarity = verbSimilarity;
	}

	public double getWordSimilarity() {
		return wordSimilarity;
	}

	public void setWordSimilarity(double wordSimilarity) {
		this.wordSimilarity = wordSimilarity;
	}

	public double getWordOverlapSSent() {
		return wordOverlapSSent;
	}

	public void setWordOverlapSSent(double wordOverlapSSent) {
		this.wordOverlapSSent = wordOverlapSSent;
	}

	public double getWordOverlapTSent() {
		return wordOverlapTSent;
	}

	public void setWordOverlapTSent(double wordOverlapTSent) {
		this.wordOverlapTSent = wordOverlapTSent;
	}

	public double getLcs() {
		return lcs;
	}

	public void setLcs(double lcs) {
		this.lcs = lcs;
	}

	public double getSubjectOverlap() {
		return subjectOverlap;
	}

	public void setSubjectOverlap(double subjectOverlap) {
		this.subjectOverlap = subjectOverlap;
	}

	public double getObjectOverlap() {
		return objectOverlap;
	}

	public void setObjectOverlap(double objectOverlap) {
		this.objectOverlap = objectOverlap;
	}

	public double getSubjectNounOverlap() {
		return subjectNounOverlap;
	}

	public void setSubjectNounOverlap(double subjectNounOverlap) {
		this.subjectNounOverlap = subjectNounOverlap;
	}

	public double getNerRatio() {
		return nerRatio;
	}

	public void setNerRatio(double nerRatio) {
		this.nerRatio = nerRatio;
	}

	public double getLengthRatio() {
		return lengthRatio;
	}

	public void setLengthRatio(double lengthRatio) {
		this.lengthRatio = lengthRatio;
	}

	public int getTosScore() {
		return tosScore;
	}

	public void setTosScore(int tosScore) {
		this.tosScore = tosScore;
	}

	public double getSemanticSimilarityScore() {
		return semanticSimilarityScore;
	}

	public void setSemanticSimilarityScore(double semanticSimilarityScore) {
		this.semanticSimilarityScore = semanticSimilarityScore;
	}

	public void save() throws SQLException {
		String sql = "INSERT INTO FEATURE_ENTRY "
				+ "(RELATIONSHIP_ID,"
				+ "TYPE,"
				+ "ADJECTIVE_SIMI,"
				+ "NOUN_SIMI,"
				+ "VERB_SIMI,"
				+ "WORD_SIMI,"
				+ "WOVERLAP_S,"
				+ "WOVERLAP_T,"
				+ "ETRANSITION,"
				+ "CTRANSITION,"
				+ "LCS,"
				+ "SOVERLAP,"
				+ "OOVERLAP,"
				+ "SNOVERLAP,"
				+ "NER_RATIO,"
				+ "LENGTH_RATIO,"
				+ "TOS_SCORE,"
				+ "SEMANTIC_SCORE) " +
				"VALUES ("
				+ relationshipId + ", " +
				+ type + ", " +
				+ adjectiveSimilarity + ", " +
				+ nounSimilarity + ", " +
				+ verbSimilarity + ", " +
				+ wordSimilarity + ", " +
				+ wordOverlapSSent + ", " +
				+ wordOverlapTSent + ", " +
				+ ellaborationTransitionScore + ", " +
				+ changeTransitionScore + ", " +
				+ lcs + ", " +
				+ subjectOverlap + ", " +
				+ objectOverlap + ", " +
				+ subjectNounOverlap + ", " +
				+ nerRatio + ", " +
				+ lengthRatio + ", " +
				+ tosScore + ", " +
				semanticSimilarityScore + ");";
		System.out.println(sql);
		sqLiteUtils.executeUpdate(sql);
	}

	public static ArrayList<FeatureEntry> getAll() throws SQLException {
		String sql = "SELECT * FROM FEATURE_ENTRY;";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		ArrayList<FeatureEntry> featureEntries = new ArrayList<>();
		FeatureEntry entry;
		while (resultSet.next()){
			entry = new FeatureEntry();
			entry.dbId = resultSet.getInt("ID");
			entry.relationshipId = resultSet.getInt("RELATIONSHIP_ID");
			entry.type = resultSet.getInt("TYPE");
			entry.adjectiveSimilarity = resultSet.getDouble("ADJECTIVE_SIMI");
			entry.nounSimilarity = resultSet.getDouble("NOUN_SIMI");
			entry.verbSimilarity = resultSet.getDouble("VERB_SIMI");
			entry.wordSimilarity = resultSet.getDouble("WORD_SIMI");
			entry.wordOverlapSSent = resultSet.getDouble("WOVERLAP_S");
			entry.wordOverlapTSent = resultSet.getDouble("WOVERLAP_T");
			entry.ellaborationTransitionScore = resultSet.getDouble("ETRANSITION");
			entry.changeTransitionScore = resultSet.getDouble("CTRANSITION");
			entry.lcs = resultSet.getDouble("LCS");
			entry.subjectOverlap = resultSet.getDouble("SOVERLAP");
			entry.objectOverlap = resultSet.getDouble("OOVERLAP");
			entry.subjectNounOverlap = resultSet.getDouble("SNOVERLAP");
			entry.nerRatio = resultSet.getDouble("NER_RATIO");
			entry.lengthRatio = resultSet.getDouble("LENGTH_RATIO");
			entry.tosScore = resultSet.getInt("TOS_SCORE");
			entry.semanticSimilarityScore = resultSet.getDouble("SEMANTIC_SCORE");

			featureEntries.add(entry);
		}

		return featureEntries;
	}

	public static FeatureEntry getFeatureEntryLegal(int id) throws Exception{
		String sql = "SELECT * FROM FEATURE_ENTRY_LEGAL_SENTENCE WHERE ID="+id+";";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		FeatureEntry entry = new FeatureEntry();
		while (resultSet.next()){
			entry.dbId = resultSet.getInt("ID");
			entry.ssid = resultSet.getInt("SSID");
			entry.tsid = resultSet.getInt("TSID");
			entry.type = resultSet.getInt("TYPE");
			entry.adjectiveSimilarity = resultSet.getDouble("ADJECTIVE_SIMI");
			entry.nounSimilarity = resultSet.getDouble("NOUN_SIMI");
			entry.verbSimilarity = resultSet.getDouble("VERB_SIMI");
			entry.wordSimilarity = resultSet.getDouble("WORD_SIMI");
			entry.wordOverlapSSent = resultSet.getDouble("WOVERLAP_S");
			entry.wordOverlapTSent = resultSet.getDouble("WOVERLAP_T");
			entry.ellaborationTransitionScore = resultSet.getDouble("ETRANSITION");
			entry.changeTransitionScore = resultSet.getDouble("CTRANSITION");
			entry.lcs = resultSet.getDouble("LCS");
			entry.subjectOverlap = resultSet.getDouble("SOVERLAP");
			entry.objectOverlap = resultSet.getDouble("OOVERLAP");
			entry.subjectNounOverlap = resultSet.getDouble("SNOVERLAP");
			entry.nerRatio = resultSet.getDouble("NER_RATIO");
			entry.lengthRatio = resultSet.getDouble("LENGTH_RATIO");
			entry.tosScore = resultSet.getInt("TOS_SCORE");
			entry.semanticSimilarityScore = resultSet.getDouble("SEMANTIC_SCORE");
		}

		return entry;
	}

	public void saveLegal() throws SQLException {
		String sql = "INSERT INTO FEATURE_ENTRY_LEGAL_SENTENCE "
				+ "(SSID,"
				+ "TSID,"
				+ "TYPE,"
				+ "ADJECTIVE_SIMI,"
				+ "NOUN_SIMI,"
				+ "VERB_SIMI,"
				+ "WORD_SIMI,"
				+ "WOVERLAP_S,"
				+ "WOVERLAP_T,"
				+ "ETRANSITION,"
				+ "CTRANSITION,"
				+ "LCS,"
				+ "SOVERLAP,"
				+ "OOVERLAP,"
				+ "SNOVERLAP,"
				+ "NER_RATIO,"
				+ "LENGTH_RATIO,"
				+ "TOS_SCORE,"
				+ "SEMANTIC_SCORE) " +
				"VALUES ("
				+ ssid + ", " +
				+ tsid + ", " +
				+ type + ", " +
				+ adjectiveSimilarity + ", " +
				+ nounSimilarity + ", " +
				+ verbSimilarity + ", " +
				+ wordSimilarity + ", " +
				+ wordOverlapSSent + ", " +
				+ wordOverlapTSent + ", " +
				+ ellaborationTransitionScore + ", " +
				+ changeTransitionScore + ", " +
				+ lcs + ", " +
				+ subjectOverlap + ", " +
				+ objectOverlap + ", " +
				+ subjectNounOverlap + ", " +
				+ nerRatio + ", " +
				+ lengthRatio + ", " +
				+ tosScore + ", " +
				semanticSimilarityScore + ");";
		System.out.println(sql);
		sqLiteUtils.executeUpdate(sql);
	}

	public void saveWiki() throws SQLException {
		String sql = "INSERT INTO FEATURE_ENTRY_WIKIPEDIA_SENTENCE "
				+ "(SSID,"
				+ "TSID,"
				+ "TYPE,"
				+ "ADJECTIVE_SIMI,"
				+ "NOUN_SIMI,"
				+ "VERB_SIMI,"
				+ "WORD_SIMI,"
				+ "WOVERLAP_S,"
				+ "WOVERLAP_T,"
				+ "ETRANSITION,"
				+ "CTRANSITION,"
				+ "LCS,"
				+ "SOVERLAP,"
				+ "OOVERLAP,"
				+ "SNOVERLAP,"
				+ "NER_RATIO,"
				+ "LENGTH_RATIO,"
				+ "TOS_SCORE,"
				+ "SEMANTIC_SCORE) " +
				"VALUES ("
				+ ssid + ", " +
				+ tsid + ", " +
				+ type + ", " +
				+ adjectiveSimilarity + ", " +
				+ nounSimilarity + ", " +
				+ verbSimilarity + ", " +
				+ wordSimilarity + ", " +
				+ wordOverlapSSent + ", " +
				+ wordOverlapTSent + ", " +
				+ ellaborationTransitionScore + ", " +
				+ changeTransitionScore + ", " +
				+ lcs + ", " +
				+ subjectOverlap + ", " +
				+ objectOverlap + ", " +
				+ subjectNounOverlap + ", " +
				+ nerRatio + ", " +
				+ lengthRatio + ", " +
				+ tosScore + ", " +
				semanticSimilarityScore + ");";
		System.out.println(sql);
		sqLiteUtils.executeUpdate(sql);
	}

	public static ArrayList<FeatureEntry> getAllLegal() throws SQLException {
		String sql = "SELECT * FROM FEATURE_ENTRY_LEGAL_SENTENCE;";
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);

		if(resultSet.isClosed()){
			return null;
		}

		ArrayList<FeatureEntry> featureEntries = new ArrayList<>();
		FeatureEntry entry;
		while (resultSet.next()){
			entry = new FeatureEntry();
			entry.dbId = resultSet.getInt("ID");
			entry.ssid = resultSet.getInt("SSID");
			entry.tsid = resultSet.getInt("TSID");
			entry.type = resultSet.getInt("TYPE");
			entry.adjectiveSimilarity = resultSet.getDouble("ADJECTIVE_SIMI");
			entry.nounSimilarity = resultSet.getDouble("NOUN_SIMI");
			entry.verbSimilarity = resultSet.getDouble("VERB_SIMI");
			entry.wordSimilarity = resultSet.getDouble("WORD_SIMI");
			entry.wordOverlapSSent = resultSet.getDouble("WOVERLAP_S");
			entry.wordOverlapTSent = resultSet.getDouble("WOVERLAP_T");
			entry.ellaborationTransitionScore = resultSet.getDouble("ETRANSITION");
			entry.changeTransitionScore = resultSet.getDouble("CTRANSITION");
			entry.lcs = resultSet.getDouble("LCS");
			entry.subjectOverlap = resultSet.getDouble("SOVERLAP");
			entry.objectOverlap = resultSet.getDouble("OOVERLAP");
			entry.subjectNounOverlap = resultSet.getDouble("SNOVERLAP");
			entry.nerRatio = resultSet.getDouble("NER_RATIO");
			entry.lengthRatio = resultSet.getDouble("LENGTH_RATIO");
			entry.tosScore = resultSet.getInt("TOS_SCORE");
			entry.semanticSimilarityScore = resultSet.getDouble("SEMANTIC_SCORE");

			featureEntries.add(entry);
		}

		return featureEntries;
	}

	public double[] getFeatureArray(){
		double[] featureArray = {
				this.getAdjectiveSimilarity(),
				this.getChangeTransitionScore(),
				this.getEllaborationTransitionScore(),
				this.getLcs(),
				this.getLengthRatio(),
				this.getNerRatio(),
				this.getNounSimilarity(),
				this.getObjectOverlap(),
				this.getSemanticSimilarityScore(),
				this.getSubjectNounOverlap(),
				this.getSubjectOverlap(),
				this.getTosScore(),
				this.getVerbSimilarity(),
				this.getWordOverlapSSent(),
				this.getWordOverlapTSent(),
				this.getWordSimilarity()
		};

		return featureArray;
	}
}
