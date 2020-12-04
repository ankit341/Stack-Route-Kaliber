package com.kaliber.semanticservice.repository;

import com.kaliber.semanticservice.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public interface UserRepo extends Neo4jRepository<User, Long> {

    //Creating person node in the system
    @Query("merge (user:User {username: {username}}) return user")
    User createPersonNode(String username);

    //Mapping USER to the [played] QUIZ
    //API will be called as the user starts the quiz.
    @Query("match (me:User {username:{username}}),(quiz:Quiz {quizCode: {quizCode} }) merge (me)-[played:PLAYED]-(quiz) return me,played,quiz")
    void mapPersonToQuiz(String username, String quizCode);


    //Mapping Relationship User following User...
    @Query("match (me:User {username: {username}}), (friend:User {username: {personName}}) merge (me)-[following:IS_FOLLOWING]-(friend) return me,following, friend")
    void userFollowingUser(String username, String personName);

    //Mapping relationship User [INTRESTED_IN] Subject
    @Query("match (me:User) where me.username = {username} match (subject:Subject) where subject.subject in {subject} merge (me)-[interestedIn:INTRESTED_IN]-(subject) return interestedIn,subject")
    void userFollowingSubject(String username, HashSet<String> subject);


    //For Quiz Feed
    //GET quizzes to a person based upon the quizzes played by his peers and not by him.
    @Query("MATCH (me:User)-[:IS_FOLLOWING]-(friend:User)-[:PLAYED]-(quiz) WHERE NOT exists((me)-[:PLAYED]-(quiz)) AND me.username = {username} RETURN quiz.quizCode as quizCode, friend.username as username")
    ArrayList<HashMap<String,String>> getSuggestedQuizzes(String username);


    //For Quiz Feed
    //Get quizzes of those subject in which user is intrested in..
    @Query("match (u:User {username :{username} })-[r:INTRESTED_IN] ->(s:Subject) with collect(s) as inlist, u as name UNWIND inlist as list match (q:Quiz)-[about:ABOUT]-(k:Subject) where not (name)-[:PLAYED]-(q) and k.subject in list.subject match (u1:User)-[played:PLAYED]-(qz:Quiz) where qz.quizCode = q.quizCode return u1.username as username, qz.quizCode as quizCode")
    ArrayList<HashMap<String,String>> getQuizzesBasedOnSubjects(String username);


    //Map person to a question
    @Query("match (me:User {username:{username}}),(questionId:Question {questionId: {questionId} }) merge (me)-[answered:ANSWERED]-(questionId) return me,answered,questionId")
    void userAnsweredQuestion(String username, UUID questionId);




//    //Filter Question by Concept,taxonomy and difficultyLevel
//    @Query("MATCH (p:Concept {concept:{concept} })-[rel:RELATED_TO]-(c:Question) WHERE (rel.taxonomy={taxonomy} and rel.difficultyLevel= {difficultyLevel}) RETURN c")
//    ArrayList<Question> SearchQuestionByConcept(String concept, String taxonomy, String difficultyLevel);


//    //Fetching all the user who have played the quiz
//    @Query("MATCH (me:Person)-[p:PLAYED]-(quizCode:Quiz) return me,p,quizCode")
//    Collection<User> getAllPerson();
//
//
}
