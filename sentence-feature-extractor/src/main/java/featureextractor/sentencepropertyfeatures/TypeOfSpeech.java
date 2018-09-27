package featureextractor.sentencepropertyfeatures;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeOfSpeech {

    private String sourceSentence;
    private String targetSentence;
    Pattern p = Pattern.compile("(?:^|\\s)'([^']*?)'(?:$|\\s)", Pattern.MULTILINE);
    ArrayList<String> regexMatchesSource = new ArrayList<String>();
    ArrayList<String> regexMatchesTarget = new ArrayList<String>();

    public TypeOfSpeech(String sourceSentence,String targetSentence){
        this.sourceSentence = sourceSentence;
        this.targetSentence = targetSentence;
    }

    private boolean checkRelativeToSource(){

        Matcher ms = p.matcher(sourceSentence);
        if (ms.find()) {
            regexMatchesSource.add(ms.group());
            while (ms.find()) {
                regexMatchesSource.add(ms.group());
            }
            for (String regexMatch: regexMatchesSource){

                String regexMatch1 = regexMatch.replaceAll("(')", "");

                if(targetSentence.toLowerCase().indexOf(regexMatch1.toLowerCase()) != -1){
//                    System.out.println("found : "+regexMatch1);
                    return true;
                }else if(targetSentence.toLowerCase().indexOf(regexMatch.toLowerCase()) != -1){
//                    System.out.println("found : "+regexMatch);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRelativeToTarget(){

        Matcher mt = p.matcher(targetSentence);
        if (mt.find()) {
            regexMatchesTarget.add(mt.group());
            while (mt.find()) {
                regexMatchesTarget.add(mt.group());
            }
            for (String regexMatch: regexMatchesTarget){
                String regexMatch1 = regexMatch.replaceAll("(')", "");

                if(sourceSentence.toLowerCase().indexOf(regexMatch1.toLowerCase()) != -1){
//                    System.out.println("found : "+regexMatch1);
                    return true;
                }else if(sourceSentence.toLowerCase().indexOf(regexMatch.toLowerCase()) != -1){
//                    System.out.println("found : "+regexMatch);
                    return true;
                }
            }
        }
        return false;
    }

    public int getTOSScore(){
        if(checkRelativeToSource() || checkRelativeToTarget()){
            return 1;
        }
        return 0;
    }


}
