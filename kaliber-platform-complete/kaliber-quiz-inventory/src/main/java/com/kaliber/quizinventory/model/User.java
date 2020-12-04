package com.kaliber.quizinventory.model;

import java.util.Set;

public class User {

    private Long Id;
    private String username; //email-id of the user.
    private Set<Quiz> attemptedQuizzes;
    private Set<User> followingUsers;
    private Set<Question> questions;

    public User(Long id, String username, Set<Quiz> attemptedQuizzes, Set<User> followingUsers, Set<Question> questions) {
        Id = id;
        this.username = username;
        this.attemptedQuizzes = attemptedQuizzes;
        this.followingUsers = followingUsers;
        this.questions = questions;
    }

    public User() {
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
