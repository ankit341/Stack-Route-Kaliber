package com.kaliber.questioninventory.service;

import com.kaliber.questioninventory.exception.QuestionInventoryException;
import com.kaliber.questioninventory.model.Question;
import com.kaliber.questioninventory.repository.QuestionRepository;
import com.kaliber.questioninventory.serviceproxy.SemanticServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService implements IQuestionService {

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private SemanticServiceProxy semanticServiceProxy;

	public List<Question> getAllQuestions(int page, int limit) throws QuestionInventoryException {
		Pageable pageable = PageRequest.of(page, limit);
		if( questionRepo.findAll().isEmpty()) {
			throw new QuestionInventoryException("There is no question in the database");
		}
		return (List<Question>) questionRepo.findAll(pageable).getContent();
	}

	public Question getQuestionById(UUID questionId) throws QuestionInventoryException {
		if( questionRepo.findByQuestionId(questionId) == null ) {
			throw new QuestionInventoryException("No question with this question Id ");
		}
		return  questionRepo.findByQuestionId(questionId);
	}

	//Semantic service is called. Node of the question will be created.
	public Question createQuestion(Question ques) throws QuestionInventoryException {
		UUID questionUUID = UUID.randomUUID();
		ques.setQuestionId(questionUUID);
		Question question = questionRepo.save(ques);
		if( question != null){
			semanticServiceProxy.createQuestionNode(questionUUID);
			semanticServiceProxy.createConceptNode(question.getConcepts().getName());
			semanticServiceProxy.mapConceptToQuestion(question.getQuestionId(),
					question.getConcepts().getName(),
					question.getConcepts().getTaxonomy().name(),
					question.getDifficultyLevel().toString());
			System.out.println("Question has been created :::-------> "+ questionUUID);
			return question;
		} else {
			throw new QuestionInventoryException("question not saved");
		}
	}

	public void deleteQuestion(UUID questionId) throws QuestionInventoryException {
		if(questionRepo.findByQuestionId(questionId)== null) {
			throw new QuestionInventoryException("No question found with this id");
		}else {
			questionRepo.delete(questionRepo.findByQuestionId(questionId));
		}
	}

	@Override
	public int getCountOfQuestions() {
		return questionRepo.findAll().size();
	}

	public Question modifyQuestionById(UUID questionId, Question question) throws QuestionInventoryException {
	question.setQuestionId(questionId);

		Question ques = questionRepo.save(question);
		if(ques == null) {
			throw new QuestionInventoryException("Question not updated");
		}
		return ques;
	}
}
