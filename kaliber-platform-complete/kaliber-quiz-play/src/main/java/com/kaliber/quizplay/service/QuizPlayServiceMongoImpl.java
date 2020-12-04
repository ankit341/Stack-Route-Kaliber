package com.kaliber.quizplay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaliber.quizplay.controller.QuizPlayController;
import com.kaliber.quizplay.exception.QuizPlayException;
import com.kaliber.quizplay.model.Question;
import com.kaliber.quizplay.model.LeaderBoard;
import com.kaliber.quizplay.model.Quiz;
import com.kaliber.quizplay.model.AnswerOptions;
import com.kaliber.quizplay.model.ConfidenceWager;
import com.kaliber.quizplay.model.QuestionResponse;
import com.kaliber.quizplay.model.QuizSectionQuestions;
import com.kaliber.quizplay.model.QuizSubmissionFeedback;
import com.kaliber.quizplay.model.QuizSubmissionQuestions;
import com.kaliber.quizplay.repository.QuizPlayRepository;
import com.kaliber.quizplay.repository.QuizSubmissionQuestionsRepository;
import com.kaliber.quizplay.serviceproxy.MapUserToQuizServiceProxy;
import com.kaliber.quizplay.serviceproxy.LeaderBoardServiceProxy;
import com.kaliber.quizplay.serviceproxy.QuestionServiceProxy;
import com.kaliber.quizplay.serviceproxy.QuizServiceProxy;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class QuizPlayServiceMongoImpl implements QuizPlayService {
    @Autowired
    private QuizServiceProxy proxy;
    @Autowired
    private QuizPlayRepository quizRepo;
    @Autowired
    private LeaderBoardServiceProxy leaderBoardServiceProxy;

    @Value("${jwt.signing.key}")
    String jwtSigningKey;

//    private QuizSubmissionQuestions quizSubQues;
    @Autowired
    private QuestionServiceProxy qproxy;

    @Autowired
    private QuizSubmissionQuestionsRepository quizSubQuesRepo;

    @Autowired
    MapUserToQuizServiceProxy mapUserToQuizServiceProxy;

    private static Logger logger = LogManager.getLogger(QuizPlayController.class);

    @Override
    public UUID startQuiz(String quizCode, HttpServletRequest httpServlet) {
        QuizSubmissionFeedback quizSub = new QuizSubmissionFeedback();
        String userName = loadByUserName(httpServlet);
        UUID uuid = UUID.randomUUID();
        quizSub.setSubmissionId(uuid);
        quizSub.setQuizCode(quizCode);
        quizRepo.save(quizSub);

        ArrayList<QuizSectionQuestions> quizSectionQuestions = proxy.getAllQuizSectionQuestionsByQuizCode(quizCode);
        System.out.println(quizSectionQuestions.size()+" size of section questions");
        mapUserToQuizServiceProxy.mapPersonToQuiz(userName, quizCode);
        quizSectionQuestions.forEach(sectionQuestion ->{
            QuizSubmissionQuestions quizSubQuestions = new QuizSubmissionQuestions();
            quizSubQuestions.setSubmissionId(quizSub.getSubmissionId());
            quizSubQuestions.setSectionTitle(sectionQuestion.getSectionTitle());
            quizSubQuestions.setQuestionId(sectionQuestion.getQuestionId());
            try {
                Question question =  playQuizQuestionFeign(sectionQuestion.getQuestionId());
                quizSubQuestions.setQuestion(question);
            } catch (QuizPlayException e) {
                logger.error("No question found with this questionId in startQuiz() method");
            }
            quizSubQuestions.setSequence(sectionQuestion.getSequence());
            System.out.println("quizSubQuestion "+quizSubQuestions.getSequence());
            quizSubQuesRepo.save(quizSubQuestions);
        });
        return quizSub.getSubmissionId();

    }

    @Override
    public QuizSubmissionFeedback insertQuizSubmission(QuizSubmissionFeedback quizSub) {
        QuizSubmissionFeedback savedQuizSubmission = quizRepo.save(quizSub);
        return savedQuizSubmission;
    }

    @Override
    public void insertQuizSubmissionQuestions(QuizSubmissionQuestions quizSubQues) {

        quizSubQuesRepo.save(quizSubQues);
    }

// get quizSubmissionQuestion by submissionId
    @Override
    public ArrayList<QuizSubmissionQuestions> getQuizSubmissionQuestions(UUID submissionId) {

        return quizSubQuesRepo.findBySubmissionId(submissionId);
    }

    @Override
    public QuizSubmissionFeedback createQuizsubmission(String quizCode, UUID submissionId, HttpServletRequest httpServletRequest) throws QuizPlayException {

        QuizSubmissionFeedback quizSub = quizRepo.findBySubmissionId(submissionId);
        if (quizRepo.toString().isEmpty()) {

            throw new QuizPlayException("There is no data in the system, couldn't post data");
        } else {
//            responses.forEach((key, value) -> {
//                if (value.equals(Question.isCorrect.TRUE)) {
//                    int count = quizSub.getCorrectlyAnsweredQuestions() + 1;
//                    quizSub.setCorrectlyAnsweredQuestions(count);
//                } else if (value.equals(Question.isCorrect.FALSE)) {
//                    int count = quizSub.getIncorrectAnsweredQuestions() + 1;
//                    quizSub.setIncorrectAnsweredQuestions(count);
//                } else {
//                    int count = quizSub.getUnattemptedQuestions() + 1;
//                    quizSub.setUnattemptedQuestions(count);
//                }
//            });
            quizSub.setQuizEndTime(new Date());
            quizSub.setUserName(loadByUserName(httpServletRequest));
            int total = quizSub.getCorrectlyAnsweredQuestions() + quizSub.getIncorrectAnsweredQuestions()
                    + quizSub.getUnattemptedQuestions();
            quizSub.setTotalQuestions(total);
            QuizSubmissionFeedback updatedSubmission = insertQuizSubmission(quizSub);
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.setUsername(updatedSubmission.getUserName());
            leaderBoard.setQuizCode(updatedSubmission.getQuizCode());
            leaderBoard.setQuizTakenTime(updatedSubmission.getQuizEndTime());
            leaderBoard.setUserPoints(updatedSubmission.getTotalPointsScored());
            playQuizLeaderBoardFeign(leaderBoard);

            return quizSub;
        }
    }

//  implemented pagination and get all the quizSubmissions
@Override
public List<QuizSubmissionFeedback> getAllQuizSubmissions(int page, int limit) throws QuizPlayException {
    Pageable pageable = PageRequest.of(page, limit);
    return quizRepo.findAll(pageable).getContent();
}
    //get quiz submission data by username this method is for postman data for temp
    @Override
    public QuizSubmissionFeedback makeQuizSubmission(QuizSubmissionFeedback quizSubmission) {
        UUID uuid = UUID.randomUUID();
        quizSubmission.setSubmissionId(uuid);
        QuizSubmissionFeedback savedSubmission = quizRepo.save(quizSubmission);
        return savedSubmission;
    }

// get quiz submission data by user name
    @Override
    public ArrayList<QuizSubmissionFeedback> getAllQuizzesByUserName(Integer page, Integer limit, HttpServletRequest httpServletRequest) throws QuizPlayException {

        String userName = loadByUserName(httpServletRequest);
        PageRequest pageable =  PageRequest.of(page, limit);

        if (quizRepo.findByUserName(userName, pageable).isEmpty())
            throw new QuizPlayException("There is no data in the system with this userName");

        return quizRepo.findByUserName(userName, pageable);
//        return (ArrayList<QuizSubmissionFeedback>) quizRepo.findByUserName(userName);
    }

//get quiz by quizCode as a responseEntity using feign client and map it to Quiz Object -
// - using object mapper
    @Override
    public Quiz playQuizFeign(String quizCode) throws QuizPlayException {
        if (proxy.getQuizList(null,null,null,quizCode,null,null,null).getBody().get("result") == null) {
            throw new QuizPlayException("There is no data in the system with this quizCode");
        }
        ObjectMapper mapper = new ObjectMapper();
       ArrayList<Quiz> quizzes = (ArrayList<Quiz>) proxy.getQuizList(null,null,null,quizCode,null,null,null).getBody().get("result");
        Quiz response = mapper.convertValue(quizzes.get(0),Quiz.class);
        System.out.println("quiz Object "+response);
        return response;
    }

    @Override
    public ArrayList<QuizSectionQuestions> getSectionQuestions(String quizCode, String sectionTitle) {
//        ObjectMapper mapper = new ObjectMapper();
        ArrayList response = (ArrayList) proxy.getAllQuizSectionQuestion(quizCode, sectionTitle).getBody().get("result");
        return response;
    }

//get question by questionId as a responseEntity using feign client and map it to Question Object -
// - using object mapper
    @Override
    public Question playQuizQuestionFeign(UUID questionId) throws QuizPlayException {
        ObjectMapper mapper = new ObjectMapper();
        Question response = mapper.convertValue(qproxy.getQuestionById(questionId).getBody().get("result"), Question.class);
        return response;

    }
    // leader board feign service method
    @Override
    public ResponseEntity<?> playQuizLeaderBoardFeign(LeaderBoard leaderBoard) throws QuizPlayException{
        ResponseEntity<?> response = leaderBoardServiceProxy.saveNewUserInLeaderBoard(leaderBoard);
        return response;
    }

//get quizSubmission by submissionid
    @Override
    public QuizSubmissionFeedback findBySubmissionId(UUID submissionId) throws QuizPlayException {
        return quizRepo.findBySubmissionId(submissionId);
    }

    @Override
    public Question evaluate(String quizCode, String sectionTitle, UUID questionId, UUID submissionId,int sequence, QuestionResponse questionResponse, HttpServletRequest httpServletRequest) throws QuizPlayException {

        String userName = loadByUserName(httpServletRequest);
        System.out.println("inside evaluation");
        QuizSectionQuestions quizSectionQuestion = null;
        if(questionId!=null)
        quizSectionQuestion = proxy.getQuizSectionQuestion(quizCode,sectionTitle,questionId);
        QuizSubmissionQuestions quizSubQues = null;
        if(questionId!=null)
        quizSubQues = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndQuestionId(submissionId,sectionTitle,questionId);

        Question question = null;
        if(questionId!=null)
        question = quizSubQues.getQuestion();
        Question nextQuestion = null;
        QuizSubmissionFeedback quizSubmissionFeedback = quizRepo.findBySubmissionId(submissionId);
        ArrayList<AnswerOptions> answers = questionResponse.getAnswerOptions();
        System.out.println("answer array "+answers);
        ConfidenceWager confidence = questionResponse.getConfidenceWager();
        boolean bool = Boolean.parseBoolean(null);
        if (answers == null) {
            if(questionId!=null)
            quizSubQues.setSkipped(true);
            UUID nextQuestionId = null;
            if(questionId!=null)
            nextQuestionId = quizSectionQuestion.getAlternativeQuestion();
            System.out.println("questionId"+ nextQuestionId);
            if(nextQuestionId!=null) {
                nextQuestion = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndQuestionId(submissionId,sectionTitle,nextQuestionId).getQuestion();
            } else
            {
                System.out.println("questionId"+ nextQuestionId);
                QuizSubmissionQuestions quizSubmissionQuestions = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndSequence(submissionId, sectionTitle, sequence);
                if(quizSubmissionQuestions!=null)
                nextQuestion = quizSubmissionQuestions.getQuestion();
            }
            if(questionId!=null)
            quizSubmissionFeedback.setUnattemptedQuestions(quizSubmissionFeedback.getUnattemptedQuestions()+1);
//            System.out.println(" alternative question "+nextQuestionId)
        } else {
            bool = true;
            //relationship user [asnwered] question created here..
            mapUserToQuizServiceProxy.userAnsweredQuestion( userName, questionId);
            ArrayList<String> answerKeys = question.getAnswerKeys();
            if(answerKeys.size()!=answers.size())
                bool = false;
                for (int i = 0; i < answers.size(); i++) {
                    System.out.println("answer " + answers.get(i));
                    if (!answerKeys.contains(answers.get(i).getOptionContent())) {
                        bool = false;
                        break;
                    }
                }
            ArrayList<String> userAnswerContent = new ArrayList<>();
            answers.forEach((answer)-> userAnswerContent.add(answer.getOptionContent()));
            quizSubQues.setUserAnswerContent(userAnswerContent);
            if (confidence != null)
                quizSubQues.setWagerLabel(confidence.getLabel());
            if (bool) {
                // Semantic service methode called to map the user [answered] a question..
                quizSubmissionFeedback.setCorrectlyAnsweredQuestions(quizSubmissionFeedback.getCorrectlyAnsweredQuestions()+1);
                quizSubQues.setCorrect(true);
                UUID nextQuestionId = quizSectionQuestion.getFollowUpQuestion();
                System.out.println("questionId"+ nextQuestionId);
                if(nextQuestionId!=null) {
                    nextQuestion = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndQuestionId(submissionId,sectionTitle,nextQuestionId).getQuestion();
                } else
                {
                    System.out.println("questionId"+ nextQuestionId);
                    QuizSubmissionQuestions quizSubmissionQuestions = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndSequence(submissionId, sectionTitle, sequence);
                    if(quizSubmissionQuestions!=null)
                    nextQuestion = quizSubmissionQuestions.getQuestion();
                }
                float questionScore = question.getMaxScore();
                if (confidence != null) {
                    quizSubQues.setWagerBonusScore(confidence.getBonusScore());
                    float bonusScore = confidence.getBonusScore();
                    float totalScore = questionScore+bonusScore;
                    quizSubQues.setScore(totalScore);
                    quizSubmissionFeedback.setTotalPointsScored(quizSubmissionFeedback.getTotalPointsScored()+totalScore);
                }
                else
                {
                    quizSubQues.setScore(questionScore);
                    quizSubmissionFeedback.setTotalPointsScored(quizSubmissionFeedback.getTotalPointsScored()+questionScore);
                }
            } else {
                quizSubmissionFeedback.setIncorrectAnsweredQuestions(quizSubmissionFeedback.getIncorrectAnsweredQuestions()+1);
                UUID nextQuestionId = quizSectionQuestion.getDiagonisticQuestion();
                System.out.println("questionId"+ nextQuestionId);
                if(nextQuestionId!=null) {
                    nextQuestion = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndQuestionId(submissionId,sectionTitle,nextQuestionId).getQuestion();
                } else
                {
                    System.out.println("questionId"+ nextQuestionId);
                    QuizSubmissionQuestions quizSubmissionQuestions = quizSubQuesRepo.findBySubmissionIdAndSectionTitleAndSequence(submissionId, sectionTitle, sequence);
                    if(quizSubmissionQuestions!=null)
                    nextQuestion = quizSubmissionQuestions.getQuestion();
                }
                quizSubQues.setCorrect(false);
                if (confidence != null) {
                    quizSubQues.setWagerPenaltyScore(confidence.getPenaltyScore());
                    float score = -1*confidence.getPenaltyScore();
                    quizSubQues.setScore(score);
                    quizSubmissionFeedback.setTotalPointsScored(quizSubmissionFeedback.getTotalPointsScored()+score);
                }
            }
        }
        if(questionId!=null) {
            insertQuizSubmissionQuestions(quizSubQues);
            quizRepo.save(quizSubmissionFeedback);
        }
        return nextQuestion;
    }

    @Override
    public int countOfQuizSubmissionsByUserName(HttpServletRequest httpServletRequest) {
        String username = loadByUserName(httpServletRequest);
        int count = quizRepo.findByUserName(username).size();
        return count;
    }


    public String loadByUserName(HttpServletRequest httpServletRequest) {
        System.out.println("inside load byt username");
        String username = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals("JWT_TOKEN")) {
                username = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(cookie.getValue()).getBody()
                        .get("username", String.class);
            }
        }
        return username;
    }
    public int countOfQuizSubmissions() {
        return quizRepo.findAll().size();
    }

}
