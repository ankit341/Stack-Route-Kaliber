package com.kaliber.usermanagement.model;

import java.util.Date;

public class PointsLog {
    private float points;
    private String quizCode;
    private Date dateAttempted = new Date();

    public PointsLog(float points, String quizCode, Date dateAttempted) {
        super();
        this.points = points;
        this.quizCode = quizCode;
        this.dateAttempted = dateAttempted;
    }
    public float getPoints() {
        return points;
    }
    public void setPoints(float points) {
        this.points = points;
    }
    public String getQuizCode() {
        return quizCode;
    }
    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }
    public Date getDateAttempted() {
        return dateAttempted;
    }
    public void setDateAttempted(Date dateAttempted) {
        this.dateAttempted = dateAttempted;
    }


}
