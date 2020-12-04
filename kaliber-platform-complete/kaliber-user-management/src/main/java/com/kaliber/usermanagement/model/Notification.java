package com.kaliber.usermanagement.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notification")
public class Notification {

    @Indexed
    private String challengedBy;
    private String challengedTo;
    @Indexed
    private String quizCode;
    private enum status{
        PENDING, ACCEPTED
    }
    private status statusValue;
    @CreatedDate
    private Date createdDate = new Date();
    private enum type{
        INVITE, CHALLENGE
    }
    private type typeValue;

    public Notification(String challengedBy, String challengedTo, String quizCode, status statusValue, Date createdDate, type typeValue) {
        this.challengedBy = challengedBy;
        this.challengedTo = challengedTo;
        this.quizCode = quizCode;
        this.statusValue = statusValue;
        this.createdDate = createdDate;
        this.typeValue = typeValue;
    }

    public Notification() {
    }

    public String getChallengedBy() {
        return challengedBy;
    }

    public void setChallengedBy(String challengedBy) {
        this.challengedBy = challengedBy;
    }

    public String getChallengedTo() {
        return challengedTo;
    }

    public void setChallengedTo(String challengedTo) {
        this.challengedTo = challengedTo;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

    public status getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(status statusValue) {
        this.statusValue = statusValue;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public type getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(type typeValue) {
        this.typeValue = typeValue;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "challengedBy='" + challengedBy + '\'' +
                ", challengedTo='" + challengedTo + '\'' +
                ", quizCode='" + quizCode + '\'' +
                ", statusValue=" + statusValue +
                ", createdDate=" + createdDate +
                ", typeValue=" + typeValue +
                '}';
    }
}
