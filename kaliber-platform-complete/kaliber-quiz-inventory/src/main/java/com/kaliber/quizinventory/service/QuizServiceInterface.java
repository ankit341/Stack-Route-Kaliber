

package com.kaliber.quizinventory.service;
import com.kaliber.quizinventory.exception.QuizNotFoundException;
import com.kaliber.quizinventory.model.Quiz;
import com.kaliber.quizinventory.model.ReportQuiz;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface QuizServiceInterface
{
    List<Quiz> findAll(Integer page, Integer limit) throws QuizNotFoundException;
    Quiz save(Quiz quiz) throws QuizNotFoundException;
         Quiz findByQuizCode(String quizCode) throws QuizNotFoundException;
//     ArrayList<Quiz> findByCreatedBy(String createdBy) throws QuizNotFoundException;
//     void delete(String quizCode) throws QuizNotFoundException;
//     Quiz modify(Quiz quiz, String quizCode) throws QuizNotFoundException;​

    Quiz modify(Quiz quiz, String quizCode) throws QuizNotFoundException;
   int getCountOfQuizzes();
    ArrayList<Quiz> getByQuizList(Integer page, Integer limit, String title, String quizCode, ArrayList<String> concepts, String authoredBy, String subject, Quiz.statusValue statusvalue)
            throws QuizNotFoundException;

    Quiz changeStatus(String quizCode, Quiz.statusValue statusValue) throws QuizNotFoundException;

    HashMap<String,ArrayList<String>> getQuizFeed(String username, HttpServletRequest httpServletRequest);
    Quiz reportQuiz(String quizCode, ReportQuiz report, HttpServletRequest httpServletRequest) throws QuizNotFoundException;

}