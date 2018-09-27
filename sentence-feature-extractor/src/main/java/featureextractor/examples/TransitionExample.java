package featureextractor.examples;

import featureextractor.sentencepropertyfeatures.TransitionalWords;

public class TransitionExample {
    public static void main(String[] args) {
        TransitionalWords trWords = new TransitionalWords("In reaching this conclusion, the Court emphasized that it has \"traditionally exercised restraint in assessing the reach of a federal criminal statute, both out of deference to the prerogatives of Congress and out of concern that 'a fair warning should be given to the world in language that the common world will understand, of what the law intends to do if a certain line is passed.' \"\n");
        System.out.println("ellaborationScore :" + trWords.ellaborationScore());
        System.out.println("ellaborationScore :" + trWords.changeScore());
    }
}
