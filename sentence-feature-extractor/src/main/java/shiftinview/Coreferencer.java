package shiftinview;

import edu.stanford.nlp.pipeline.Annotation;
import utils.NLPUtils;

import java.util.ArrayList;

public class Coreferencer {

	public static ArrayList<String> getCoreferencedSentences(String sourceSentence, String targetSentence, NLPUtils nlpUtils) {
		ArrayList<String> twoSentences = new ArrayList<>();
		// text to resolve coreferences
		String corefText = targetSentence + " " + sourceSentence;

		// annotate both sentences in order to resolve coreferences
		Annotation annotation = nlpUtils.annotate(corefText);
		ArrayList<String> resolvedSents = nlpUtils.replaceCoreferences(annotation);

		// coreferences replaced new sentences
		sourceSentence = resolvedSents.get(0);
		targetSentence = resolvedSents.get(1);
		twoSentences.add(sourceSentence);
		twoSentences.add(targetSentence);

		return twoSentences;
	}
}
