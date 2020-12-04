package com.kaliber.semanticservice.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long Id;
    private String username; //email-id of the user.

    @Relationship(type = "PLAYED", direction = Relationship.OUTGOING)
    private Set<Quiz> attemptedQuizzes;

    @Relationship(type = "IS_FOLLOWING")
    private Set<User> followingUsers;

    @Relationship(type = "ANSWERED",direction = Relationship.OUTGOING)
    private Set<Question> questions;

    public User() {
    }

    public User(Long Id, String username) {
        this.Id = Id;
        this.username = username;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Quiz> getAttemptedQuizzes() {
        return attemptedQuizzes;
    }

    public void setAttemptedQuizzes(Set<Quiz> attemptedQuizzes) {
        this.attemptedQuizzes = attemptedQuizzes;
    }

    public Set<User> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(Set<User> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
