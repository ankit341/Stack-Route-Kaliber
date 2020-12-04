package com.kaliber.questioninventory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 
 * This class is responsible for storing all the questions.
 *
 */

@Document(collection = "questions")
public class Question 
{

//	@Id
//	private UUID uniqueQuestionId;


	@Id
	private UUID questionId;



	@Indexed
	private String questionTitle;



	public enum QuestionType {
		MCQ,
		MMCQ
	}
	private QuestionType questionType;
	private String questionContent;	
	private String subject;
	private Concept concepts;
	private float maxScore;
	private int maxDurationMins;
	public enum DifficultyLevel{
		Beginner,
		Intermediate,
		Expert
	}
	private DifficultyLevel difficultyLevel;
	private String hintContent;
	private String feedbackContent;
	public enum Status{
		Draft,
		Active,
		Archieved
	}
	private Status status;
	private ArrayList<AnswerOptions> answerOptions = new ArrayList<>();
//	private String answerOptions[] = new String[4];
	private int timesAttempted;
	private int correctResponse;
	//answerKeys Strore's the correct answer of the question
	private ArrayList<String> answerKeys = new ArrayList<>();
	private boolean randomiseOptions;
	private Date createdOn = new Date();
	private String createdBy;
	private Date updatedOn = new Date();
	private String updatedBy;
	
	
	public Question() {
		super();
	}


	public Question( String questionTitle, QuestionType questionType, String questionContent, String subject, Concept concepts, float maxScore, int maxDurationMins, DifficultyLevel difficultyLevel, String hintContent, String feedbackContent, Status status, ArrayList<AnswerOptions> answerOptions, int timesAttempted, int correctResponse, ArrayList<String> answerKeys, boolean randomiseOptions, Date createdOn, String createdBy, Date updatedOn, String updatedBy) {


		this.questionTitle = questionTitle;
		this.questionType = questionType;
		this.questionContent = questionContent;
		this.subject = subject;
		this.concepts = concepts;
		this.maxScore = maxScore;
		this.maxDurationMins = maxDurationMins;
		this.difficultyLevel = difficultyLevel;
		this.hintContent = hintContent;
		this.feedbackContent = feedbackContent;
		this.status = status;
		this.answerOptions = answerOptions;
		this.timesAttempted = timesAttempted;
		this.correctResponse = correctResponse;
		this.answerKeys = answerKeys;
		this.randomiseOptions = randomiseOptions;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	public UUID getQuestionId() {
		return questionId;
	}


	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}


	public String getQuestionTitle() {
		return questionTitle;
	}


	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}


	public QuestionType getQuestionType() {
		return questionType;
	}


	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}


	public String getQuestionContent() {
		return questionContent;
	}


	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public Concept getConcepts() {
		return concepts;
	}


	public void setConcepts(Concept concepts) {
		this.concepts = concepts;
	}


	public float getMaxScore() {
		return maxScore;
	}


	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}


	public int getMaxDurationMins() {
		return maxDurationMins;
	}


	public void setMaxDurationMins(int maxDurationMins) {
		this.maxDurationMins = maxDurationMins;
	}


	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}


	public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}


	public String getHintContent() {
		return hintContent;
	}


	public void setHintContent(String hintContent) {
		this.hintContent = hintContent;
	}


	public String getFeedbackContent() {
		return feedbackContent;
	}


	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public ArrayList<AnswerOptions> getAnswerOptions() {
		return answerOptions;
	}


	public void setAnswerOptions(ArrayList<AnswerOptions> answerOptions) {
		this.answerOptions = answerOptions;
	}


	public int getTimesAttempted() {
		return timesAttempted;
	}


	public void setTimesAttempted(int timesAttempted) {
		this.timesAttempted = timesAttempted;
	}


	public int getCorrectResponse() {
		return correctResponse;
	}


	public void setCorrectResponse(int correctResponse) {
		this.correctResponse = correctResponse;
	}


	public ArrayList<String> getAnswerKeys() {
		return answerKeys;
	}


	public void setAnswerKeys(ArrayList<String> answerKeys) {
		this.answerKeys = answerKeys;
	}


	public boolean isRandomiseOptions() {
		return randomiseOptions;
	}


	public void setRandomiseOptions(boolean randomiseOptions) {
		this.randomiseOptions = randomiseOptions;
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
