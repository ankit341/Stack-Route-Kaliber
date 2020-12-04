package com.kaliber.quizplay.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionResponse {
    private ArrayList<AnswerOptions> answerOptions;
    private ConfidenceWager confidenceWager;
    private UUID questionId;

    public ArrayList<AnswerOptions> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(ArrayList<AnswerOptions> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public ConfidenceWager getConfidenceWager() {
        return confidenceWager;
    }

    public void setConfidenceWager(ConfidenceWager confidenceWager) {
        this.confidenceWager = confidenceWager;
    }
}
