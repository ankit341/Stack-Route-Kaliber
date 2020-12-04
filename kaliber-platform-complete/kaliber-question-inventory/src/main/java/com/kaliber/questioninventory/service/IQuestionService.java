package com.kaliber.questioninventory.service;

import com.kaliber.questioninventory.exception.QuestionInventoryException;
import com.kaliber.questioninventory.model.Question;

import java.util.List;
import java.util.UUID;

public interface IQuestionService {

    List<Question> getAllQuestions(int page, int limit) throws QuestionInventoryException;
    Question getQuestionById(UUID questionId) throws QuestionInventoryException;
    Question createQuestion(Question ques) throws QuestionInventoryException;
    Question modifyQuestionById(UUID questionId, Question question) throws QuestionInventoryException;
    void deleteQuestion(UUID questionId) throws QuestionInventoryException;
    int getCountOfQuestions();
}
