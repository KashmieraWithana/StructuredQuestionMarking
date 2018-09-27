package questionPaperMarking;

import java.util.ArrayList;

import edu.stanford.nlp.pipeline.Annotation;
import featureextractor.cosinesimilarity.AdjectiveSimilarity;
import featureextractor.cosinesimilarity.NounSimilarity;
import featureextractor.cosinesimilarity.Similarity;
import featureextractor.cosinesimilarity.VerbSimilarity;
import featureextractor.cosinesimilarity.WordSimilarity;
import featureextractor.lexicalsimilarity.OverlapWordRatio;
import featureextractor.semanticsimilarity.SemanticSentenceSimilarity;
import featureextractor.sentencepropertyfeatures.SentenceLengths;
import utils.NLPUtils;

public class CheckAllFeatures {
	
	
	public static void main(String[] args) {
		
		NLPUtils nlpUtils = new NLPUtils("tokenize,ssplit,pos");
		
	    String sourceSentence = "A database is a collection of information which can be easily accessed, managed and updated.";
	    String targetSentence = "A database is a collection of information that is organized so that it can be easily accessed, managed and updated ";

	    Annotation sourceAnnotation =nlpUtils.annotate(sourceSentence);
        Annotation targetAnnotation = nlpUtils.annotate(targetSentence);

        SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity(sourceAnnotation,targetAnnotation,nlpUtils);
        double semanticSimilarityScore = semanticSentenceSimilarity.getAverageScore();
        System.out.println("\nSemantic Similarity Score   : "+semanticSimilarityScore);

        OverlapWordRatio overlapWordRatio = new OverlapWordRatio();
		ArrayList<Double> overlapScores = overlapWordRatio.getOverlapScore(sourceSentence, targetSentence);
		System.out.println("Word overlap Scores         : " + overlapScores.toString());
		
		SentenceLengths sentenceLengths = new SentenceLengths(sourceSentence, targetSentence);
        System.out.println("Sentence Length Score       : "+ sentenceLengths.getLengthScore());
        
        Similarity wordSimilarity = new WordSimilarity(sourceSentence,targetSentence) ;
        double scoreWordsSimilarity = wordSimilarity.similarityScore();
        System.out.println("Word Similarity Score       : "+ scoreWordsSimilarity);
        
        Similarity nounSimilarity = new NounSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
        double scoreNounsSimilarity = nounSimilarity.similarityScore();
        System.out.println("Noun Similarity Score       : "+ scoreNounsSimilarity);

	    Similarity verbSimilarity = new VerbSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
	    double scoreVerbsSimilarity = verbSimilarity.similarityScore();
	    System.out.println("Verb Similarity Score       : "+ scoreVerbsSimilarity);

	    Similarity adjectiveSimilarity = new AdjectiveSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
	    double scoreAdjectivesSimilarity = adjectiveSimilarity.similarityScore();
	    System.out.println("Adjective Similarity Score  : "+ scoreAdjectivesSimilarity);
        
        
	}

}
