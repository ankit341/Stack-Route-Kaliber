package com.kaliber.quizplay.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kaliber.quizplay.model.LeaderBoard;
import com.kaliber.quizplay.model.Quiz;
import com.kaliber.quizplay.model.Question;
import com.kaliber.quizplay.model.QuestionResponse;


import com.kaliber.quizplay.model.QuizSectionQuestions;
import com.kaliber.quizplay.model.QuizSubmissionFeedback;
import com.kaliber.quizplay.model.QuizSubmissionQuestions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.kaliber.quizplay.exception.QuizPlayException;

import javax.servlet.http.HttpServletRequest;

@Service
public interface QuizPlayService {
    UUID startQuiz(String quizCode, HttpServletRequest httpServletRequest);

    QuizSubmissionFeedback insertQuizSubmission(QuizSubmissionFeedback quizSub);

    void insertQuizSubmissionQuestions(QuizSubmissionQuestions quizSubQues);

    ArrayList<QuizSubmissionQuestions> getQuizSubmissionQuestions(UUID submissionId);

    QuizSubmissionFeedback createQuizsubmission(String quizCode, UUID submissionId, HttpServletRequest httpServletRequest) throws QuizPlayException;

//    ArrayList<QuizSubmissionFeedback> getAllQuizSubmissions() throws QuizPlayException;
    List<QuizSubmissionFeedback> getAllQuizSubmissions(int page, int limit) throws QuizPlayException;


    //get quiz submission data by username this method is for postman data for temp
    QuizSubmissionFeedback makeQuizSubmission(QuizSubmissionFeedback quizSubmission);
    // get quiz submission data by user name
    ArrayList<QuizSubmissionFeedback> getAllQuizzesByUserName(Integer page, Integer limit, HttpServletRequest httpServletRequest) throws QuizPlayException;

    Quiz playQuizFeign(String quizCode) throws QuizPlayException, IOException;

    ArrayList<QuizSectionQuestions> getSectionQuestions(String quizCode, String sectionTitle);

    Question playQuizQuestionFeign(UUID questionId) throws QuizPlayException;

    ResponseEntity<?> playQuizLeaderBoardFeign(LeaderBoard leaderBoard) throws QuizPlayException;

    QuizSubmissionFeedback findBySubmissionId(UUID submissionId) throws QuizPlayException;

    Question evaluate(String quizCode, String sectionTitle, UUID questionId, UUID submissionId,int sequence, QuestionResponse questionResponse, HttpServletRequest httpServletRequest) throws QuizPlayException;

    int countOfQuizSubmissionsByUserName(HttpServletRequest httpServletRequest);

}
