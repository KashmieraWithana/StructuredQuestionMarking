package featureextractor.sentenceproperties;

import edu.stanford.nlp.pipeline.Annotation;
import utils.NLPUtils;

public class SentenceVerbs extends SentenceProps{

	public SentenceVerbs(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils) {
		seq_sentence1 = nlpUtils.getVerbsWithOutBe(sourceAnnotation);
		seq_sentence2 = nlpUtils.getVerbsWithOutBe(targetAnnotation);
	}

}
