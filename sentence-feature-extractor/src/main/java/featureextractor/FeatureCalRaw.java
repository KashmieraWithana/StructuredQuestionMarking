package featureextractor;

import datasetparser.models.FeatureEntry;
import edu.stanford.nlp.pipeline.Annotation;
import featureextractor.cosinesimilarity.AdjectiveSimilarity;
import featureextractor.cosinesimilarity.NounSimilarity;
import featureextractor.cosinesimilarity.VerbSimilarity;
import featureextractor.cosinesimilarity.WordSimilarity;
import featureextractor.grammaticalrelationships.GrammarOverlapRatio;
import featureextractor.lexicalsimilarity.LongestCommonSubstring;
import featureextractor.lexicalsimilarity.OverlapWordRatio;
import featureextractor.semanticsimilarity.SemanticSentenceSimilarity;
import featureextractor.sentencepropertyfeatures.NERRatio;
import featureextractor.sentencepropertyfeatures.SentenceLengths;
import featureextractor.sentencepropertyfeatures.TransitionalWords;
import featureextractor.sentencepropertyfeatures.TypeOfSpeech;
import utils.NLPUtils;

import java.util.ArrayList;
import java.util.Properties;

public class FeatureCalRaw {

	public static void main(String[] args) throws Exception {

		String sourceSentence;
		String targetSentence;

		FeatureEntry featureEntry;
		ArrayList<FeatureEntry> featureEntries = new ArrayList<>();

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,coref");
		props.setProperty("coref.algorithm", "statistical");
		//		NLPUtils nlpUtils = new NLPUtils(props, "http://corenlp.run", 80, 8);
		NLPUtils nlpUtils = new NLPUtils(props);

		/**
		 *  This loop iterates through all the relationships and calculate all the
		 *  features based on WORDS.
		 */
		// iterating through all the relationships

		// takes two sentences from the relationship
		sourceSentence = " group attack was very cornerstone of Government's case, and virtually every witness to crime agreed that Fuller was killed by large group of perpetrators.";
		targetSentence = "Considering withheld evidence \"in context of entire record,\" Agurs, supra, at 112, that evidence is too little, weak, or distant from main evidentiary points to meet Brady's standards.";

		// creates a FeatureEntry to hold all feature values
		featureEntry = new FeatureEntry();

		// sets relationshipId (for the table reference)
		featureEntry.setRelationshipId(900000);

		// sets relationship type
		featureEntry.setType(1);

		// word cosine similarity
		WordSimilarity wordSimilarity = new WordSimilarity(sourceSentence, targetSentence);
		featureEntry.setWordSimilarity(wordSimilarity.similarityScore());

		// overlap ratio of words
		OverlapWordRatio overlapWordRatio = new OverlapWordRatio();
		ArrayList<Double> overlapWordRatios = overlapWordRatio.getOverlapScore(sourceSentence, targetSentence);
		featureEntry.setWordOverlapSSent(overlapWordRatios.get(0));
		featureEntry.setWordOverlapTSent(overlapWordRatios.get(1));

		// longest common substring
		LongestCommonSubstring lcs = new LongestCommonSubstring(sourceSentence, targetSentence);
		featureEntry.setLcs(lcs.lcsValueSentence1());

		// lengths of sentences
		SentenceLengths sentenceLengths = new SentenceLengths(sourceSentence, targetSentence);
		featureEntry.setLengthRatio(sentenceLengths.getLengthScore());

		// type of speech
		TypeOfSpeech typeOfSpeech = new TypeOfSpeech(sourceSentence, targetSentence);
		featureEntry.setTosScore(typeOfSpeech.getTOSScore());

		// transitional word
		TransitionalWords trWords = new TransitionalWords(sourceSentence);
		//check words that ellaborate
		featureEntry.setEllaborationTransitionScore(trWords.ellaborationScore());
		//check words that change the topic
		featureEntry.setChangeTransitionScore(trWords.changeScore());

		// text to resolve coreferences
		String corefText = targetSentence + " " + sourceSentence;

		// annotate both sentences in order to resolve coreferences
		Annotation annotation = nlpUtils.annotate(corefText);
		ArrayList<String> resolvedSents = nlpUtils.replaceCoreferences(annotation);

		// coreferences replaced new sentences
		sourceSentence = resolvedSents.get(0);
		targetSentence = resolvedSents.get(1);

		// annotated each for other features
		Annotation sourceAnnotation = nlpUtils.annotate(sourceSentence);
		Annotation targetAnnotation = nlpUtils.annotate(targetSentence);

		// noun cosine similarity
		NounSimilarity nounSimilarity = new NounSimilarity(sourceAnnotation, targetAnnotation, nlpUtils);
		featureEntry.setNounSimilarity(nounSimilarity.similarityScore());

		// verb cosine similarity
		VerbSimilarity verbSimilarity = new VerbSimilarity(sourceAnnotation, targetAnnotation, nlpUtils);
		featureEntry.setVerbSimilarity(verbSimilarity.similarityScore());

		// adjective cosine similarity
		AdjectiveSimilarity adjectiveSimilarity = new AdjectiveSimilarity(sourceAnnotation, targetAnnotation, nlpUtils);
		featureEntry.setAdjectiveSimilarity(adjectiveSimilarity.similarityScore());

		// ratio overlap of grammatical relationships
		GrammarOverlapRatio grammarOverlapRatio = new GrammarOverlapRatio(sourceAnnotation, targetAnnotation, nlpUtils);
		featureEntry.setSubjectOverlap(grammarOverlapRatio.getSubjectOverlap());
		featureEntry.setObjectOverlap(grammarOverlapRatio.getObjectOverlap());
		featureEntry.setSubjectNounOverlap(grammarOverlapRatio.getSubjectNounOverlap());

		// NER ratio
		NERRatio nerRatio = new NERRatio(sourceAnnotation, targetAnnotation, nlpUtils);
		featureEntry.setNerRatio(nerRatio.getRatio());

		// Semantic Similarity Score
		SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity(sourceAnnotation,
				targetAnnotation, nlpUtils);
		featureEntry.setSemanticSimilarityScore(semanticSentenceSimilarity.getAverageScore());

		// at the end
		featureEntries.add(featureEntry);
		System.out.println("aaaa :" + featureEntry.getAdjectiveSimilarity());

		double[] val = { featureEntry.getType(), featureEntry.getAdjectiveSimilarity(),
				featureEntry.getChangeTransitionScore(), featureEntry.getEllaborationTransitionScore(),
				featureEntry.getLcs(), featureEntry.getLengthRatio(),
				featureEntry.getNerRatio(), featureEntry.getNounSimilarity(),
				featureEntry.getObjectOverlap(),
				featureEntry.getSemanticSimilarityScore(), featureEntry.getSubjectNounOverlap(),
				featureEntry.getSubjectOverlap(), featureEntry.getTosScore(),
				featureEntry.getVerbSimilarity(), featureEntry.getWordOverlapSSent(),
				featureEntry.getWordOverlapTSent(), featureEntry.getWordSimilarity() };

		System.out.println("feature array");
		for (int p = 0; p < val.length; p++) {
			System.out.println(val[p] + ",");
		}

	}
}
