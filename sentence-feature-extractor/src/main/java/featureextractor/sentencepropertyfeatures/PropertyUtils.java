package featureextractor.sentencepropertyfeatures;

import java.util.ArrayList;
import java.util.Arrays;

public class PropertyUtils {

    private static final ArrayList<String> ellaborationPhrases = new ArrayList<String>(
            Arrays.asList("To make that", "in the first place", "not only",
                    "but also", "as well as", "as a matter of fact", "together with", "in like manner",
                     "of course", "in addition", "coupled with",
                    "in the same fashion", "in the same way",
                     "not to mention", "to say nothing of",
                     "equally important", "by the same token",
                    "different from", "on the other hand",  "at the same time",  "in spite of",  "even so ",
                    "even though","as much as",
                     "be that as it may",
                    "after all", "with this intention","so that","even if","with this in mind","because of this",
                    "due to this", "as mentioned here","in other words",
                    "in fact","in general", "including this","in this case","for example","for this reason","for instance",
                    "to put it", "to demonstrate this", "that is to say", "on the positive side",
                     "with this in mind", "as a result",
                    "in that case", "in effect", "one is","two is","Another is","Neither this", "each of these",
                    "in reaching this","as relevant here","it follows","in those cases",
                    "either way","to do so","to achieve that","to perform that",
                    "to show that","in each case”","at that time","this opinion","confirms this","confirm this",
                    "in context","the statute’s","that phrase","one is","second is","third is",
                    "another is","they are","that is","with these assumptions", " special factors here",
                    "other special factors","these reasons", "these arguments","of the sentence","that reasoning",
                    "it is also","that is also","those are also","it could also","it can also"


            ));

    private static final ArrayList<String> ellaborationWords = new ArrayList<String>(
            Arrays.asList( "again", "to", "moreover", "more","and", "also","then", "likewise",
                    "equally","comparatively", "identically","correspondingly","first","uniquely",
                    "similarly", "like", "furthermore", "as", "additionally","too",
                    "but","still", "instead","unlike","whereas", "despite","yet", "conversely",
                    "otherwise","nevertheless","besides", "rather","nonetheless","regardless","above",
                    "notwithstanding","that", "those", "such","indeed","especially","consequently", "thus",
                    "therefore","thereupon","forthwith","hence", "accordingly", "henceforth", "later","these",
                    "see","subsequent","nor","finally","accordingly","second","third","fourth"

                    )
    ); //although is removed

    private static final ArrayList<String> changeOfTopicsWords = new ArrayList<String>(
            Arrays.asList("in contrast","on the contrary","on the negative side",
                    "however","albeit","meanwhile"
                    )
    );

    public static ArrayList<String> getEllaborationWords() {
        return ellaborationWords;
    }

    public static ArrayList<String> getEllaborationPhrases() {
        return ellaborationPhrases;
    }

    public static ArrayList<String> getChangeOfTopicsWords() {
        return changeOfTopicsWords;
    }
}
