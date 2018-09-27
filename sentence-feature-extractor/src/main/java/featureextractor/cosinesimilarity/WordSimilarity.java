package featureextractor.cosinesimilarity;

import featureextractor.sentenceproperties.SentenceWords;

public class WordSimilarity extends Similarity{

	public WordSimilarity(String sentence1, String sentence2){
		sentenceProps = new SentenceWords(sentence1,sentence2);
	}
}
