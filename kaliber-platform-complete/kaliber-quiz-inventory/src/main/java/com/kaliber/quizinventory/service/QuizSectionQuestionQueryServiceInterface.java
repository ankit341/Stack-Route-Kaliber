package com.kaliber.quizinventory.service;

import com.kaliber.quizinventory.exception.QuizSectionQuestionQueryNotFoundException;
import com.kaliber.quizinventory.model.QuizSectionQuestionQuery;

import java.util.ArrayList;

public interface QuizSectionQuestionQueryServiceInterface
{
     ArrayList<QuizSectionQuestionQuery> findAll() throws QuizSectionQuestionQueryNotFoundException;
     void save(QuizSectionQuestionQuery quizSectionQuestionQuery) throws QuizSectionQuestionQueryNotFoundException;
}
