package com.kaliber.quizinventory.model;

public class Concepts {

	private String conceptName;
	private int maxQuestions;
	private enum taxonomyLevels {REMEMBERING,UNDERSTANDING,APPLYING,ANALYSING,EVALUATION,CREATION};
	private taxonomyLevels taxonomy;
	public Concepts(String conceptName, int maxQuestions, taxonomyLevels taxonomy) {
		super();
		this.conceptName = conceptName;
		this.maxQuestions = maxQuestions;
		this.taxonomy = taxonomy;
	}
	public Concepts() {
	}
	public String getConceptName() {
		return conceptName;
	}
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}
	public int getMaxQuestions() {
		return maxQuestions;
	}
	public void setMaxQuestions(int maxQuestions) {
		this.maxQuestions = maxQuestions;
	}
	public taxonomyLevels getTaxonomy() {
		return taxonomy;
	}
	public void setTaxonomy(taxonomyLevels taxonomy) {
		this.taxonomy = taxonomy;
	}
	
	
}
