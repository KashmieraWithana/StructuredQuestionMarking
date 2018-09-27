package featureextractor.examples;

import featureextractor.sentencepropertyfeatures.TypeOfSpeech;

public class TOSExample {
    public static void main(String[] args) {
        TypeOfSpeech typeOfSpeech = new TypeOfSpeech( "A 'reasonable probability' of a different result\" is one in which the suppressed evidence \"" +
                " 'undermines confidence in the outcome of the trial.' ","Such evidence is 'material' when there is a reasonable probability  had the evidence been disclosed, the result of the proceeding would have been different.");
        System.out.println("Score : "+ typeOfSpeech.getTOSScore());
    }
}
