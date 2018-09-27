package shiftinview;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureextractor.semanticsimilarity.SemanticSentenceSimilarity;
import shiftinview.models.Verb;
import utils.NLPUtils;

import java.util.ArrayList;
import java.util.Properties;

import edu.cmu.lti.jawjaw.pobj.POS;

public class ShiftInViewAnalyzer {
    public static void main(String[] args) {
        String targetSentence = "Although he has lived in this country for most of his life, Lee is not a United States" +
                " citizen, and he feared that a criminal conviction might affect his status as a lawful " +
                "permanent resident.";
        String sourceSentence = "His attorney assured him there was nothing to worry about,the Government would not " +
                "deport him if he pleaded guilty.\n";
        ArrayList<Verb> verbsSentence1;
        ArrayList<Verb> verbsSentence2;
        Annotation targetAnnotation;
        Annotation sourceAnnotation;

        ArrayList<String> coreferencedSentences = new ArrayList<>();

        // set up pipeline properties
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
        // use faster shift reduce parser
        props.setProperty("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");
        props.setProperty("parse.maxlen", "100");
        // set up Stanford CoreNLP pipeline
        // StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        NLPUtils nlpUtils =  new NLPUtils(props);
        // build annotation for a review
        sourceAnnotation = nlpUtils.annotate(sourceSentence);
        targetAnnotation = nlpUtils.annotate(targetSentence);



       /* Coreferencer coreferencer = new Coreferencer();
        coreferencedSentences=Coreferencer.getCoreferencedSentences(sourceSentence,targetSentence,nlpUtils);
        System.out.println(coreferencedSentences.get(0));
        System.out.println(coreferencedSentences.get(1));*/
        ConstituentParser constituentParser = new ConstituentParser();
        //  constituentParser.runConstituentParser(sourceAnnotation,nlpUtils);

        System.out.println("break");

        verbsSentence1 = constituentParser.getVerbRelationships(sourceAnnotation,nlpUtils);



        System.out.println( "targetSentence");
        System.out.println(" ");

        verbsSentence2 = constituentParser.getVerbRelationships(targetAnnotation,nlpUtils);
        String verbSource="";
        String verbTarget="";
        String currentSourceVerb="";
        String currentTargetVerb = "";
        Verb secondverb;
        Verb verb;
        ArrayList<String[]> closeVerbs = new ArrayList<>();


        System.out.println("array sizes");
        System.out.println(verbsSentence2.size());
        System.out.println(verbsSentence1.size());

        for(int j=0;j<verbsSentence2.size();j++){
            verb = verbsSentence2.get(j);

            if(verb.isVerbIsDep()){
                verbTarget = verb.getDepLemma();
            }
            else if(verb.isVerbIsGov()){
                verbTarget=verb.getGovLemma();
            }
            for(int i=0;i<verbsSentence1.size();i++){
                secondverb= verbsSentence1.get(i);


                if(secondverb.isVerbIsDep()){
                    verbSource=secondverb.getDepLemma();
                }
                else if(secondverb.isVerbIsGov()){
                    verbSource=secondverb.getGovLemma();
                }
                /*System.out.println(" ");
                System.out.println("s  "  +verbSource);
                System.out.println("t  "+ verbTarget);*/
                SemanticSentenceSimilarity semanticSentenceSimilarity= new SemanticSentenceSimilarity();
                double score =semanticSentenceSimilarity.wordSimilarity(verbSource,POS.v,verbTarget,POS.v);
                if(score>=0.8){
                    if(!currentSourceVerb.equals(verbSource) || !currentTargetVerb.equals(verbTarget)){
                        String[] verbPair = new String[2];
                        verbPair[0]=verbTarget;
                        verbPair[1]=verbSource;
                        currentSourceVerb=verbSource;
                        currentTargetVerb=verbTarget;
                        closeVerbs.add(verbPair);

                    }
                }

                //System.out.println("score: "+score);

            }

        }

        System.out.println("close verbs");
        for(String[] pair:closeVerbs){
            System.out.println(pair[0]);
            System.out.println(pair[1]);
            System.out.println("");
        }







    }
}
