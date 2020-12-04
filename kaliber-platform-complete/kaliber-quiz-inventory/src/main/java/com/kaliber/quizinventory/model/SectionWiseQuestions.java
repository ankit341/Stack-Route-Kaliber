package com.kaliber.quizinventory.model;

import java.util.UUID;

public class SectionWiseQuestions {

	private UUID questionId;
	private UUID followUpQuestion;
	private UUID diagnosticQuestion;
	private UUID alternativeQuestion;
	public SectionWiseQuestions() {
		super();
	}
	public SectionWiseQuestions(UUID questionId, UUID followUpQuestion, UUID diagnosticQuestion,
			UUID alternativeQuestion) {
		super();
		this.questionId = questionId;
		this.followUpQuestion = followUpQuestion;
		this.diagnosticQuestion = diagnosticQuestion;
		this.alternativeQuestion = alternativeQuestion;
	}
	public UUID getQuestionId() {
		return questionId;
	}
	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}
	public UUID getFollowUpQuestion() {
		return followUpQuestion;
	}
	public void setFollowUpQuestion(UUID followUpQuestion) {
		this.followUpQuestion = followUpQuestion;
	}
	public UUID getDiagnosticQuestion() {
		return diagnosticQuestion;
	}
	public void setDiagnosticQuestion(UUID diagnosticQuestion) {
		this.diagnosticQuestion = diagnosticQuestion;
	}
	public UUID getAlternativeQuestion() {
		return alternativeQuestion;
	}
	public void setAlternativeQuestion(UUID alternativeQuestion) {
		this.alternativeQuestion = alternativeQuestion;
	}
	
	
	
}