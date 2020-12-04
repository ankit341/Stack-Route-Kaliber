package com.kaliber.semanticservice.repository;

import com.kaliber.semanticservice.model.Subject;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SubjectRepo extends Neo4jRepository<Subject, Long> {

    //Creating Subject node.
    @Query("merge (subject:Subject { subject: {subject} }) return subject")
    Subject createSubjectNode(String subject);


}
