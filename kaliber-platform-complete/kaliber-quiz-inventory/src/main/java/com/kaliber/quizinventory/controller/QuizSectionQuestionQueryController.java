package com.kaliber.quizinventory.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaliber.quizinventory.exception.QuizSectionQuestionQueryNotFoundException;
import com.kaliber.quizinventory.model.QuizSectionQuestionQuery;
import com.kaliber.quizinventory.service.QuizSectionQuestionQueryService;

@RestController
public class QuizSectionQuestionQueryController {
	
	final Logger log =LogManager.getLogger(QuizController.class);

		
	@Autowired
	private QuizSectionQuestionQueryService quizSectionService;
	
	@GetMapping("/quizsectionquestionquery/quizzes/{quizcode}")
	public ResponseEntity<?> getAll()
	{
		HashMap<String, Object> response= new HashMap<>();
		ArrayList<QuizSectionQuestionQuery> quizSectionQuestionQueryArray;
		try {
			quizSectionQuestionQueryArray= (ArrayList<QuizSectionQuestionQuery>) quizSectionService.findAll();
		if(quizSectionQuestionQueryArray.isEmpty()) {
			throw new QuizSectionQuestionQueryNotFoundException("No quizzesSectionQuestionQuery found");
		}
			response.put("message","Quiz section question found");
			response.put("result", quizSectionQuestionQueryArray);
			response.put("status", HttpStatus.OK);
		}
		catch(QuizSectionQuestionQueryNotFoundException e) {
			log.error("Exception occured "+e);
			response.put("message","Quiz section question not found");
			response.put("result", new ArrayList<QuizSectionQuestionQuery>());
			response.put("status", HttpStatus.OK);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	
	@PostMapping("/quizsectionquestionquery/quizzes/{quizcode}")
	public ResponseEntity<?> create(@RequestBody QuizSectionQuestionQuery quizSectionQuestionQuery) {
		HashMap<String, Object> response= new HashMap<>();
		QuizSectionQuestionQuery quiz=new QuizSectionQuestionQuery();
		try {
			if(quizSectionQuestionQuery.toString().isEmpty())
				throw new QuizSectionQuestionQueryNotFoundException("there is no quiz object");
			quizSectionService.save(quizSectionQuestionQuery);

			response.put("message","Quiz section question query post successfully");
			response.put("result", quiz);
			response.put("status", HttpStatus.OK);
		}
		catch(QuizSectionQuestionQueryNotFoundException e) {
			log.error("Quiz not found, exceptio occured " + e);
			response.put("message", "Quiz section question query not post");
			response.put("result", new QuizSectionQuestionQuery());
			response.put("status", HttpStatus.OK);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}


}
