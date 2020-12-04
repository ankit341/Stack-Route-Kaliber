package com.kaliber.quizplay.controller;

import com.kaliber.quizplay.exception.QuizPlayException;
import com.kaliber.quizplay.model.Quiz;
import com.kaliber.quizplay.model.Question;
import com.kaliber.quizplay.model.QuestionResponse;
import com.kaliber.quizplay.model.QuizSectionQuestions;
import com.kaliber.quizplay.model.QuizSubmissionFeedback;
import com.kaliber.quizplay.model.QuizSubmissionQuestions;
import com.kaliber.quizplay.service.QuizPlayServiceMongoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

@RestController
public class QuizPlayController {


    @Autowired
    private QuizPlayServiceMongoImpl quizplayservice;


    private static Logger logger = LogManager.getLogger(QuizPlayController.class);

    HashMap<Integer, Question> map;
    HashMap<Integer, Question.isCorrect> responses;
    String quizCode;

//A post mapping call when user starts quiz from quizLobby
    @PostMapping("/quizzes/{quizCode}/startQuiz")
    public ResponseEntity<Map<String, Object>> startQuiz(@PathVariable String quizCode, HttpServletRequest httpServletRequest) {
		UUID submissionId = quizplayservice.startQuiz(quizCode, httpServletRequest);
		Map<String, Object> map = new HashMap<>();
		map.put("error", false);
		map.put("message", "successfully started quiz");
		map.put("result", submissionId);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }


//get quiz Submissions by user name

// A Get API call to get all quizSubmissions of a username
//    @GetMapping("/quizSubmissions/{userName}")
//    public ResponseEntity<Map<String, Object>> getAllQuizzesByUserName(@PathVariable String userName,
//                                                                       @RequestParam(value = "page", required = false) Integer page,
//                                                                       @RequestParam(value = "limit", required = false) Integer limit) {
//
//        if(page == null && limit == null) {
//            page = 0;
//            limit = 10;
//        }
//
//        ArrayList<QuizSubmissionFeedback> quizSubArray;
//        Map<String, Object> map = new HashMap<>();
//        try {
//            quizSubArray = (ArrayList<QuizSubmissionFeedback>) quizplayservice.getAllQuizzesByUserName(userName, page, limit);
//            map.put("error", false);
//            map.put("message", "successfully recieved quizSubmissions");
//            map.put("result", quizSubArray);
//
//        } catch (QuizPlayException quizplayexception) {
//            logger.error("Couldn't  find data with this userName");
//            map.put("error", true);
//            map.put("errorMessage", "Couldn't  find data with this userName");
//            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
//        }
//        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
//    }

    @GetMapping("/quizSubmissions/user")
    public ResponseEntity<Map<String, Object>> getAllQuizSubmissionsByUserName(@RequestParam(value = "page", required = false) Integer page,
                                                                               @RequestParam(value = "limit", required = false) Integer limit,
                                                                               HttpServletRequest httpServletRequest) throws QuizPlayException
    {
        if(page == null && limit == null) {
            page = 0;
            limit = 10;
        }
        HashMap<String, Object> response = new HashMap<>();
        try{
            int totalCount = quizplayservice.countOfQuizSubmissionsByUserName(httpServletRequest);
            ArrayList<QuizSubmissionFeedback> userQuizSubmissions = quizplayservice.getAllQuizzesByUserName(page, limit, httpServletRequest);
            response.put("count", totalCount);
            response.put("result", userQuizSubmissions);
            response.put("message", "Successfully retrieved quizSubmissions by username");
            response.put("status", HttpStatus.OK);
        }
        catch (QuizPlayException e) {
            response.put("count", 0);
            response.put("result", new ArrayList<>());
            response.put("message", "Could not retrieve quizSubmissions by username beacuse of the reason " + e.getMessage());
            response.put("status", HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/quizSubmissions/quizzes/{quizCode}/{submissionId}")
    public ResponseEntity<Map<String, Object>> createQuizsubmission(@PathVariable String quizCode, @PathVariable UUID submissionId, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        try {
            QuizSubmissionFeedback quizSub = quizplayservice.createQuizsubmission(quizCode, submissionId, httpServletRequest);
            map.put("error", false);
            map.put("message", "successfully posted quizSubmissions");
            map.put("result", quizSub);
        } catch (QuizPlayException quizplayexception) {
            logger.error("Couldn't post data in QuizSubmission Entity");
            return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

    }

    //A Get API  call to get all quizSubmissionQuestions by submissionId
    @GetMapping("/quizSubmissionQuestions")
    public ResponseEntity<Map<String, Object>> getQuizSubmissionQuestions(@RequestParam(name = "submissionId") UUID submissionId) {
        ArrayList<QuizSubmissionQuestions> quizSubmissionQuestions = quizplayservice.getQuizSubmissionQuestions(submissionId);
        Map<String, Object> map = new HashMap<>();
        map.put("error", false);
        map.put("message", "succesfully recieved quizSubmission object");
        map.put("result", quizSubmissionQuestions);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    //A Get API call to get all quizSectionQuestion objects
    @GetMapping("/quizzes/{quizcode}/sections/{sectiontitle}")
    public ResponseEntity<Map<String, Object>> getAllQuizSectionQuestion(@PathVariable(name = "quizcode") String quizCode, @PathVariable(name = "sectiontitle") String sectionTitle) {
        ArrayList<QuizSectionQuestions> quesArray = quizplayservice.getSectionQuestions(quizCode, sectionTitle);
        Map<String, Object> map = new HashMap<>();
        map.put("error", false);
        map.put("message", "succesfully recieved quizSections object");
        map.put("result", quesArray);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }


// A Get API call to get All quizSubmissions with pagination
@GetMapping("/quizSubmissions")
public ResponseEntity<?> getAllQuizSubmissions(@RequestParam(value = "page", required=false) Integer page,
                                      @RequestParam(value = "limit", required=false) Integer limit
) throws QuizPlayException {

    if (page == null && limit == null) {
        page = 0;
        limit = 20;
    }
    //Call the service method to get the count of total quizSubmissions
    int totalSubmissionsCount = quizplayservice.countOfQuizSubmissions();

    //Call the paginated method
    List<QuizSubmissionFeedback> submissions = quizplayservice.getAllQuizSubmissions(page, limit);

    Map<String, Object> response = new HashMap<>();

    response.put("message", "Successfully retrieved quiz Submissions");
    response.put("result", submissions);
    response.put("count", totalSubmissionsCount);
    response.put("status", HttpStatus.OK);
    return new ResponseEntity<>(response, HttpStatus.OK);


}

//A Get API call to get a quiz by quizCode
    @GetMapping("/quizzes/{quizCode}")
    public ResponseEntity<Map<String, Object>> playQuizFeign(@PathVariable String quizCode) {
        Quiz quiz;
        Map<String, Object> map = new HashMap<>();
        try {
            logger.info("IN GET API");
            quiz = quizplayservice.playQuizFeign(quizCode);
            logger.info("QUIZ IS " + quiz);
            map.put("error", false);
            map.put("message", "succesfully recieved quiz object");
            map.put("result", quiz);
        } catch (QuizPlayException quizplayexception) {
            logger.error("Couldn't get quiz object from quiz-inventory with this quizCode");
            map.put("error", true);
            map.put("errorMessage", "Couldn't get quiz object from quiz-inventory with this quizcode");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    //A Get API call to get quizsubmissions by submissionId
	@GetMapping("/quizsubmissions/feedback/{submissionId}")
	public ResponseEntity<Map<String,Object>> getQuizSubmissionById(@PathVariable UUID submissionId){
    	Map<String,Object> map = new HashMap<>();
    	QuizSubmissionFeedback quizSub;
    	try{
    		quizSub = quizplayservice.findBySubmissionId(submissionId);
			logger.info("QUIZ IS "+quizSub);
			map.put("error", false);
			map.put("message", "succesfully recieved quiz object");
			map.put("result",quizSub);
		}
		catch (QuizPlayException quizplayexception) {
			logger.error("Couldn't get quiz submission data from quiz-play with this submissionId");
			map.put("error", true);
			map.put("errorMessage", "Couldn't get quiz submission data from quiz-play with this submissionId");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}

	//A Get API call to get a question by questionId
    @GetMapping("/questions/{questionId}")
    public ResponseEntity<Map<String, Object>> playQuizQuestionFeign(@PathVariable UUID questionId) {

        Question question;
        Map<String, Object> map = new HashMap<>();
        try {
            question = quizplayservice.playQuizQuestionFeign(questionId);
            map.put("error", false);
            map.put("message", "succesfully recieved question object");
            map.put("result", question);
        } catch (QuizPlayException quizplayexception) {
            logger.error("Couldn't get question object from question-invetory with this questionId");
            map.put("error", true);
            map.put("errorMessage", "Couldn't get question object from question-inventory with this questionId");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @PutMapping("/quizzes/{quizcode}/sections/{sectionTitle}/questions/sequence/{sequence}/{submissionId}/quizsubmissionquestions")
    public ResponseEntity<Map<String, Object>> evaluate(@PathVariable String quizcode, @PathVariable String sectionTitle, @PathVariable UUID submissionId,@PathVariable int sequence,@RequestBody QuestionResponse questionResponse, HttpServletRequest httpServletRequest) throws QuizPlayException {
        UUID questionId = questionResponse.getQuestionId();
        Question nextQuestion = quizplayservice.evaluate(quizcode, sectionTitle, questionId, submissionId, sequence, questionResponse , httpServletRequest);
        Question question = new Question();
        if(nextQuestion!=null) {
            question.setQuestionId(nextQuestion.getQuestionId());
            question.setAnswerOptions(nextQuestion.getAnswerOptions());
            question.setConcepts(nextQuestion.getConcepts());
            question.setCorrectResponse(nextQuestion.getCorrectResponse());
            question.setCreatedBy(nextQuestion.getCreatedBy());
            question.setDifficultyLevel(nextQuestion.getDifficultyLevel());
            question.setFeedbackContent(nextQuestion.getFeedbackContent());
            question.setHintContent(nextQuestion.getHintContent());
            question.setMaxDurationMins(nextQuestion.getMaxDurationMins());
            question.setQuestionTitle(nextQuestion.getQuestionTitle());
            question.setQuestionType(nextQuestion.getQuestionType());
            question.setSubject(nextQuestion.getSubject());
            question.setMaxScore(nextQuestion.getMaxScore());
            question.setQuestionContent(nextQuestion.getQuestionContent());
            question.setRandomiseOptions(nextQuestion.isRandomiseOptions());
            question.setStatus(nextQuestion.getStatus());
            question.setTimesAttempted(nextQuestion.getTimesAttempted());
            question.setUpdatedBy(nextQuestion.getUpdatedBy());
            question.setUpdatedOn(nextQuestion.getUpdatedOn());
            question.setCreatedOn(nextQuestion.getCreatedOn());
        }
        else
        {
            question = null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", question);
        map.put("error", false);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    // API to post quiz submission data into DB through postman for demo purpose only
    @PostMapping("/quizSubmissions/samples")
    public QuizSubmissionFeedback createQuizSubmission(@RequestBody QuizSubmissionFeedback quizSubmission) {
        logger.info("In POST Mapping");
        QuizSubmissionFeedback submission = quizplayservice.makeQuizSubmission(quizSubmission);
        System.out.println("submission is" + submission);
        return submission;
    }
}
