package featureextractor;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Properties;

import datasetparser.models.FeatureEntry;
import datasetparser.models.Relationship;
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
import org.slf4j.Logger;
import utils.NLPUtils;

public class FeatureCal {

	private static final Logger logger = getLogger(FeatureCal.class);

	public static void main(String[] args) throws Exception {

		// takes all relationships consisting of sourcesent, targetsent and judgement
		ArrayList<Relationship> relationships = Relationship.getAll();

		logger.info(relationships.size() + " relationships fetch from the database.");

		String sourceSentence;
		String targetSentence;

		FeatureEntry featureEntry;
		ArrayList<FeatureEntry> featureEntries = new ArrayList<>();

		Properties props = new Properties();
		props.setProperty("annotators","tokenize,ssplit,pos,lemma,ner,depparse,coref");
		props.setProperty("coref.algorithm", "statistical");
		//		NLPUtils nlpUtils = new NLPUtils(props, "http://corenlp.run", 80, 8);
		NLPUtils nlpUtils = new NLPUtils(props);

		// iterating through all the relationships
		for (int i=1577;i<relationships.size();i++) {
			Relationship relationship = relationships.get(i);

			// takes two sentences from the relationship
			sourceSentence = relationship.getSourceSent();
			targetSentence = relationship.getTargetSent();

			// creates a FeatureEntry to hold all feature values
			featureEntry = new FeatureEntry();

			// sets relationshipId (for the table reference)
			featureEntry.setRelationshipId(relationship.getDbId());

			// sets relationship type
			featureEntry.setType(relationship.getType());

			// word cosine similarity
			WordSimilarity wordSimilarity = new WordSimilarity(sourceSentence, targetSentence) ;
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
			NounSimilarity nounSimilarity = new NounSimilarity(sourceAnnotation, targetAnnotation,nlpUtils);
			featureEntry.setNounSimilarity(nounSimilarity.similarityScore());

			// verb cosine similarity
			VerbSimilarity verbSimilarity = new VerbSimilarity(sourceAnnotation, targetAnnotation,nlpUtils);
			featureEntry.setVerbSimilarity(verbSimilarity.similarityScore());

			// adjective cosine similarity
			AdjectiveSimilarity adjectiveSimilarity = new AdjectiveSimilarity(sourceAnnotation, targetAnnotation,nlpUtils);
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
			SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity(sourceAnnotation, targetAnnotation, nlpUtils);
			featureEntry.setSemanticSimilarityScore(semanticSentenceSimilarity.getAverageScore());

			// at the end
			featureEntries.add(featureEntry);

			logger.info("Relationship : " + relationship.getDbId() + " calculated.");

			featureEntry.save();
		}

		logger.info("All features calculated.");

//		for(FeatureEntry entry:featureEntries){
//			entry.save();
//		}

		logger.info("All features entries saved");

	}

}
