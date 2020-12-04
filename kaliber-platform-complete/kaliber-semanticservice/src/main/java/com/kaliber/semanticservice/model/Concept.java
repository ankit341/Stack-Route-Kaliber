package com.kaliber.semanticservice.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.Set;

@NodeEntity
public class Concept {
    @Id
    private Long id;
    private ArrayList<String> concept;

    @Relationship(type = "RELATED_TO",direction = Relationship.OUTGOING)
    public Set<Question> questions;

    @Relationship(type = "BELONGS_TO" , direction = Relationship.OUTGOING)
    public Set<Subject> subject;

    public Concept() {
    }

    public Concept(Long id, ArrayList<String> concept) {
        this.id = id;
        this.concept = concept;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getConcept() {
        return concept;
    }

    public void setConcept(ArrayList<String> concept) {
        this.concept = concept;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }
}
