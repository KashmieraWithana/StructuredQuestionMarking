package questionPaperMarking;
import annotationreader.DBConnect;
import edu.stanford.nlp.pipeline.Annotation;
import featureextractor.semanticsimilarity.SemanticSentenceSimilarity;
import utils.NLPUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class questionMarking {

    Connection con = null;

    String sourceSentence = null;
    String targetSentence = null;

    public questionMarking() {

        con = DBConnect.connect();
    }

    public  void paperMarking(int paperId){

        try
        {
            ArrayList<question> queArray = new ArrayList<question>();
            ArrayList<studentAnswer> stdAnsArray = new ArrayList<>();

            String sql = "SELECT questionId FROM question WHERE questionPaperId = '"+ paperId +"' AND type = 'structured'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                question que = new question();
                int qid = rs.getInt("questionId");


                String sql1 = "SELECT structuredqId,answer,contentMark,markPerKeyword FROM structured_question WHERE structuredqId = '"+ qid +"'";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                ResultSet rs1 = pst1.executeQuery();
                while(rs1.next())
                {

                    String ans = rs1.getString("answer");

                    System.out.println(ans);

                    float cMark = rs1.getFloat("contentMark");
                    int q_id = rs1.getInt("structuredqId");
                    que.setTeacherAnswer(ans);
                    que.setContentMark(cMark);
                    que.setPaperId(paperId);
                    que.setQuestionId(q_id);
                    queArray.add(que);



                    String sql2 = "SELECT studentId,studAnswerId FROM student_answer WHERE  questionId = '"+ q_id +"' AND markedStatus = '0'";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pst2.executeQuery();

                    while(rs2.next())
                    {
                        studentAnswer stdA = new studentAnswer();
                        int sId = rs2.getInt("studentId");
                        int sAnsId = rs2.getInt("studAnswerId");
                        stdA.setTeacherAnswer(ans);
                        stdA.setStudentId(sId);
                        stdA.setQuestionId(qid);
                        stdA.setStudentAnswerId(sAnsId);
                        stdA.setContentMark(cMark);

                        System.out.println(sId);

                        String sql3 = "SELECT stdanswer FROM structured_stud_answer WHERE structuredStudAnsId = '"+ sAnsId +"' ";
                        PreparedStatement pst3 = con.prepareStatement(sql3);
                        ResultSet rs3 = pst3.executeQuery();
                        while(rs3.next())
                        {
                           String stdans = rs3.getString("stdanswer");
                           stdA.setStudentAnswer(stdans);
                           stdAnsArray.add(stdA);
                           System.out.println(stdans);

                        }


                    }

                }

            }

            /* While Loop for iterating ArrayList*/


            for (studentAnswer s : stdAnsArray) {

                NLPUtils nlpUtils = new NLPUtils("tokenize,ssplit,pos");

                String sourceSentence = s.getStudentAnswer();
                String targetSentence = s.getTeacherAnswer();

                Annotation sourceAnnotation =nlpUtils.annotate(sourceSentence);
                Annotation targetAnnotation = nlpUtils.annotate(targetSentence);

                SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity(sourceAnnotation,targetAnnotation,nlpUtils);
                double semanticSimilarityScore = semanticSentenceSimilarity.getAverageScore();

                System.out.println(s.getQuestionId());
                System.out.println(s.getStudentId());
                System.out.println(s.getTeacherAnswer());
                System.out.println(s.getStudentAnswer());
                System.out.println("\nSemantic Similarity Score   : "+semanticSimilarityScore);
            }
            



        }
        catch (Exception e)
        {
            System.out.println(e);

        }

    }

    public static void main(String[] args) throws Exception {

        questionMarking am = new questionMarking();
        am.paperMarking(1);


    }

}


