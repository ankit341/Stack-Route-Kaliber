package com.kaliber.quizplay.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Document(collection = "quizSubmissionQuestions")
@CompoundIndexes(
		{@CompoundIndex(name = "question_submission", def = "{'submissionId':1, 'questionId':1, 'sequence':1}", unique=true)})
public class QuizSubmissionQuestions {

	@Id
	private ObjectId quizSubmissionQuestionId;

//	@NotNull
	private UUID submissionId;
//	@NotNull
	private UUID questionId;
//	@NotNull
	private int  sequence;
//	@NotNull
	private String sectionTitle;
	private ArrayList<String> userAnswerKey;
	private ArrayList<String> userAnswerContent;
	private ArrayList<String> userAnswerReference;
	private boolean isCorrect;
	private boolean isSkipped=false;
//	public enum status;
//	@NotNull
	private float score;
	private String wagerLabel;
	private float wagerBonusScore;
	private float wagerPenaltyScore;
//	@NotNull
	private Date startedOn;
//	@NotNull
	private Date finishedOn;
	private Question question;
//	@NotNull
	public UUID getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(UUID submissionId) {
		this.submissionId = submissionId;
	}

	public ObjectId getQuizSubmissionQuestionId() {
		return quizSubmissionQuestionId;
	}

	public void setQuizSubmissionQuestionId(ObjectId quizSubmissionQuestionId) {
		this.quizSubmissionQuestionId = quizSubmissionQuestionId;
	}

	//	@NotNull
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
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	public ArrayList<String> getUserAnswerKey() {
		return userAnswerKey;
	}
	public void setUserAnswerKey(ArrayList<String> userAnswerKey) {
		this.userAnswerKey = userAnswerKey;
	}
	public ArrayList<String> getUserAnswerContent() {
		return userAnswerContent;
	}
	public void setUserAnswerContent(ArrayList<String> userAnswerContent) {
		this.userAnswerContent = userAnswerContent;
	}
	public ArrayList<String> getUserAnswerReference() {
		return userAnswerReference;
	}
	public void setUserAnswerReference(ArrayList<String> userAnswerReference) {
		this.userAnswerReference = userAnswerReference;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getWagerLabel() {
		return wagerLabel;
	}
	public void setWagerLabel(String wagerLabel) {
		this.wagerLabel = wagerLabel;
	}
	public float getWagerBonusScore() {
		return wagerBonusScore;
	}
	public void setWagerBonusScore(float wagerBonusScore) {
		this.wagerBonusScore = wagerBonusScore;
	}
	public float getWagerPenaltyScore() {
		return wagerPenaltyScore;
	}
	public void setWagerPenaltyScore(float wagerPenaltyScore) {
		this.wagerPenaltyScore = wagerPenaltyScore;
	}
	public Date getStartedOn() {
		return startedOn;
	}
	public void setStartedOn(Date startedOn) {
		this.startedOn = startedOn;
	}
	public Date getFinishedOn() {
		return finishedOn;
	}
	public void setFinishedOn(Date finishedOn) {
		this.finishedOn = finishedOn;
	}


	public boolean isSkipped() {
		return isSkipped;
	}

	public void setSkipped(boolean skipped) {
		isSkipped = skipped;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
