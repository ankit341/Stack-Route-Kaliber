package com.kaliber.semanticservice.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;
import java.util.UUID;

@NodeEntity
public class Question {

    @Id
    @GeneratedValue
    private Long id;
    private UUID questionId;

    @Relationship(type = "ASKED_IN", direction = Relationship.OUTGOING)
    private Set<Quiz> quizzes;

    public Question() {
    }

    public Question(Long id, UUID questionId) {
        this.id = id;
        this.questionId = questionId;
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
