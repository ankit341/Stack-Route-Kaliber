package com.kaliber.leaderboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "leaderboard")
public class LeaderBoard {

    @Id
    private ObjectId leaderboardId;

    @Indexed
    private String quizCode;

    private String userAvatar;

    private float userStarRating;

    @Indexed
    private String username;

    private String name;

    private float userPoints;

    private int positionChange;

    private Date quizTakenTime;

    public LeaderBoard(){

    }

    public LeaderBoard(String quizCode, float userStarRating, String username, String name, float userPoints, int positionChange, Date quizTakenTime, String userAvatar) {
        super();
        this.quizCode = quizCode;
        this.userStarRating = userStarRating;
        this.username = username;
        this.name = name;
        this.userPoints = userPoints;
        this.positionChange = positionChange;
        this.quizTakenTime = quizTakenTime;
        this.userAvatar = userAvatar;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public ObjectId getLeaderboardId() {
        return leaderboardId;
    }

    public void setLeaderboardId(ObjectId leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

    public float getUserStarRating() {
        return userStarRating;
    }

    public void setUserStarRating(float userStarRating) {
        this.userStarRating = userStarRating;
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(float userPoints) {
        this.userPoints = userPoints;
    }

    public int getPositionChange() {
        return positionChange;
    }

    public void setPositionChange(int positionChange) {
        this.positionChange = positionChange;
    }

    public Date getQuizTakenTime() {
        return quizTakenTime;
    }

    public void setQuizTakenTime(Date quizTakenTime) {
        this.quizTakenTime = quizTakenTime;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public String toString() {
        return "LeaderBoard{" +
                "quizCode='" + quizCode + '\'' +
                ", userStarRating=" + userStarRating +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", userPoints=" + userPoints +
                ", positionChange=" + positionChange +
                ", userAvatar=" + userAvatar +
                ", quizTakenTime=" + quizTakenTime +
                '}';
    }
}
