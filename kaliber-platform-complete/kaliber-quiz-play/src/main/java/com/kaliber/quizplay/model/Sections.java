package com.kaliber.quizplay.model;

public class Sections {
     
	private String sectionTitle;
	private int sequence;
	private boolean isOptional;
	private boolean randomiseQuestions;

	public Sections() {
	}

	public boolean isRandomiseQuestions() {
		return randomiseQuestions;
	}

	public void setRandomiseQuestions(boolean randomiseQuestions) {
		this.randomiseQuestions = randomiseQuestions;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public boolean isOptional() {
		return isOptional;
	}
	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
}
