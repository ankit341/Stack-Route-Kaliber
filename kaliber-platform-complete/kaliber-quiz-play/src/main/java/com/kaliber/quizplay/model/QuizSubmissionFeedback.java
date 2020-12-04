package com.kaliber.quizplay.model;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document(collection = "quizSubmissions")
@Component
public class QuizSubmissionFeedback {
    @Id
    private UUID submissionId;
   // @Indexed(unique=true)
    private String userName;
//    @Indexed(unique=true)
    private String quizCode;
    private int totalQuestions;
    private int correctlyAnsweredQuestions;
    private int incorrectAnsweredQuestions;
    private int unattemptedQuestions;
    private float totalPointsScored;
    @CreatedDate
    private Date quizStartTime=new Date();

    private Date quizEndTime;
    private Date quizEvaluationTime;

    @CreatedDate
    private Date createdOn = new Date();
    public QuizSubmissionFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

    public QuizSubmissionFeedback(UUID submissionId, String userName, String quizCode, int totalQuestions, int correctlyAnsweredQuestions, int incorrectAnsweredQuestions, int unattemptedQuestions, int totalPointsScored, Date quizStartTime, Date quizEndTime, Date quizEvaluationTime, Date createdOn) {
        this.submissionId = submissionId;
        this.userName = userName;
        this.quizCode = quizCode;
        this.totalQuestions = totalQuestions;
        this.correctlyAnsweredQuestions = correctlyAnsweredQuestions;
        this.incorrectAnsweredQuestions = incorrectAnsweredQuestions;
        this.unattemptedQuestions = unattemptedQuestions;
        this.totalPointsScored = totalPointsScored;
        this.quizStartTime = quizStartTime;
        this.quizEndTime = quizEndTime;
        this.quizEvaluationTime = quizEvaluationTime;
        this.createdOn = createdOn;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectlyAnsweredQuestions() {
        return correctlyAnsweredQuestions;
    }

    public void setCorrectlyAnsweredQuestions(int correctlyAnsweredQuestions) {
        this.correctlyAnsweredQuestions = correctlyAnsweredQuestions;
    }

    public int getIncorrectAnsweredQuestions() {
        return incorrectAnsweredQuestions;
    }

    public void setIncorrectAnsweredQuestions(int incorrectAnsweredQuestions) {
        this.incorrectAnsweredQuestions = incorrectAnsweredQuestions;
    }

    public int getUnattemptedQuestions() {
        return unattemptedQuestions;
    }

    public void setUnattemptedQuestions(int unattemptedQuestions) {
        this.unattemptedQuestions = unattemptedQuestions;
    }

    public float getTotalPointsScored() {
        return totalPointsScored;
    }

    public void setTotalPointsScored(float totalPointsScored) {
        this.totalPointsScored = totalPointsScored;
    }

    public Date getQuizStartTime() {
        return quizStartTime;
    }

    public void setQuizStartTime(Date quizStartTime) {
        this.quizStartTime = quizStartTime;
    }

    public Date getQuizEndTime() {
        return quizEndTime;
    }

    public void setQuizEndTime(Date quizEndTime) {
        this.quizEndTime = quizEndTime;
    }

    public Date getQuizEvaluationTime() {
        return quizEvaluationTime;
    }

    public void setQuizEvaluationTime(Date quizEvaluationTime) {
        this.quizEvaluationTime = quizEvaluationTime;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
