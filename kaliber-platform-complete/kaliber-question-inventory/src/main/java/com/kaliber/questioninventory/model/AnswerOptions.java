package com.kaliber.questioninventory.model;

public class AnswerOptions {

	private String optionKey;
	private String optionContent;
	private String optionReference;
	
	public AnswerOptions() {
		super();
	}
	public AnswerOptions(String optionKey, String optionContent, String optionReference) {
		super();
		this.optionKey = optionKey;
		this.optionContent = optionContent;
		this.optionReference = optionReference;
	}
	public String getOptionKey() {
		return optionKey;
	}
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	public String getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}
	public String getOptionReference() {
		return optionReference;
	}
	public void setOptionReference(String optionReference) {
		this.optionReference = optionReference;
	}
}
