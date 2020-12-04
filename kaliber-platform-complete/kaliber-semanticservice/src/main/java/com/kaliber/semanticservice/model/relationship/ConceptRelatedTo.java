package com.kaliber.semanticservice.model.relationship;

import com.kaliber.semanticservice.model.Concept;
import com.kaliber.semanticservice.model.Question;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "RELATED_TO")
public class ConceptRelatedTo {

    private String taxonomy;
    private String difficultyLevel;

    @StartNode
    Concept concept;

    @EndNode
    Question question;

    public ConceptRelatedTo() {
    }

    public ConceptRelatedTo(String taxonomy, String difficultyLevel) {
        this.taxonomy = taxonomy;
        this.difficultyLevel = difficultyLevel;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
