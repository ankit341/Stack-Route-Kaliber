package com.kaliber.quizinventory.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaliber.quizinventory.exception.QuizSectionQuestionQueryNotFoundException;
import com.kaliber.quizinventory.model.QuizSectionQuestionQuery;
import com.kaliber.quizinventory.repository.QuizSectionQuestionQueryRepository;

@Service
public class QuizSectionQuestionQueryService implements QuizSectionQuestionQueryServiceInterface{

	@Autowired 
	private QuizSectionQuestionQueryRepository quizSectionRepo;

	public ArrayList<QuizSectionQuestionQuery> findAll() throws QuizSectionQuestionQueryNotFoundException{
		
		return (ArrayList<QuizSectionQuestionQuery>) quizSectionRepo.findAll();
	}

	public void save(QuizSectionQuestionQuery quizSectionQuestionQuery) throws QuizSectionQuestionQueryNotFoundException{
		
		quizSectionRepo.save(quizSectionQuestionQuery);
	}

	
	
}
