package com.kaliber.semanticservice.repository;

import com.kaliber.semanticservice.model.Concept;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface ConceptRepo extends Neo4jRepository<Concept, Long> {

    //Create CONCEPT node.
    @Query("merge (concept:Concept {concept: {concept}}) return concept")
    Concept createConceptNode(String concept);

    //Map Concept with a Question [RELATED_TO]
    @Query("MATCH (a:Question) where a.questionId = {questionId} MATCH (b:Concept) where b.concept in {concept} merge (a)-[relatedTo:RELATED_TO {taxonomy:{taxonomy}, difficultyLevel:{difficultyLevel}}]->(b) return a, relatedTo, b")
    void mapConceptToQuestion(UUID questionId, ArrayList<String> concept, String taxonomy, String difficultyLevel);


//    @Query("match (concept:Concept {concept:{concept}}),(subject:Subject {subject: {subject} }) merge (concept)-[belongsTo:BELONGS_TO]-(subject) return concept,belongsTo,subject")
//    void conceptBelongsToSubject(String concept[], String subject);



}
