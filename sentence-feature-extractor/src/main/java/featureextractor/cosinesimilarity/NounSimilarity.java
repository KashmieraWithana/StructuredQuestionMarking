package featureextractor.cosinesimilarity;

import edu.stanford.nlp.pipeline.Annotation;
import featureextractor.sentenceproperties.SentenceNouns;
import utils.NLPUtils;

public class NounSimilarity extends Similarity{

	public NounSimilarity(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils){
		sentenceProps = new SentenceNouns(sourceAnnotation, targetAnnotation, nlpUtils);
	}

}
