package com.kaliber.semanticservice.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Quiz {

    @Id
    private Long id;
    @Index(unique = true)
    private String quizCode;

    @Relationship(type = "ABOUT", direction = Relationship.OUTGOING)
    public List<Subject> subjects;

    @Relationship(type = "HAS_QUESTION_ON", direction = Relationship.OUTGOING)
    public  List<Concept> concepts;


    public Quiz() {
    }

    public Quiz(Long id, String quizCode) {
        this.id = id;
        this.quizCode = quizCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuiz() {
        return quizCode;
    }

    public void setQuiz(String quizCode) {
        this.quizCode = quizCode;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }
}
