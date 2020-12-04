package com.kaliber.questioninventory.exception;

/**
 * Custom exception class to handle all the exception of Question Inventory
 */

public class QuestionInventoryException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public QuestionInventoryException(String exString) {
		super(exString);
	}
}
