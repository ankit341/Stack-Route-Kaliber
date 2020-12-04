package com.kaliber.leaderboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.HashSet;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String avatarURL;
    private float  totalPoints;
    private HashSet<String> followedTopics;
    private HashSet<String> usersFollowing;
    private HashSet<String> followers;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();


    public User() {
        super();
    }

    public User(String userId, String username, String name, String email, String avatarURL, float totalPoints, HashSet<String> followedTopics, HashSet<String> usersFollowing, HashSet<String> followers, Date createdOn, Date updatedOn) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.totalPoints = totalPoints;
        this.followedTopics = followedTopics;
        this.usersFollowing = usersFollowing;
        this.followers = followers;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public User(String username, String name, String email, float totalPoints) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.totalPoints = totalPoints;
    }

    public String getUserId() {return userId; }
    public void setUserId(String userId) {this.userId = userId ;}
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
    public String getAvatarURL() {
        return avatarURL;
    }
    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
    public float getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(float totalPoints) {
        this.totalPoints = totalPoints;
    }

    public HashSet<String> getFollowedTopics() {
        return followedTopics;
    }

    public void setFollowedTopics(HashSet<String> followedTopics) {
        this.followedTopics = followedTopics;
    }

    public HashSet<String> getUsersFollowing() {
        return usersFollowing;
    }

    public void setUsersFollowing(HashSet<String> usersFollowing) {
        this.usersFollowing = usersFollowing;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }
    public void setFollowers(HashSet<String> followers) {
        this.followers = followers;
    }
    public Date getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    public Date getUpdatedOn() {
        return updatedOn;
    }
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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
                ", email='" + email + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", totalPoints=" + totalPoints +
                ", followedTopics=" + followedTopics +
                ", usersFollowing=" + usersFollowing +
                ", followers=" + followers +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}