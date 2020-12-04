package com.kaliber.quizplay.model;

import java.util.Date;

public class ReportQuiz {

    private String reportReason;
    private String comment;
    private Date reportTime = new Date();
    private String userName;

    public ReportQuiz() {
    }

    public ReportQuiz(String reportReason, String comment, Date reportTime, String userName) {
        this.reportReason = reportReason;
        this.comment = comment;
        this.reportTime = reportTime;
        this.userName = userName;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
