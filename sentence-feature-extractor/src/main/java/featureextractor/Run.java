package featureextractor;

import java.util.ArrayList;

import datasetparser.models.FeatureEntry;
import utils.SQLiteUtils;

/**
 * To Run any temporary algorithms
 */
public class Run {

	public static void main(String[] args) throws Exception {
		ArrayList<FeatureEntry> featureEntries = FeatureEntry.getAll();
		SQLiteUtils sqLiteUtils = new SQLiteUtils();

		for (int i = 0; i < featureEntries.size(); i++) {
			double semScore = featureEntries.get(i).getSemanticSimilarityScore();

			semScore = semScore * 2.0;
			if (semScore > 1) {
				semScore = 1;
			}

			String sql = "UPDATE FEATURE_ENTRY SET SEMANTIC_SCORE=" + semScore +
					" WHERE ID=" +featureEntries.get(i).getDbId();

			sqLiteUtils.executeUpdate(sql);
//			System.out.println(i);
		}

	}

}
