package com.kaliber.quizinventory.service;

import com.kaliber.quizinventory.exception.QuizSectionQuestionNotFoundException;
import com.kaliber.quizinventory.model.QuizSectionQuestion;

import java.util.ArrayList;
import java.util.UUID;

public interface QuizSectionQuestionServiceInterface
{
     ArrayList<QuizSectionQuestion> findAll() throws QuizSectionQuestionNotFoundException;
     ArrayList<QuizSectionQuestion> findByQuizCodeAndSectionTitle(String quizCode, String sectionTitle)
            throws QuizSectionQuestionNotFoundException;
     QuizSectionQuestion save(QuizSectionQuestion quizSectionQuestion)
            throws QuizSectionQuestionNotFoundException;

     ArrayList<QuizSectionQuestion> findByQuizCode(String quizCode);
     QuizSectionQuestion findByQuizCodeAndSectionTitleAndQuestionId(String quizCode, String sectionTitle, UUID questionId);
}
