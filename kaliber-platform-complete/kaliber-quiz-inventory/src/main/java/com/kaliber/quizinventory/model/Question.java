package com.kaliber.quizinventory.model;

import java.util.Set;
import java.util.UUID;

public class Question {

    private Long id;
    private UUID questionId;
    private Set<Quiz> quizzes;

    public Question() {
    }

    public Question(Long id, UUID questionId, Set<Quiz> quizzes) {
        this.id = id;
        this.questionId = questionId;
        this.quizzes = quizzes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
