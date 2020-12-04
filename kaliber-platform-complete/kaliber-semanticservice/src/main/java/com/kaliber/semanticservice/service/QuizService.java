package com.kaliber.semanticservice.service;

import com.kaliber.semanticservice.model.Quiz;
import com.kaliber.semanticservice.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    //Create Quiz Node
    public Quiz createQuizNode(String quizCode) {
        return quizRepo.createQuizNode(quizCode);
    }

    //Map Quiz To Subject.
    public void mapQuizToSubject(String quizCode, String subject) {
        quizRepo.mapQuizToSubject(quizCode, subject);
    }

    //Map quiz to Concept with Quiz [HAS_QUESTION_ON] Concept.
    public void quizHasQuestionOn(String quizCode, String[] concept) {
        quizRepo.quizHasQuestionOn(quizCode,concept);
    }

    //To get the trending Quizzes.....
    public ArrayList<HashMap<String, String>> getTrendingQuizzes() {
        return quizRepo.getTrendingQuizzes();
    }



}
