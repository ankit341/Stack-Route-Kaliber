package com.kaliber.quizplay.model;

import java.util.ArrayList;

public class Concept {

	private ArrayList<String> name;
	public enum Taxonomy{
		Remember,
		Understand,
		Apply,
		Analyze,
		Evaluate,
		Create
	}
	private Taxonomy taxonomy;
	public Concept() {
		super();
	}
	public Concept(ArrayList<String> name, Taxonomy taxonomy) {
		super();
		this.name = name;
		this.taxonomy = taxonomy;
	}
	public ArrayList<String> getName() {
		return name;
	}
	public void setName(ArrayList<String> name) {
		this.name = name;
	}
	public Taxonomy getTaxonomy() {
		return taxonomy;
	}
	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}
}
