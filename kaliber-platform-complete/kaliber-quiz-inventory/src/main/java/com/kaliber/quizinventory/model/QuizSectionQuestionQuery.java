package com.kaliber.quizinventory.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="quizSectionQuestionQuery")
public class QuizSectionQuestionQuery {
//	@Indexed
	private String sectionTitle;
	private String questionPattern;
	@CreatedDate
	private Date createdOn;
	private String createdBy;
	private String updatedBy;
	@LastModifiedDate
	private Date updatedOn;
	private ArrayList<Concepts> concepts=new ArrayList<Concepts>();
	public QuizSectionQuestionQuery(String sectionTitle, String questionPattern, String createdBy,
			String updatedBy, ArrayList<Concepts> concepts) {
		super();
		this.sectionTitle = sectionTitle;
		this.questionPattern = questionPattern;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.concepts = concepts;
	}
	public QuizSectionQuestionQuery() {
	}
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	public String getQuestionPattern() {
		return questionPattern;
	}
	public void setQuestionPattern(String questionPattern) {
		this.questionPattern = questionPattern;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public ArrayList<Concepts> getConcepts() {
		return concepts;
	}
	public void setConcepts(ArrayList<Concepts> concepts) {
		this.concepts = concepts;
	}
	
	

}
