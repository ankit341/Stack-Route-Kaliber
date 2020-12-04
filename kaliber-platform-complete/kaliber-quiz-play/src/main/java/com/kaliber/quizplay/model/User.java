package com.kaliber.quizplay.model;



public class User {

    private String userId;

    private String username;

    public enum UserRole
    {
        PARTICIPANT,AUTHOR,ADMIN;
    }

    private String name;
    private String email;
    UserRole userRole;


    public User() {
        super();
    }

    public User(String userId, String username, String name, String email, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    public User(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    //	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                ", name='" + name + '\'' +
                ", email='" + email + '\'';
    }
}
