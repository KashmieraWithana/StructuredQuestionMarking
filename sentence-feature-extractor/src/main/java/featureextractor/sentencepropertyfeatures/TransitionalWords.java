package featureextractor.sentencepropertyfeatures;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransitionalWords {




    Pattern p = Pattern.compile("[a-zA-Z]+");
    Matcher m2;
    String sentence;
    int wordCount = 6;
    String firstWord;
    String subsentence="";
    boolean wordFound = false;

    public TransitionalWords(String sourceSentence){
        sentence=sourceSentence;
        m2 = p.matcher(sourceSentence);
        for(int i=0; i< wordCount; i++){
            if(m2.find()) {
                String word = m2.group().toLowerCase();
                if (i == 0) {
                    firstWord = word;
                    wordFound = true;
                }
                subsentence = subsentence + " " + word;
            }
        }

    }

    private boolean checkEllaborationWords(){

        if(wordFound){
            if(PropertyUtils.getEllaborationWords().contains(firstWord)){
                return true;
            }
        }

        return false;

    }

    private boolean checkEllaborationPhrase(){

        if (wordFound){
            ArrayList<String> transitionPhrases=PropertyUtils.getEllaborationPhrases();
            for(int i=0;i<transitionPhrases.size();i++){
                Pattern p = Pattern.compile("("+transitionPhrases.get(i)+")", Pattern.MULTILINE);
                Matcher m = p.matcher(subsentence);
                if(m.find()){
                    return true;
                }

            }
        }
        return false;

    }



    private boolean checkChangePhrase(){

        if (wordFound){
            ArrayList<String> changePhrases=PropertyUtils.getChangeOfTopicsWords();
            for(int i=0;i<changePhrases.size();i++){
                Pattern p = Pattern.compile("("+changePhrases.get(i)+")", Pattern.MULTILINE);
                Matcher m = p.matcher(subsentence);
                if(m.find()){
                    return true;
                }

            }
        }
        return false;

    }

    public int ellaborationScore(){
        if(checkEllaborationWords()){
                return 1;
        }else if(checkEllaborationPhrase()){
            return 1;
        }else{
            return 0;
        }
    }

    public int changeScore(){
        if(checkChangePhrase()){
            return 1;
        }else{
            return 0;
        }
    }



}
