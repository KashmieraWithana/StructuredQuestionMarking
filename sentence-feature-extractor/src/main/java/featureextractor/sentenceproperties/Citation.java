package featureextractor.sentenceproperties;

import java.util.ArrayList;

public class Citation {

    static int patternCount1 = 6;
    static int patternCount2 = 3;

    public static boolean checkCitation(String sourceSentence){

        boolean returnValue = false;
        String regexPattern1 = "^\\w+\\sv\\.\\s\\w+.*(([A-Za-z]+\\s){4}).*$";
        String regexPattern2 = "^See.*";
        String regexPattern3 = "^Pp..*";
        String checkingPart1="";
        String checkingPart2="";

        /*String[] words = sourceSentence.split(" ");

        if(words.length<patternCount2){
            patternCount2=words.length;
            patternCount1=words.length;
        } else if(words.length<patternCount1){
            patternCount1=words.length;
        }
        for(int i=0;i<patternCount1;i++){
           if(i!=0){
               checkingPart1=checkingPart1+" "+words[i];
           }else{
               checkingPart1=words[i];
           }
        }
        for(int j=0;j<patternCount2;j++){
            if(j!=0){
                checkingPart2=checkingPart2+" "+words[j];
            }else{
                checkingPart2=words[j];
            }
        }

        System.out.println(checkingPart1);
        System.out.println(checkingPart2);*/
        if(sourceSentence.matches(regexPattern1) || sourceSentence.matches(regexPattern2)
                || sourceSentence.matches(regexPattern3)){

            returnValue = true;
        }

        return returnValue;
    };
}
