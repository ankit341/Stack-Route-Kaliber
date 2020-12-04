
package com.kaliber.quizinventory.controller;

import com.kaliber.quizinventory.exception.QuizNotFoundException;
import com.kaliber.quizinventory.model.Quiz;
import com.kaliber.quizinventory.model.ReportQuiz;
import com.kaliber.quizinventory.service.QuizService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class QuizController {

    final Logger log =LogManager.getLogger(QuizController.class);

    @Autowired
    private QuizService quizService;

    /* This method is to get all the quizzes present, parameters page limit are also there,
     default value of page =0, and limit = 10, returns an object of response entity
     with 10 quizzes
     */
//    @GetMapping("/quizzes")
//    public ResponseEntity<?> getAllQuizes(@RequestParam(name="page",required = false) Integer page,
//                                          @RequestParam(name="limit",required = false) Integer limit
//    ) throws QuizNotFoundException {
//
//
//        if(page == null && limit == null) {
//            page=0;
//            limit=20;
//        }
//        HashMap<String, Object> response= new HashMap<>();
//
//        int totalSize = quizService.getCountOfQuizzes();
//        // Call the service to get total number of quizzes in the database
//        // Call the service which returns the quizzes passing page and limit
//        try {
//            List<Quiz> quizzes = quizService.findAll(page, limit);
//            response.put("message","Successfully reterived all quizzes");
//            response.put("count",totalSize);
//            response.put("result", quizzes);
//            response.put("status", HttpStatus.OK);
//        }
//        catch(QuizNotFoundException qe)
//        {
//            log.error(qe.getMessage());
//
//            response.put("message","Could not reterieve all quizzes");
//            response.put("count",totalSize);
//            response.put("result", new ArrayList<>());
//            response.put("status", HttpStatus.OK);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    /* This method is to post quiz, returns an object of response entity
        of the quiz that is posted
     */
    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> postAllQuizes(@RequestBody Quiz quiz) {
        HashMap<String, Object> response= new HashMap<>();

        try {
            if(quiz.toString().isEmpty())
                throw new QuizNotFoundException("there is no quiz object");
            System.out.println(quiz);
            quizService.save(quiz);

            response.put("message","Successfully posted the new quiz");
            response.put("result", quiz);
            response.put("status", HttpStatus.OK);
        }
        catch(QuizNotFoundException e)
        {
            log.error("Quiz not found, exception occured "+e);
            response.put("message","Quiz not posted ");
            response.put("result", quiz);
            response.put("status", HttpStatus.OK);
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Quiz>(quiz,HttpStatus.OK);
    }

	/* This method is to get quiz based on quizCode which will be unique,  returns an object of response entity
		with the particular quiz
         */
//	@GetMapping("/quizzes/{quizcode}")
//	public ResponseEntity<Map<String, Object>> getQuizByCode(@PathVariable("quizcode") String quizCode) {
//		Quiz quiz;
//        Map<String, Object> response= new HashMap<>();
//		try {
//			quiz=  quizService.findByQuizCode(quizCode);
//
//			System.out.println("In QUIZ INVENTORY AND QUIZ IS " + quiz);
//
//			if(quiz==null)
//			{
//				System.out.println("NO QUIZZES FOUND");
//				throw new QuizNotFoundException("No quizzes found");
//			}
//            response.put("message","Quiz found by quizCode");
//            response.put("result", quiz);
//            response.put("status", HttpStatus.OK);
//			}
//			catch(QuizNotFoundException e)
//            {
//				System.out.println("EXCEPTION HERE");
//				log.error("Exception occured "+e);
//                response.put("message","Quiz not found");
//                response.put("result", new ArrayList<>());
//                response.put("status", HttpStatus.OK);
////				return new ResponseEntity<Quiz>(HttpStatus.NOT_FOUND);
//
//			}
////        return new ResponseEntity<Quiz>(response, HttpStatus.OK);
//		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
//
//
//	}

//	@GetMapping("/quizzes/creatorname")
//	public ResponseEntity<Map<String,Object>> getAllQuizesByCreater(@RequestParam(name = "creatorname") String createdBy) {
//
//		ArrayList<Quiz> quizArray;
//        Map<String, Object> response= new HashMap<>();
//		try {
//		quizArray= (ArrayList<Quiz>) quizService.findByCreatedBy(createdBy);
//		if(quizArray.isEmpty()) {
//			throw new QuizNotFoundException("No quizzes found by creater"+createdBy);
//		}
//            response.put("message","Quiz found by createrName");
//            response.put("result", quizArray);
//            response.put("status", HttpStatus.OK);
//		}
//		catch(QuizNotFoundException e) {
//			log.error("Exception occured "+e);
//            response.put("message","Quiz not found");
//            response.put("result", new ArrayList<>());
//            response.put("status", HttpStatus.OK);
//		}
//		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
//	}

    /* This method is to update a quiz present with the quizcode,  returns an object of response entity
        with the updated quiz
         */
    @PutMapping( "/quizzes/{quizcode}")
    public ResponseEntity modifyQuizByquizcode(@PathVariable("quizcode") String quizCode,  @RequestBody Quiz quiz) {
        HashMap<String, Object> response= new HashMap<>();
        try {
//            quiz.setQuizCode(quizCode);
            Quiz savedQuiz = quizService.modify(quiz,quizCode);
            response.put("message","Quiz modified by quizCode");
            response.put("result", savedQuiz);
            response.put("status", HttpStatus.OK);

        }
        catch(Exception e) {
            log.error("no quiz object");
            response.put("message","Quiz not found to modify");
            response.put("result", new ArrayList<Quiz>());
            response.put("status", HttpStatus.OK);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<HashMap<String,Object>> getQuizList(@RequestParam(name="page",required = false) Integer page,
                                         @RequestParam(name="limit",required = false) Integer limit,
                                         @RequestParam(name="title",required = false) String title,
                                         @RequestParam(name="quizCode",required = false) String quizCode,
                                         @RequestParam(name="concepts",required = false) ArrayList<String> concepts,
                                         @RequestParam(name="authoredBy",required = false) String authoredBy,
                                         @RequestParam(name="subject",required = false) String subject,
                                                              @RequestParam(name="statusvalue",required = false) Quiz.statusValue statusvalue
    ) throws QuizNotFoundException {

        if(page == null && limit == null) {
            page=0;
            limit=20;
        }
        HashMap<String, Object> response= new HashMap<>();

        ArrayList<Quiz> quizzes= quizService.getByQuizList(page,limit,title, quizCode, concepts, authoredBy, subject,statusvalue);
        response.put("count", quizzes.size());
        response.put("result", quizzes);


        return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.OK);
    }

    @PatchMapping("/quizzes/{quizCode}/status/{statusValue}")
    public ResponseEntity<?> changeStatus (@PathVariable("quizCode") String quizCode,
                                           @PathVariable("statusValue") Quiz.statusValue statusValue) throws QuizNotFoundException {
        HashMap<String, Object> response = new HashMap<>();
        try{
            Quiz modifiedQuiz = quizService.changeStatus(quizCode, statusValue);
            response.put("result", modifiedQuiz);
            response.put("count", 1);
            response.put("status", HttpStatus.OK);
            response.put("error", false);
            response.put("message", "Successfully updated the status of the quiz");
        } catch (QuizNotFoundException e) {
            response.put("result", new ArrayList<String>());
            response.put("count", 0);
            response.put("status", HttpStatus.OK);
            response.put("error", true);
            response.put("message", "Status of the quiz cannot be changed as the quiz does not exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/quizzes/{quizCode}/report")
    public ResponseEntity<?> reportQuiz (@PathVariable("quizCode") String quizCode,
                                         @RequestBody ReportQuiz report,
                                         HttpServletRequest httpServletRequest) {
//            quizService.reportQuiz(quizCode, report, httpServletRequest);
        HashMap<String, Object> response = new HashMap<>();


        try{
            Quiz modifiedQuiz = quizService.reportQuiz(quizCode, report, httpServletRequest);
            System.out.println("hell" + modifiedQuiz.getReportQuiz());
            System.out.println("heloool" + modifiedQuiz.toString());

            response.put("result", modifiedQuiz);
            response.put("count", 1);
            response.put("status", HttpStatus.OK);
            response.put("error", false);
            response.put("message", "Successfully updated the status of the quiz");
        } catch (QuizNotFoundException e) {
            response.put("result", new ArrayList<String>());
            response.put("count", 0);
            response.put("status", HttpStatus.OK);
            response.put("error", true);
            response.put("message", "Status of the quiz cannot be changed as the quiz does not exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);


    }


    @GetMapping("/quizfeed/{username}")
    public ResponseEntity<?> getQuizFeed(@PathVariable String username, HttpServletRequest httpServletRequest) {
       quizService.getQuizFeed(username, httpServletRequest);
        HashMap<String, Object> response = new HashMap<>();
        try{
            HashMap<String,ArrayList<String>> personalizedquizzes = quizService.getQuizFeed(username, httpServletRequest);
            response.put("result", personalizedquizzes);
            response.put("count", personalizedquizzes.size());
            response.put("status", HttpStatus.OK);
            response.put("error", false);
            response.put("message", "Successfully updated the status of the quiz");
        } catch (Exception e) {
            response.put("result", new ArrayList<String>());
            response.put("count", 0);
            response.put("status", HttpStatus.OK);
            response.put("error", true);
            response.put("message", "Status of the quiz cannot be changed as the quiz does not exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/suggestedquizfeed/{username}")
//    public ResponseEntity<?> getQuizPlayedByPeers(@PathVariable String username) {
//        quizService.getQuizPlayedByPeers(username);
//        HashMap<String, Object> response = new HashMap<>();
//        try{
//            HashMap<String,ArrayList<User>> modifiedQuiz = quizService.getQuizPlayedByPeers(username);
//            System.out.println("quiz feed data bsed on peers:: -->" + modifiedQuiz);
//            response.put("result", modifiedQuiz);
//            response.put("count", 1);
//            response.put("status", HttpStatus.OK);
//            response.put("error", false);
//            response.put("message", "Successfully updated the status of the quiz");
//        } catch (Exception e) {
//            response.put("result", new ArrayList<String>());
//            response.put("count", 0);
//            response.put("status", HttpStatus.OK);
//            response.put("error", true);
//            response.put("message", "Status of the quiz cannot be changed as the quiz does not exist");
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


}