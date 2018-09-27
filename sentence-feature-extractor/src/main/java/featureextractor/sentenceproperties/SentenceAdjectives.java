package featureextractor.sentenceproperties;

import edu.stanford.nlp.pipeline.Annotation;
import utils.NLPUtils;

public class SentenceAdjectives extends SentenceProps {

	public SentenceAdjectives(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils) {
		seq_sentence1 = nlpUtils.getAdjectives(sourceAnnotation);
		seq_sentence2 = nlpUtils.getAdjectives(targetAnnotation);
	}

}
