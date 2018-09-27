package shiftinview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TfIdfCal {

	// case numbers taken for annotation
	// 86, 88, 102, 113, 114, 118, 130, 145, 146

	public static void main(String[] args) {
		ArrayList<ArrayList<String>> totalWords = new ArrayList<>();

		for (int i = 0; i < 231; i++) {
			totalWords.add(getWordsOfCase(i + 1));
		}

		int[] caseNumbers = { 86, 88, 102, 113, 114, 118, 130, 145, 146 };
		HashMap<Integer, HashMap> casesTfIdf = new HashMap<>();

		for (int i = 0; i < caseNumbers.length; i++) {
			HashMap<String, Double> tfIdfWords = calTfIdf(totalWords, caseNumbers[i]);
			casesTfIdf.put(caseNumbers[i], tfIdfWords);
			System.out.println("Case " + caseNumbers[i] + " done");
		}

		System.out.println("done");
	}

	private static HashMap<String, Double> calTfIdf(ArrayList<ArrayList<String>> totalWords, int n) {
		/**
		 * l (logarithm) = 1 + log( tf(t,d) )
		 * tf(t,d) is the number of times term t appears in the document d.
		 *
		 * idf(t,D) = log ( N / df(t,D) )
		 * N is the number of documents in the data set.
		 * df(t,D) is the number of documents which contains t
		 *
		 * TFIDF(t,d,D) = tf(t,d) * idf(t,D)
		 */

		HashMap<String, Double> tfIdfValues = new HashMap<>();

		int docCount = totalWords.size();

		ArrayList<String> words = totalWords.get(n - 1);
		Set<String> distinctWords = new HashSet<>(words);

		for (String word : distinctWords) {
			double termFrequency = (double) Collections.frequency(words, word);
			double termFrequencyLog = 1 + Math.log10(termFrequency);

			double docFrequency = 0.0;
			for (ArrayList<String> docWords : totalWords) {
				if (docWords.contains(word)) {
					docFrequency++;
				}
			}

			double inverseDocFrequency = Math.log10(docCount / docFrequency);

			double tfIdf = termFrequencyLog * inverseDocFrequency;

			tfIdfValues.put(word, tfIdf);
		}

		return tfIdfValues;
	}

	private static ArrayList<String> getWordsOfCase(int n) {
		String filePath = new File("").getAbsolutePath();
		filePath += "/src/main/resources/StopWordsRemovedCriminalCases/" + n + ".txt";

		BufferedReader br = null;

		ArrayList<String> words = new ArrayList<String>();

		try (FileReader fr = new FileReader(filePath)) {
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 2) {
					words.add(line.trim().toLowerCase());
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return words;
	}

}
