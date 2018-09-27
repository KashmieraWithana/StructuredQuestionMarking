package featureextractor.sentenceproperties;

import edu.stanford.nlp.pipeline.Annotation;
import utils.NLPUtils;

public class SentenceNouns extends SentenceProps {

	public SentenceNouns(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils) {
		seq_sentence1 = nlpUtils.getNouns(sourceAnnotation);
		seq_sentence2 = nlpUtils.getNouns(targetAnnotation);
	}

}
