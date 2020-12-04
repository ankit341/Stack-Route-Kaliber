package com.kaliber.quizinventory.serviceproxy;

import com.kaliber.quizinventory.model.Quiz;
import com.kaliber.quizinventory.model.Subject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@FeignClient(name = "kaliber-semanticservice")
public interface QuizServiceProxxy {

    //Create Quiz node if the node is not existing
    @PostMapping("/quizzes")
    Quiz createQuizNode(@RequestParam(value = "quizCode") String quizCode) ;

    //Create Subject node
    @PostMapping("/subjects")
    Subject createSubjectNode(@RequestParam(value = "subject") String subject);

    // Question -[ASKED_IN] Quiz relationship.
//    @PostMapping("/questions/askedin")
//    void questionAskedInQuiz(@RequestParam(value = "questionId") UUID questionId,
//                             @RequestParam(value = "quizCode") String quizCode );


    //Map Quiz to a Subject
    @PostMapping("/map-quiz-to-subject")
    void mapQuizToSubject(@RequestParam(value = "quizCode") String quizCode,
                                 @RequestParam(value = "subject") String subject);


    //Map Quiz to a Concept on [HAS_QUESTION_ON]
    @PostMapping("/map-quiz-to-concept")
    void quizHasQuestionOn(@RequestParam(value = "quizCode") String quizCode,
                                  @RequestBody String[] concept);


    //Get the peer taken quizzes for the logged in users
    @GetMapping("/users/quizzes/suggest")
    ArrayList<HashMap<String,String>> getSuggestedQuiz(@RequestParam("username") String username);


    //Get the quizzes for a logged in user based on topics of interest of the user
    @GetMapping("users/quizzes/on/followedsubjects")
    ArrayList<HashMap<String,String>> getQuizzesBasedOnSubjects(@RequestParam("username") String username);


    //Get the trending quizzes in the platform (top 5 played quizzes)
    @GetMapping("/trending/quizzes")
    ArrayList<HashMap<String,String>> getTrendingQuizzes();

}
