package com.kaliber.quizinventory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.kaliber.quizinventory.exception.QuizSectionQuestionNotFoundException;
import com.kaliber.quizinventory.model.QuizSectionQuestion;
import com.kaliber.quizinventory.service.QuizSectionQuestionService;

@RestController
public class QuizSectionQuestionController {
	
	final Logger log =LogManager.getLogger(QuizController.class);

	
	@Autowired
	private QuizSectionQuestionService quizSectionQuestionService;
	
//	@GetMapping("/questions")
//	public ResponseEntity<ArrayList<QuizSectionQuestion>> getAll(){
//		ArrayList<QuizSectionQuestion> quizArray;
//		HashMap<String, Object> response= new HashMap<>();
//		int totalSize = quizSectionQuestionService.getCountOfQuestions();
//		try {
//		quizArray= (ArrayList<QuizSectionQuestion>) quizSectionQuestionService.findAll();
//		if(quizArray.isEmpty()) {
//			throw new QuizSectionQuestionNotFoundException("No quizzes found");
//		}
//			response.put("message","");
//			response.put("result", quizArray);
//			response.put("status", HttpStatus.OK);
//		}
//		catch(QuizSectionQuestionNotFoundException e) {
//			log.error("Exception occured "+e);
//			response.put("message","Quiz deleted by quizCode");
////			response.put("result", quizArray);
//			response.put("status", HttpStatus.OK);
//			return new ResponseEntity<ArrayList<QuizSectionQuestion>>(HttpStatus.NOT_FOUND);
//
//		}
//		return new ResponseEntity<ArrayList<QuizSectionQuestion>>(quizArray,HttpStatus.OK);
//	}

	/* This method is to get all the quizzes and the sections present according to the quiz code and section title,
	 returns an object of response entity with the quizzes and section title
	 */
	@GetMapping("/quizzes/{quizcode}/section/{sectiontitle}")
	public ResponseEntity<?> getAllQuizSectionQuestion(@PathVariable(name = "quizcode") String quizCode, @PathVariable(name="sectiontitle") String sectionTitle) {
		ArrayList<QuizSectionQuestion> quizArray;
		HashMap<String, Object> response= new HashMap<>();
		try {
		quizArray= (ArrayList<QuizSectionQuestion>) quizSectionQuestionService.findByQuizCodeAndSectionTitle(quizCode,sectionTitle);
		if(quizArray.isEmpty()) {
			throw new QuizSectionQuestionNotFoundException("No quizzes found");
		}
			response.put("message","Quiz found by quizCode and sectiontitle");
			response.put("result", quizArray);
			response.put("status", HttpStatus.OK);

		}
		catch(QuizSectionQuestionNotFoundException e) {
			log.error("Exception occured "+e);
			response.put("message","Quiz not found by quizCode and sectiontitle");
			response.put("result", new ArrayList<QuizSectionQuestion>());
			response.put("status", HttpStatus.OK);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);

	}
	/* This method is to post with the quiz and sections, returns an object of response entity
	 */
	@PostMapping("/questions/quizzes/sections")
	public ResponseEntity<?> postQuizSectionQuestion(@RequestBody QuizSectionQuestion quizSectionQuestion) throws QuizSectionQuestionNotFoundException {
		HashMap<String, Object> response= new HashMap<>();
		QuizSectionQuestion quiz=new QuizSectionQuestion();
		try {
			if(quizSectionQuestion.toString().isEmpty())
				throw new QuizSectionQuestionNotFoundException("there is no quiz object");
			quizSectionQuestionService.save(quizSectionQuestion);

			response.put("message","Questions post successfully to the section of the quiz");
			response.put("result", quiz);
			response.put("status", HttpStatus.OK);

		}
		catch(QuizSectionQuestionNotFoundException e)
		{
			log.error("QuizSectionQuestion not found, exceptio occured "+e);
			response.put("message","Questions not posted successfully to the section of the quiz");
			response.put("result", quiz);
			response.put("status", HttpStatus.OK);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@GetMapping("/quizzes/{quizCode}/sections")
	public ArrayList<QuizSectionQuestion> getAllQuizSectionQuestionsByQuizCode(@PathVariable String quizCode){

		return quizSectionQuestionService.findByQuizCode(quizCode);

	}

	@GetMapping("/quizzes/{quizCode}/sections/{sectionTitle}/questions/{questionId}")
	public QuizSectionQuestion getQuizSectionQuestion(@PathVariable String quizCode,@PathVariable String sectionTitle,@PathVariable UUID questionId){
		return quizSectionQuestionService.findByQuizCodeAndSectionTitleAndQuestionId(quizCode,sectionTitle,questionId);
	}
}








