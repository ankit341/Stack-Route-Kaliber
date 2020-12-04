package com.kaliber.semanticservice.repository;

import com.kaliber.semanticservice.model.Question;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface QuestionRepo extends Neo4jRepository<Question, Long> {

    //Creating QUESTION node
    @Query("merge (questionId:Question {questionId: {questionId}}) return questionId")
    Question createQuestionNode(UUID questionId);

//    @Query("merge (questionId:Question {questionId:{questionId}}),(quizCode:Quiz {quizCode: {quizCode} }) merge (questionId)-[askedin:ASKED_IN]-(quizCode) return questionId, askedin,quizCode")
//    void questionAskedInQuiz(UUID questionId, String quizCode);
}
