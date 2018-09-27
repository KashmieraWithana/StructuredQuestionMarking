import static org.slf4j.LoggerFactory.getLogger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import datasetparser.models.FeatureEntry;
import org.slf4j.Logger;
import utils.SQLiteUtils;

public class RelationToJson {

	private static final Logger logger = getLogger(RelationToJson.class);

	public static void main(String[] args) throws Exception {
		/*
		String sql = "SELECT * FROM LEGAL_SENTENCE WHERE CASE_FILE='criminal_triples/case_11.txt'";

		// executes sql and fills up the array list
		SQLiteUtils sqLiteUtils = new SQLiteUtils();
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);
		ArrayList<String> sentences = new ArrayList<>();
		while (resultSet.next()) {
			sentences.add(resultSet.getString("SENTENCE"));
		}
		logger.info(sentences.size() + " sentences fetch from the database.");

		List<Map<String, Object>> sentenceList = new ArrayList<>();

		Map<String, Object> map;

		for (int i = 0; i < 51; i++) {
			map = new HashMap<>();

			String sentence = sentences.get(i).replaceAll("\"","\'");
//			sentence = sentence.replaceAll("'","\\");
			map.put("key",i+1);
			map.put("text",(i+1)+ ") " +sentence);

			sentenceList.add(map);
		}

		String json = new ObjectMapper().writeValueAsString(sentenceList);
		System.out.println(json);
		*/


		String sql = "SELECT * FROM FEATURE_ENTRY_LEGAL_SENTENCE where SSID=22 or TSID=22 GROUP BY SSID,TSID";
		// executes sql and fills up the array list
		SQLiteUtils sqLiteUtils = new SQLiteUtils();
		ResultSet resultSet = sqLiteUtils.executeQuery(sql);
		ArrayList<FeatureEntry> entries = new ArrayList<>();
		FeatureEntry featureEntry;
		while (resultSet.next()) {
			featureEntry = new FeatureEntry();
			featureEntry.setSsid(resultSet.getInt("SSID"));
			featureEntry.setTsid(resultSet.getInt("TSID"));
			featureEntry.setType(resultSet.getInt("TYPE"));
			entries.add(featureEntry);
		}

		List<Map<String, Object>> featureList = new ArrayList<>();

		Map<String, Object> map;

		for (FeatureEntry entry:entries) {
			map = new HashMap<>();
			map.put("from",entry.getSsid());
			map.put("to",entry.getTsid());
			map.put("color",getColor(entry.getType()));
			featureList.add(map);
		}

		String json = new ObjectMapper().writeValueAsString(featureList);
		System.out.println(json);

	}

	public static String getColor(int type){
		String color = "";
		switch (type){
			case 0:
				color= "black";
				break;
			case 1:
				color=  "blue";
				break;
			case 2:
				color=  "pink";
				break;
			case 4:
				color=  "yellow";
				break;
			case 5:
				color=  "red";
				break;
			case 6:
				color=  "grey";
				break;
			case 8:
				color=  "purple";
				break;
			case 9:
				color=  "orange";
				break;
			case 10:
				color=  "green";
				break;
			case 11:
				color=  "brown";
				break;
			case 12:
				color=  "indigo";
				break;
			case 13:
				color=  "maroon";
				break;
			case 14:
				color=  "gold";
				break;
			case 15:
				color=  "tan";
				break;
			case 18:
				color=  "aqua";
				break;
		}
		return color;
	}

}
