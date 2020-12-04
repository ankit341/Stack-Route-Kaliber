package com.kaliber.quizplay.model;

import java.util.Date;

public class LeaderBoard {
        private String quizCode;

        private float userStarRating;

        private String username;

        private String name;

        private float userPoints;

        private int positionChange;

        private Date quizTakenTime;

        public LeaderBoard(){

        }

        public LeaderBoard(String quizCode, float userStarRating, String username, String name, float userPoints, int positionChange, Date quizTakenTime) {
            super();
            this.quizCode = quizCode;
            this.userStarRating = userStarRating;
            this.username = username;
            this.name = name;
            this.userPoints = userPoints;
            this.positionChange = positionChange;
            this.quizTakenTime = quizTakenTime;
        }

        public String getQuizCode() {
            return quizCode;
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

        public void setUsername(String username) {
            this.username = username;
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

}
