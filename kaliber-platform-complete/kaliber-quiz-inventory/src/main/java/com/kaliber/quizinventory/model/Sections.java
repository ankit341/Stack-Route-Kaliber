package com.kaliber.quizinventory.model;

public class Sections {

	private String sectionTitle;
//	private ArrayList<SectionWiseQuestions> sectionWiseQuestion = new ArrayList<>();
	private int sequence;
	private boolean isOptional;
	private boolean randomiseQuestions;
//	@CreatedDate
//	private Date createdOn=new Date();
//	private String createdBy;
//	@LastModifiedDate
//	private Date updatedOn=new Date();
//	private String updatedBy;

	public Sections() {
	}

	public Sections(String sectionTitle, int sequence, boolean isOptional,boolean randomiseQuestions) {
		super();
		this.sectionTitle = sectionTitle;
		this.sequence = sequence;
		this.isOptional = isOptional;
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

	public boolean isRandomiseQuestions() {
		return randomiseQuestions;
	}

	public void setRandomiseQuestions(boolean randomiseQuestions) {
		this.randomiseQuestions = randomiseQuestions;
	}
	

}