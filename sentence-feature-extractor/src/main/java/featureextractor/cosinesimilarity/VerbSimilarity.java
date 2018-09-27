package featureextractor.cosinesimilarity;

import edu.stanford.nlp.pipeline.Annotation;
import featureextractor.sentenceproperties.SentenceVerbs;
import utils.NLPUtils;

public class VerbSimilarity extends Similarity{

	public VerbSimilarity(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils){
		sentenceProps = new SentenceVerbs(sourceAnnotation, targetAnnotation, nlpUtils);
	}

}
