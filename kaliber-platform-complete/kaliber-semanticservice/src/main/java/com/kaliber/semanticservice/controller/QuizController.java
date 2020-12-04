package com.kaliber.semanticservice.controller;

import com.kaliber.semanticservice.model.Quiz;
import com.kaliber.semanticservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class QuizController {

    @Autowired
    QuizService quizService;

    //Create Quiz node if the node is not existing
    @PostMapping("/quizzes")
    public Quiz createQuizNode(@RequestParam(value = "quizCode") String quizCode) {
        return quizService.createQuizNode(quizCode);
    }

    //Map Quiz to a Subject
    @PostMapping("/map-quiz-to-subject")
    public void mapQuizToSubject(@RequestParam(value = "quizCode") String quizCode,
                                 @RequestParam(value = "subject") String subject) {
        quizService.mapQuizToSubject(quizCode,subject);
    }


    //Map Quiz to a Concept [HAS_QUESTION_ON]
    @PostMapping("/map-quiz-to-concept")
    public void quizHasQuestionOn(@RequestParam(value = "quizCode") String quizCode,
                                 @RequestBody String[] concept) {
        quizService.quizHasQuestionOn(quizCode,concept);
    }


    //For Quiz Feed..
    //Fetch top 5 trending quizzes
    @GetMapping("/trending/quizzes")
    public ArrayList<HashMap<String, String>> getTrendingQuizzes(){
        return quizService.getTrendingQuizzes();
    }



}
