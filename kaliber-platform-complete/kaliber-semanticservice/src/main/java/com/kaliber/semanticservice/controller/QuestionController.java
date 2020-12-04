package com.kaliber.semanticservice.controller;

import com.kaliber.semanticservice.model.Question;
import com.kaliber.semanticservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    //Create QUESTION node if the node is not existing
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestionNode(@RequestParam(value = "questionId") UUID questionId) {
        Question question = null;
        HashMap<String, Object> response= new HashMap<>();
        try {
            question = questionService.createQuestionNode(questionId);
            response.put("message","Successfully created question node: "+questionId);
            response.put("count", 1);
            response.put("result", question);
            response.put("statusCode", HttpStatus.OK);
        }catch (Exception qe) {
            response.put("message", "Can't create question node");
            response.put("count", 1);
            response.put("result", qe.getMessage());
            response.put("statusCode", HttpStatus.OK);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


//    @PostMapping("/questions/askedin")
//    public void questionAskedInQuiz(@RequestParam(value = "questionId") UUID questionId,
//                                    @RequestParam(value = "quizCode") String quizCode) {
//        System.out.println("In Semantic service controller...: " + questionId+ " "+ quizCode);
//        questionService.questionAskedInQuiz(questionId, quizCode);
//    }


    //Create QUESTION node if the node is not existing
//    @PostMapping("/questions")
//    public Question createQuestionNode(@RequestParam(value = "questionId") UUID questionId) {
//        return questionService.createQuestionNode(questionId);
//    }
}
