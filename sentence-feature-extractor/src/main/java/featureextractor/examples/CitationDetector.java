package featureextractor.examples;

import featureextractor.sentenceproperties.Citation;

public class CitationDetector {
    static String sentence = "See §1227(a)(2)(A)(iii).";

    public static void main(String[] args) {
        Boolean value = Citation.checkCitation(sentence);
        if(value){
            System.out.println("citation Detected");
        }else{
            System.out.println("citation Not detected");
        }

    }
}
