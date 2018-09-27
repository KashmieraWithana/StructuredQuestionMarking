package featureextractor.cosinesimilarity;

import edu.stanford.nlp.pipeline.Annotation;
import featureextractor.sentenceproperties.SentenceAdjectives;
import utils.NLPUtils;

public class AdjectiveSimilarity extends Similarity{

	public AdjectiveSimilarity(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils){
		sentenceProps = new SentenceAdjectives(sourceAnnotation, targetAnnotation, nlpUtils);
	}

}
