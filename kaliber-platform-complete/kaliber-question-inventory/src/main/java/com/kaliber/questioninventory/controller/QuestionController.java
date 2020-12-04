package com.kaliber.questioninventory.controller;

import com.kaliber.questioninventory.exception.QuestionInventoryException;
import com.kaliber.questioninventory.model.Question;
import com.kaliber.questioninventory.service.QuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	public static Logger logger = LogManager.getLogger(QuestionService.class);

	//API is pagionated
	@GetMapping("/questions")
	public ResponseEntity<?> getAllQuestions(@RequestParam(name = "page", required = false) Integer page,
											 @RequestParam(name = "limit", required = false) Integer limit
	) throws QuestionInventoryException {

		List<Question> questions = null;

		HashMap<String, Object> response= new HashMap<>();

		if(page==null && limit == null){
			page=0;
			limit =100;
		}

		// To fetch the count of total number of questions in the database
		int totalSize = questionService.getCountOfQuestions();

		// Call the service which returns the questions passing page and limit
		try {
			questions = questionService.getAllQuestions(page, limit);
			response.put("message","Successfully reterived all questions");
			response.put("count", totalSize);
			response.put("result", questions);
			response.put("statusCode", HttpStatus.OK);
		}catch(QuestionInventoryException qe) {
			logger.error(qe.getMessage());
			response.put("message", "Something went wrong. No questions fetched.");
			response.put("count", totalSize);
			response.put("result", qe.getMessage());
			response.put("statusCode", HttpStatus.OK);
			return new ResponseEntity<>(response , HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/questions/{questionId}")
	public ResponseEntity<?> getQuestionById(@PathVariable("questionId") UUID questionId) {

		Question question =null;
		HashMap<String, Object> response= new HashMap<>();
		try {
			question = questionService.getQuestionById(questionId);
			response.put("message","Successfully reterived question with questionID: "+questionId);
			response.put("count", 1);
			response.put("result", question);
			response.put("statusCode", HttpStatus.OK);
		}catch (QuestionInventoryException qe) {
			logger.error(qe.getMessage());
			response.put("message", "Can't retreived the question with questionID: "+ questionId);
			response.put("count", 1);
			response.put("result", qe.getMessage());
			response.put("statusCode", HttpStatus.OK);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@PostMapping("/questions")
	public ResponseEntity<?> createQuestion(@RequestBody Question question) {
		Question questions = null;
		HashMap<String, Object> response= new HashMap<>();
		try {
			questions = questionService.createQuestion(question);
			response.put("message","Successfully posted question");
			response.put("count", 1);
			response.put("result", questions);
			response.put("statusCode", HttpStatus.OK);
		}catch(QuestionInventoryException qe) {
			logger.error(qe.getMessage());
			response.put("message", qe.getMessage());
			response.put("count", 1);
			response.put("result", questions);
			response.put("statusCode", HttpStatus.OK);
			return new ResponseEntity<>( response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = "/questions/{questionId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable UUID questionId) {
		HashMap<String, Object> response= new HashMap<>();
		try {
			questionService.deleteQuestion(questionId);
			response.put("message", "Question deleted successfully with questionID: " + questionId);
			response.put("count ", 1);
			response.put("QuestionId", questionId);
			response.put("statusCode", HttpStatus.OK);
		}catch(QuestionInventoryException qe){
			logger.error(qe.getMessage());
			response.put("message", "Something went wrong. Question not deleted.");
			response.put("count", 1);
			response.put("result", questionId);
			response.put("statusCode", HttpStatus.OK);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping(value = "/questions/{questionId}")
	public ResponseEntity<?> modifyQuestionById(@PathVariable("questionId") UUID questionId, @RequestBody Question question) {
		question.setQuestionId(questionId);
		HashMap<String, Object> response= new HashMap<>();
		try {
			questionService.modifyQuestionById(questionId, question);
			response.put("message","Question updated successfully with questionID :"+questionId);
			response.put("count", 1);
			response.put("result", questionService);
			response.put("statusCode", HttpStatus.OK);
		}catch (QuestionInventoryException qe) {
			logger.error(qe.getMessage());
			response.put("message","Something went wrong. Question not updated.");
			response.put("count", 1);
			response.put("result", questionId);
			response.put("statusCode", HttpStatus.OK);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
