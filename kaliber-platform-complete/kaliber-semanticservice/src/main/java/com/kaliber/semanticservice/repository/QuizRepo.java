package com.kaliber.semanticservice.repository;

import com.kaliber.semanticservice.model.Quiz;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;
import java.util.HashMap;

public interface QuizRepo extends Neo4jRepository<Quiz, Long> {

    //Creating QUIZ node
    @Query("merge (quiz:Quiz {quizCode: {quizCode}}) return quiz")
    Quiz createQuizNode(String quizCode);

    //Map QUIZ to SUBJECT
    @Query("match (quiz:Quiz {quizCode: {quizCode} }),(subject:Subject {subject:{subject}}) " +
            "merge (quiz)-[about:ABOUT]-(subject) return quiz, about, subject")
    void mapQuizToSubject(String quizCode, String subject);

    //Quiz Has_Question_On relationship
    @Query("match (a:Quiz) where a.quizCode = {quizCode} match (b:Concept) where b.concept in {concept} merge (a)-[hasQuestionOn:HAS_QUESTION_ON]-(b)")
    void quizHasQuestionOn(String quizCode, String[] concept);


    //For Quiz Feed
    //Get the TRENDING quizzes sorted on the maximum times played by a user
    @Query("MATCH (u:User)-[played:PLAYED]-(q:Quiz) with size(collect(played)) AS numberOfTimesQuizPlayed , collect(q) as ql ORDER BY numberOfTimesQuizPlayed DESC  UNWIND ql as qlo match (uf:User)-[pl:PLAYED]-(qf:Quiz) where qf.quizCode =  qlo.quizCode return distinct uf.username as username, qf.quizCode as quizCode")
    ArrayList<HashMap<String, String>> getTrendingQuizzes();
}
