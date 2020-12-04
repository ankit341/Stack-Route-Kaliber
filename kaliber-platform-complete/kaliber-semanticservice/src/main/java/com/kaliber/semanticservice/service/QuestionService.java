package com.kaliber.semanticservice.service;

import com.kaliber.semanticservice.model.Question;
import com.kaliber.semanticservice.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public Question createQuestionNode(UUID questionId) {
        return questionRepo.createQuestionNode(questionId);
    }

//    public void questionAskedInQuiz(UUID questionId, String quizCode) {
//            questionRepo.questionAskedInQuiz(questionId,quizCode);
//    }
}
