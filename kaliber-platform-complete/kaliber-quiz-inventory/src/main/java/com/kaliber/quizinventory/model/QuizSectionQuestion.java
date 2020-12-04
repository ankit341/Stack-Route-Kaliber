package com.kaliber.quizinventory.model;

import java.sql.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "quizSectionQuestions")
@CompoundIndexes({
	  @CompoundIndex(def = "{'quizCode':1, 'sectionTitle':1,'questionId':1}", name = "quizSectionQuestionId", unique=true)})
public class QuizSectionQuestion {


    //	@Indexed
	private String quizCode;
//	@Indexed
	private String sectionTitle;
//	@Indexed
	private UUID questionId;
	private int sequence;
	private UUID followUpQuestion;
	private UUID diagonisticQuestion;
	private UUID alternativeQuestion;
	private boolean isOptional;
	private float maxScore;
	private float penaltyScore;
	private float followupScore;
	private float alternativeScore;
	private float diagonisticScore;
	@CreatedDate
	private Date createdOn;
	private String createdBy;
	@LastModifiedDate
	private Date updatedOn;
	private String updatedBy;

	public QuizSectionQuestion(String quizCode, String sectionTitle, UUID questionId, int sequence,
			UUID followUpQuestion, UUID diagonisticQuestion, UUID alternativeQuestion, boolean isOptional,
			float maxScore, float penaltyScore, float followupScore, float alternativeScore, float diagonisticScore,
			String createdBy, String updatedBy) {
		super();
		this.quizCode = quizCode;
		this.sectionTitle = sectionTitle;
		this.questionId = questionId;
		this.sequence = sequence;
		this.followUpQuestion = followUpQuestion;
		this.diagonisticQuestion = diagonisticQuestion;
		this.alternativeQuestion = alternativeQuestion;
		this.isOptional = isOptional;
		this.maxScore = maxScore;
		this.penaltyScore = penaltyScore;
		this.followupScore = followupScore;
		this.alternativeScore = alternativeScore;
		this.diagonisticScore = diagonisticScore;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	public QuizSectionQuestion() {
	}

	public String getQuizCode() {
		return quizCode;
	}
	public void setQuizCode(String quizCode) {
		this.quizCode = quizCode;
	}
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	public UUID getQuestionId() {
		return questionId;
	}
	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public UUID getFollowUpQuestion() {
		return followUpQuestion;
	}
	public void setFollowUpQuestion(UUID followUpQuestion) {
		this.followUpQuestion = followUpQuestion;
	}
	public UUID getDiagonisticQuestion() {
		return diagonisticQuestion;
	}
	public void setDiagonisticQuestion(UUID diagonisticQuestion) {
		this.diagonisticQuestion = diagonisticQuestion;
	}
	public UUID getAlternativeQuestion() {
		return alternativeQuestion;
	}
	public void setAlternativeQuestion(UUID alternativeQuestion) {
		this.alternativeQuestion = alternativeQuestion;
	}
	public boolean isOptional() {
		return isOptional;
	}
	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	public float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}
	public float getPenaltyScore() {
		return penaltyScore;
	}
	public void setPenaltyScore(float penaltyScore) {
		this.penaltyScore = penaltyScore;
	}
	public float getFollowupScore() {
		return followupScore;
	}
	public void setFollowupScore(float followupScore) {
		this.followupScore = followupScore;
	}
	public float getAlternativeScore() {
		return alternativeScore;
	}
	public void setAlternativeScore(float alternativeScore) {
		this.alternativeScore = alternativeScore;
	}
	public float getDiagonisticScore() {
		return diagonisticScore;
	}
	public void setDiagonisticScore(float diagonisticScore) {
		this.diagonisticScore = diagonisticScore;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
}
